package com.daniel.modyo.pokeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    private List<PokeApiAbilityItem> abilities;
    @JsonProperty("base_experience")
    private Integer baseExperience;
    private Integer height;
    private String name;
    private List<PokeApiTypeItem> types;
    private Integer weight;
    private PokeApiSprites sprites;
    private PokeApiNamedResource species;
}
