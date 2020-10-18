package com.daniel.modyo.pokeapi.dto.evolution;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChainLink {
    private PokeApiNamedResource species;
    @JsonProperty("evolves_to")
    private List<ChainLink> evolvesTo;
}
