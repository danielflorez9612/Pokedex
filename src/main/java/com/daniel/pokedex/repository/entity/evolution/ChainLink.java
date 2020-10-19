package com.daniel.pokedex.repository.entity.evolution;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChainLink {
    private NamedApiResource species;
    @JsonProperty("evolves_to")
    private List<ChainLink> evolvesTo;
}
