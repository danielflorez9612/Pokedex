package com.daniel.modyo.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pokemon {
    private String name;
    private String type;
    private Integer weight;
    private List<String> abilities;
    private String description;
    private List<String> evolutions;
    private String image;
}
