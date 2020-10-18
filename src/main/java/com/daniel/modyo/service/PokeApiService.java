package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.PokeApiPokemonList;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import reactor.core.publisher.Mono;

public interface PokeApiService {
    Mono<Pokemon> getPokemon(String name);
    Mono<PokeApiPokemonList> getAllPokemon();
    Mono<PokemonSpecies> getSpecies(String species);
    Mono<EvolutionChain> getEvolutionChain(PokeApiNamedResource evolutionChain);
}
