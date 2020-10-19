package com.daniel.pokedex.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonSprites {
    @JsonProperty("front_default")
    private String frontDefault;

    @JsonProperty("official-artwork")
    private PokemonSprites officialArtwork;

    private PokemonSprites other;
}
