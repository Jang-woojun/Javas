package com.cyberguardians;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.cyberguardians.controller.ChatController;
import com.cyberguardians.listener.CyberGuardiansListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;

@SpringBootApplication
public class CyberguardiansApplication {
	public static void main(String[] args) throws LoginException {
		ApplicationContext context = SpringApplication.run(CyberguardiansApplication.class, args);
	    String token = "MTE5NjYzNTUzNTY3NDk3MDIxNg.Gf9oCc.1BJwyksxvrflVjnoSmaiCZAwFw8lU7aKsJs7ao";
	    ChatController chatController = context.getBean(ChatController.class);

	    JDA jda = JDABuilder.createDefault(token)
	            .setActivity(Activity.playing("메세지 기다리는 중..."))
	            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
	            .addEventListeners(new CyberGuardiansListener(chatController))
	            .build();
    }

}
