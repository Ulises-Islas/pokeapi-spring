package me.firoless.pokeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.firoless.pokeapi.entity.Ability;

public interface IAbilityRepository extends JpaRepository<Ability, String> {
    
}
