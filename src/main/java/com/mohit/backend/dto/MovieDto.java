package com.mohit.backend.dto;

import java.util.Set;

import jakarta.persistence.ElementCollection;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MovieDto {
    
    private Integer movieId;
    @NotBlank(message="please provide the movie name")
    private String movieName;
    
    @NotBlank(message="please provide the movie director")
    private String movieDirector;
    
    @NotBlank(message="please provide the movie studio")
    private String  studio;
    
    
    @ElementCollection
    private Set<String> cast;
    
    
    private String poster;
    
    private Integer releaseYear;

}
