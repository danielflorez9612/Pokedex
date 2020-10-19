package com.daniel.pokedex.repository.entity;

import lombok.Data;

@Data
public class PokemonType {
    private Integer slot;
    private NamedApiResource type;
}
