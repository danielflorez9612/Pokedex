package com.daniel.pokedex.service;

import com.daniel.pokedex.web.dto.PokemonDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PokemonService {
    Flux<PokemonDto> getAllPokemon(int limit, int offset);
    Mono<PokemonDto> getPokemon(String name);
}
