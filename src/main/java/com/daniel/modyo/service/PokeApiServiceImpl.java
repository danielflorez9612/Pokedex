package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.PokeApiPokemonList;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class PokeApiServiceImpl implements PokeApiService {

    private final WebClient webClient;

    @Value("${pokeapi.url}")
    private String pokeApiEndpoint;

    public PokeApiServiceImpl(@Value("${pokeapi.url}")
                              String pokeApiEndpoint) {
        this.webClient = WebClient.create(pokeApiEndpoint);
    }

    @Override
    public Mono<Pokemon> getPokemon(String name) {
        return webClient.get()
                .uri("/pokemon/{name}", name)
                .retrieve().bodyToMono(Pokemon.class);
    }


    @Override
    public Mono<PokeApiPokemonList> getAllPokemon() {
        return webClient.get()
                .uri("/pokemon")
                .retrieve().bodyToMono(PokeApiPokemonList.class);
    }

    @Override
    public Mono<PokemonSpecies> getSpecies(String species) {
        return webClient.get()
                .uri("/pokemon-species/{species}", species)
                .retrieve().bodyToMono(PokemonSpecies.class);
    }

    @Override
    public Mono<EvolutionChain> getEvolutionChain(PokeApiNamedResource evolutionChain) {
        return webClient.get()
                .uri(evolutionChain.getUrl().replace(pokeApiEndpoint, ""))
                .retrieve().bodyToMono(EvolutionChain.class);
    }
}
