package com.xaty.socketmanager.dto;

import lombok.Data;

@Data
public class ChatMessage {

    private String userName;
    private String message;
    private Float[] type;

}
