package com.daniel.modyo.pokeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokeApiOtherSprites {
    @JsonProperty("official-artwork")
    private PokeApiSprites officialArtwork;
}
