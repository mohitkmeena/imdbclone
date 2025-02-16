package com.mohit.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mohit.backend.dto.MovieDto;
import com.mohit.backend.dto.MoviePageResponse;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto,MultipartFile file)throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer id,MovieDto movieDto,MultipartFile file) throws IOException;
    MovieDto deleteMovie(Integer id) throws IOException;
    MoviePageResponse getAllMoviePageResponse(Integer pageNum,int pageSize);
    MoviePageResponse getAllMoviePageAndSortResponse(Integer pageNum,int pageSize,String sortBy,String dir);
}
