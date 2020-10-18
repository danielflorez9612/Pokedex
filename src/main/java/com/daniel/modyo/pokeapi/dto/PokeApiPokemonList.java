package com.daniel.modyo.pokeapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class PokeApiPokemonList {
    private List<PokeApiNamedResource> results;
}
