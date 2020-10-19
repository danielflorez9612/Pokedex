package com.daniel.modyo.pokeapi.dto.species;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlavorText {
    @JsonProperty("flavor_text")
    private String flavorText;
    private PokeApiNamedResource language;
}
