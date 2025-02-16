package com.mohit.backend.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Movies {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    
    private Integer movieId;
    @Column(nullable=false)
    @NotBlank(message="please provide the movie name")
    private String movieName;
    @Column(nullable=false)
    @NotBlank(message="please provide the movie director")
    private String movieDirector;
    @Column(nullable=false)
    @NotBlank(message="please provide the movie studio")
    private String  studio;
    @Column(nullable=false)
    
    @ElementCollection
    private Set<String> cast;
    @Column(nullable=false)
    @NotBlank(message="please provide the movie poster")
    private String poster;
    @Column(nullable=false)

    private Integer releaseYear;
    @NotBlank(message="please provide the movie poster")
    private String posterUrl;

}
