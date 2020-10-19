package com.daniel.pokedex.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonAbility {
    private NamedApiResource ability;
    @JsonProperty("is_hidden")
    private Boolean isHidden;
    private String slot;
}
