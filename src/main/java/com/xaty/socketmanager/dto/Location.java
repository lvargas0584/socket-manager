package com.xaty.socketmanager.dto;

import lombok.Data;

@Data
public class Location {
    private String type;
    private Float[] coordinates;
}
