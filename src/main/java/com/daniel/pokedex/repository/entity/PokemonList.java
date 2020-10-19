package com.daniel.pokedex.repository.entity;

import lombok.Data;

import java.util.List;

@Data
public class PokemonList {
    private List<NamedApiResource> results;
}
