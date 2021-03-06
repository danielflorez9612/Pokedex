package com.daniel.pokedex.web.error;

public class PokemonNotFoundException extends Exception {
    private String name;

    public PokemonNotFoundException(String name) {
        super("Pokemon with name \""+name+"\" not found");
    }
}
