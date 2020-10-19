package com.daniel.modyo.controller;

import com.daniel.modyo.service.PokemonService;
import com.daniel.modyo.web.dto.Pokemon;
import com.daniel.modyo.web.pagination.PaginationComponent;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PokemonController.class)
@Import({PokemonController.class, PaginationComponent.class})
public class PokemonControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PokemonService pokemonService;

    @Test
    public void testAllPokemonFirstPage() {
        Mockito.when(pokemonService.getAllPokemon(anyInt(), anyInt())).thenReturn(Flux.just(Pokemon.builder()
                .id(1)
                .type("type")
                .name("test")
                .build()));
        webClient.get().uri("/pokemon").exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("limit", "10")
                .expectHeader().valueEquals("offset", "0")
                .expectHeader().value("next", is(notNullValue()))
                .expectHeader().doesNotExist("previous")
                .expectBody().jsonPath("$", Matchers.hasSize(1))
        ;
    }

    @Test
    public void testAllPokemonSecondPage() {
        Mockito.when(pokemonService.getAllPokemon(anyInt(), anyInt())).thenReturn(Flux.just(Pokemon.builder()
                .id(1)
                .type("type")
                .name("test")
                .build()));
        webClient.get().uri("/pokemon?offset=1").exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("limit", "10")
                .expectHeader().valueEquals("offset", "1")
                .expectHeader().value("next", is(notNullValue()))
                .expectHeader().doesNotExist("previous")
                .expectBody().jsonPath("$", Matchers.hasSize(1))
        ;
    }
    @Test
    public void testHeaderPreviousIsPresent() {
        Mockito.when(pokemonService.getAllPokemon(anyInt(), anyInt())).thenReturn(Flux.just(Pokemon.builder()
                .id(1)
                .type("type")
                .name("test")
                .build()));
        webClient.get().uri("/pokemon?offset=30").exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("limit", "10")
                .expectHeader().valueEquals("offset", "30")
                .expectHeader().value("next", is(notNullValue()))
                .expectHeader().value("previous", is(notNullValue()))
                .expectBody().jsonPath("$", Matchers.hasSize(1))
        ;
    }
}