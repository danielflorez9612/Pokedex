package com.daniel.pokedex.repository.entity.species;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PokemonSpecies {
    @JsonProperty("evolution_chain")
    private NamedApiResource evolutionChain;
    @JsonProperty("flavor_text_entries")
    private List<FlavorText> flavorTextEntries;
}
