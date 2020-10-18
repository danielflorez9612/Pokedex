package com.daniel.modyo.pokeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokeApiAbilityItem {
    private PokeApiNamedResource ability;
    @JsonProperty("is_hidden")
    private Boolean isHidden;
    private String slot;
}
