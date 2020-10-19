package com.daniel.pokedex.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pokemon {
    private Integer id;
    private List<PokemonAbility> abilities;
    @JsonProperty("base_experience")
    private Integer baseExperience;
    private Integer height;
    private String name;
    private List<PokemonType> types;
    private Integer weight;
    private PokemonSprites sprites;
    private NamedApiResource species;
}
