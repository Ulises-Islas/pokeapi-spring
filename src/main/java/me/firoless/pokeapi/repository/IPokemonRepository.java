package me.firoless.pokeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.firoless.pokeapi.entity.Pokemon;

public interface IPokemonRepository extends JpaRepository<Pokemon, Long> {
    
}
