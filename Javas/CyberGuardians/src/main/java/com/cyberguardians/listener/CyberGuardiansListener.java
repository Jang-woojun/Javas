package com.cyberguardians.listener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cyberguardians.controller.ChatController;
import com.cyberguardians.entity.ChatBotResponse;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Service
@Slf4j
public class CyberGuardiansListener extends ListenerAdapter {

	private Map<Long, String> originalMessages = new HashMap<>();

	@Autowired
	private ChatController chatController;

	public CyberGuardiansListener(ChatController chatController) {
		this.chatController = chatController;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		User user = event.getAuthor();
		TextChannel textChannel = event.getChannel().asTextChannel();
		Message message = event.getMessage();

		log.info(" get message : " + message.getContentDisplay());

		if (user.isBot()) {
			return;
		} else if (message.getContentDisplay().equals("")) {
			log.info("디스코드 Message 문자열 값 공백");
		}

		String[] messageArray = message.getContentDisplay().split(" ");

		// URL 추출을 위한 정규표현식 패턴
		String regex = "(http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\\(\\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(message.getContentRaw());

		// 매칭된 URL 출력
		while (matcher.find()) {
			String matchedURL = matcher.group(0);
			System.out.println("matchedURL " + matchedURL);
			// 여기에 정규표현식에 해당하는 prompt가 있는 경우의 동작을 추가
			if (matcher.group(0) != null) {
				String result = chatController.checkUrl(matchedURL);
				// 메시지를 가리는 기능을 사용하여 가려진 메시지로 변경
				message.delete().queue();
				String originalContent = message.getContentRaw();
				originalMessages.put(message.getIdLong(), originalContent);
				textChannel.sendMessage(user.getAsMention() + "님 링크가 발견되어 채팅이 지워졌습니다!").queue();
			}
		}

		if (messageArray[0].equalsIgnoreCase("")) {

			String[] messageArgs = Arrays.copyOfRange(messageArray, 1, messageArray.length);

			for (String msg : messageArgs) {
				String returnMessage = sendMessage(event, msg);
				textChannel.sendMessage(returnMessage).queue();
			}
		} 

	}

	private String sendMessage(MessageReceivedEvent event, String message) {
		User user = event.getAuthor();
		String returnMessage = "";

		switch (message) {
		case "안녕 !":
			returnMessage = user.getAsMention() + "님 반갑습니다 !";
			break;
		case "http://abc.com/":
			// "트로이목마 악성코드에 대한 예방법과 해결방안 알려줘"를 ChatController로 보냄
			ChatBotResponse chatResponse = chatController.chat("트로이목마 악성코드에 대한 예방법과 해결방안 알려줘");

			// chatResponse에서 답변을 가져와서 returnMessage에 설정
			returnMessage = "Chat-GPT 응답: " + chatResponse.getChoices().get(0).getMessage().getContent();
			break;
		case "test":
			returnMessage = user.getAsTag() + "님 테스트 중이세요?";
			break;
		case "누구야":
			returnMessage = user.getAsMention() + "님 저는 찬호님이 JDA로 구현한 Bot이에요 !";
			break;
		default:
			returnMessage = "명령어를 확인해 주세요.";
			break;
		}
		return returnMessage;
	}

}
