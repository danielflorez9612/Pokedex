package com.daniel.modyo.service;

import com.daniel.modyo.pokeapi.dto.PokeApiNamedResource;
import com.daniel.modyo.pokeapi.dto.PokeApiPokemonList;
import com.daniel.modyo.pokeapi.dto.Pokemon;
import com.daniel.modyo.pokeapi.dto.evolution.EvolutionChain;
import com.daniel.modyo.pokeapi.dto.species.PokemonSpecies;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PokeApiServiceImplTest {
    public static MockWebServer mockBackEnd;
    public PokeApiService pokeApiService;
    @Autowired
    private WebClient webClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        pokeApiService = new PokeApiServiceImpl(baseUrl, webClient);
    }

    @Test
    public void getPokemonByName() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(fromFile("bulbasaur.json"))
                .addHeader("Content-Type", "application/json"));

        Mono<Pokemon> employeeMono = pokeApiService.getPokemon("bulbasaur");

        StepVerifier.create(employeeMono)
                .expectNextMatches(pokemon -> pokemon.getName().equals("bulbasaur"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/pokemon/bulbasaur", recordedRequest.getPath());

    }

    @Test
    public void testGetAllPokemon() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(fromFile("pokemon.json"))
                .addHeader("Content-Type", "application/json"));

        Mono<PokeApiPokemonList> employeeMono = pokeApiService.getAllPokemon(0, 10);

        StepVerifier.create(employeeMono)
                .expectNextMatches(pokeApiPokemonList -> pokeApiPokemonList.getResults().size()==2)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertTrue(Objects.requireNonNull(recordedRequest.getPath()).startsWith("/pokemon?"));
        assertTrue(recordedRequest.getPath().contains("limit=0"));
        assertTrue(recordedRequest.getPath().contains("offset=10"));
        assertTrue(recordedRequest.getPath().contains("&"));
    }

    @Test
    public void testEvolutionChain() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(fromFile("evolutionchain/1.json"))
                .addHeader("Content-Type", "application/json"));
        PokeApiNamedResource pokeApiNamedResource = new PokeApiNamedResource();
        pokeApiNamedResource.setUrl("/evolution-chain/1/");
        Mono<EvolutionChain> employeeMono = pokeApiService.getEvolutionChain(pokeApiNamedResource);

        StepVerifier.create(employeeMono)
                .expectNextMatches(pokeApiPokemonList -> pokeApiPokemonList.getChain().getEvolvesTo().size()==1)
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/evolution-chain/1/", recordedRequest.getPath());
    }

    @Test
    public void testGetSpecies() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody(fromFile("species/bulbasaur.json"))
                .addHeader("Content-Type", "application/json"));
        Mono<PokemonSpecies> employeeMono = pokeApiService.getSpecies("bulbasaur");

        StepVerifier.create(employeeMono)
                .expectNextMatches(species -> !species.getFlavorTextEntries().isEmpty())
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/pokemon-species/bulbasaur/", recordedRequest.getPath());
    }

    private String fromFile(String filePath) throws IOException, URISyntaxException {
        return Files.readString(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("_response/" + filePath)).toURI()));
    }
}