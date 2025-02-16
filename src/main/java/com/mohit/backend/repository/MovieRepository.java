package com.mohit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohit.backend.entities.Movies;
@Repository
public interface MovieRepository extends JpaRepository<Movies,Integer> {

    @SuppressWarnings( "unchecked" )
    Movies save(Movies nmovie);
    
}
