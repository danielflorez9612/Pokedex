package com.daniel.pokedex.repository;

import com.daniel.pokedex.repository.entity.NamedApiResource;
import com.daniel.pokedex.repository.entity.PokemonList;
import com.daniel.pokedex.repository.entity.Pokemon;
import com.daniel.pokedex.repository.entity.evolution.EvolutionChain;
import com.daniel.pokedex.repository.entity.species.PokemonSpecies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PokeApiRepository implements PokemonRepository {

    private final WebClient webClient;

    private final String pokeApiEndpoint;

    public PokeApiRepository(@Value("${pokeapi.url}") String pokeApiEndpoint, @Autowired WebClient webClient) {
        this.pokeApiEndpoint = pokeApiEndpoint;
        this.webClient = webClient.mutate().baseUrl(pokeApiEndpoint).build();
    }

    @Override
    @Cacheable("pokemon")
    public Mono<Pokemon> getPokemon(String name) {
        return webClient.get()
                .uri("/pokemon/{name}", name)
                .retrieve()
                .bodyToMono(Pokemon.class)
                .cache()
                ;
    }


    @Override
    @Cacheable("pokemonList")
    public Mono<PokemonList> getAllPokemon(int limit, int offset) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/pokemon").queryParam("limit", limit).queryParam("offset", offset).build())
                .retrieve()
                .bodyToMono(PokemonList.class)
                .cache()
                ;
    }

    @Override
    @Cacheable("species")
    public Mono<PokemonSpecies> getSpecies(String species) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment("pokemon-species", species).path("/").build())
                .retrieve()
                .bodyToMono(PokemonSpecies.class)
                .cache()
                ;
    }

    @Override
    @Cacheable("evolutionChains")
    public Mono<EvolutionChain> getEvolutionChain(NamedApiResource evolutionChain) {
        return webClient.get()
                .uri(evolutionChain.getUrl().replace(pokeApiEndpoint, ""))
                .retrieve()
                .bodyToMono(EvolutionChain.class)
                .cache()
                ;
    }
}
