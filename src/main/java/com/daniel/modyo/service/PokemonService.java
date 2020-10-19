package com.daniel.modyo.service;

import com.daniel.modyo.web.dto.PokemonDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonService {
    Flux<PokemonDto> getAllPokemon(int limit, int offset);
    Mono<PokemonDto> getPokemon(String name);
}
