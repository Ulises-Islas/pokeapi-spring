package me.firoless.pokeapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.firoless.pokeapi.entity.Pokemon;
import me.firoless.pokeapi.service.PokemonService;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public List<Pokemon> findAll() {
        return pokemonService.findAll();
    }

    @GetMapping("/{name}")
    public Pokemon searchAndSave(@PathVariable("name") String name) {
        return pokemonService.searchAndSave(name);
    }
    
}
