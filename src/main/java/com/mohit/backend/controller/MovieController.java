package com.mohit.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohit.backend.dto.MovieDto;
import com.mohit.backend.dto.MoviePageResponse;
import com.mohit.backend.service.MovieService;
import com.mohit.backend.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/movie")
    public ResponseEntity<MovieDto> addMovie(@RequestPart String smovieDto, @RequestPart MultipartFile file) throws IOException{
        MovieDto movieDto=  movieService.addMovie(getMovieDto(smovieDto), file);
        return new ResponseEntity<>(movieDto,HttpStatus.CREATED);
    }
    private MovieDto getMovieDto(String movieDtoString) throws JsonMappingException, JsonProcessingException{
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(movieDtoString, MovieDto.class);
    }
    @GetMapping("/movie")
    private List<MovieDto> getAllMovies(){
        return movieService.getAllMovies();
    }
    @GetMapping("/movie/{id}")
    private MovieDto getMovie(@PathVariable Integer id){
        return movieService.getMovie(id);
    }
    
    @PostMapping("/movie/{id}")
    private MovieDto updateMovies(@RequestPart String smovieDto,@PathVariable Integer id,@RequestPart MultipartFile file) throws IOException{
        MovieDto movieDto=getMovieDto(smovieDto);
    return movieService.updateMovie(id,movieDto , file);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("movie/{id}")
    private MovieDto deleteMovie(@PathVariable Integer id) throws IOException{
        return movieService.deleteMovie(id);
    }
    @GetMapping("/pmovie")
    public MoviePageResponse getMoviePageResponse(
        @RequestParam(defaultValue = AppConstants.PAGE_NUMBER ,required = false) Integer pageNum,
        @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
        
    ){
        return movieService.getAllMoviePageResponse(pageNum, pageSize);
    }
    @GetMapping("/spmovie")
    public MoviePageResponse getMoviePageAndSortResponse(
        @RequestParam(defaultValue = AppConstants.PAGE_NUMBER ,required = false) Integer pageNum,
        @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(defaultValue = AppConstants.SORTBY,required = false) String sortBy,
        @RequestParam(defaultValue = AppConstants.SORTDIR,required = false) String sortDir
        
    ){
        return movieService.getAllMoviePageAndSortResponse(pageNum, pageSize,sortBy,sortDir);
    }




}
