package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.PokeApiPokemonList;
import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import com.daniel.modyo.web.dto.PokemonDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static reactor.core.publisher.Mono.just;

@SpringBootTest
public class PokemonServiceImplTest {

    @Autowired
    private PokemonService pokemonService;

    @MockBean
    private PokeApiService pokeApiService;

    @Test
    public void testPokemonListFetchesAllData() throws IOException {
        Mockito.when(pokeApiService.getAllPokemon(anyInt(), anyInt()))
                .thenReturn(just(fromFile("pokemon.json", PokeApiPokemonList.class)));
        Mockito.when(pokeApiService.getPokemon(eq("bulbasaur")))
                .thenReturn(just(fromFile("1.json", Pokemon.class)));
        Mockito.when(pokeApiService.getPokemon(eq("ivysaur")))
                .thenReturn(just(fromFile("ivysaur.json", Pokemon.class)));
        Mockito.when(pokeApiService.getSpecies(eq("bulbasaur")))
                .thenReturn(just(fromFile("species/bulbasaur.json", PokemonSpecies.class)));
        Mockito.when(pokeApiService.getSpecies(eq("ivysaur")))
                .thenReturn(just(fromFile("species/ivysaur.json", PokemonSpecies.class)));
        List<PokemonDto> list = pokemonService.getAllPokemon(0, 0).collectList().block();
        assertThat(list, hasSize(2));
        assertThat(list, everyItem(
                allOf(
                        hasProperty("id", notNullValue()),
                        hasProperty("type", notNullValue()),
                        hasProperty("name", notNullValue()),
                        hasProperty("abilities", notNullValue())
                )
        ));
    }

    @Test
    public void testPokemonDetailFetchesAllData() throws IOException {
        Mockito.when(pokeApiService.getPokemon(eq("bulbasaur")))
                .thenReturn(just(fromFile("bulbasaur.json", Pokemon.class)));
        Mockito.when(pokeApiService.getSpecies(eq("bulbasaur")))
                .thenReturn(just(fromFile("species/bulbasaur.json", PokemonSpecies.class)));
        Mockito.when(pokeApiService.getEvolutionChain(ArgumentMatchers.any(PokeApiNamedResource.class)))
                .thenReturn(just(fromFile("evolutionchain/1.json", EvolutionChain.class)));
        PokemonDto bulbasaur = pokemonService.getPokemon("bulbasaur").block();
        assertThat(bulbasaur, allOf(
                hasProperty("id", equalTo(1)),
                hasProperty("type", equalTo("grass/poison")),
                hasProperty("name", equalTo("bulbasaur")),
                hasProperty("abilities", contains("overgrow", "chlorophyll")),
                hasProperty("description", equalTo("A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON.")),
                hasProperty("image", equalTo("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png")),
                hasProperty("evolutions", hasItem("ivysaur"))
        ));
    }

    private <T> T fromFile(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(getClass().getClassLoader().getResource("_response/" + filePath), clazz);
    }
}