package com.cyberguardians.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatBotResponse {
	
	private List<Choice> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Choice {
        private int index;
        private Message message;
    }
}
