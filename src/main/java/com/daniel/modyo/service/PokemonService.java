package com.daniel.modyo.service;

import com.daniel.modyo.web.dto.Pokemon;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PokemonService {
    List<Pokemon> getAllPokemon();
    Mono<Pokemon> getPokemon(String name);
}
