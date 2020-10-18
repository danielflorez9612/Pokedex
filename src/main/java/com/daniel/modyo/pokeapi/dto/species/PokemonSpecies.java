package com.daniel.modyo.pokeapi.dto.species;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PokemonSpecies {
    @JsonProperty("evolution_chain")
    private PokeApiNamedResource evolutionChain;
    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}
