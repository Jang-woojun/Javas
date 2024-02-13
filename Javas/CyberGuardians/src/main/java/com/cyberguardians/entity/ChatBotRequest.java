package com.cyberguardians.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotRequest {

	private String model;
    private List<Message> messages;
    private int n;
    private double temperature;
    private int max_tokens;
    
}
