package com.daniel.pokedex.repository;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.daniel.pokedex.repository.entity.Pokemon;
import com.daniel.pokedex.repository.entity.PokemonList;
import com.daniel.pokedex.repository.entity.species.PokemonSpecies;
import com.daniel.pokedex.repository.entity.evolution.EvolutionChain;
import reactor.core.publisher.Mono;

public interface PokemonRepository {
    Mono<Pokemon> getPokemon(String name);
    Mono<PokemonList> getAllPokemon(int limit, int offset);
    Mono<PokemonSpecies> getSpecies(String species);
    Mono<EvolutionChain> getEvolutionChain(NamedApiResource evolutionChain);
}
