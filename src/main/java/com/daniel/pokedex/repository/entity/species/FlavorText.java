package com.daniel.pokedex.repository.entity.species;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlavorText {
    @JsonProperty("flavor_text")
    private String flavorText;
    private NamedApiResource language;
}
