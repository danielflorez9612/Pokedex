package com.daniel.modyo.pokeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokeApiSprites {
    @JsonProperty("front_default")
    private String frontDefault;
    private PokeApiOtherSprites other;
}
