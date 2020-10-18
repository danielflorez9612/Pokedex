package com.daniel.modyo.service;

import com.daniel.modyo.web.dto.Pokemon;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonService {
    Flux<Pokemon> getAllPokemon(int limit, int offset);
    Mono<Pokemon> getPokemon(String name);
}
