package com.mohit.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mohit.backend.dto.MovieDto;
import com.mohit.backend.dto.MoviePageResponse;
import com.mohit.backend.entities.Movies;
import com.mohit.backend.exceptions.MovieNotFoundException;
import com.mohit.backend.repository.MovieRepository;
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private FIleService fileService;
    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String url;
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //upload file
         String uploadedFileName=fileService.uploadFile(path, file);
        //set the value to poster
        movieDto.setPoster(uploadedFileName);
        String posterUrl=url+"/file/"+uploadedFileName;
        //map dto to movie
        Movies nmovie=Movies.builder()
        .movieId(movieDto.getMovieId())
        .movieName(movieDto.getMovieName())
        .movieDirector(movieDto.getMovieDirector())
        .poster(movieDto.getPoster())
        .studio(movieDto.getStudio())
        .cast(movieDto.getCast())
        .releaseYear(movieDto.getReleaseYear())
        .posterUrl(posterUrl)
        .build();
        Movies movie=movieRepository.save(nmovie);
        //generate the poster url
       
        
        //movie to dto
        MovieDto movieDto2=MovieDto.builder()
        .cast(movie.getCast())
        .movieName(movie.getMovieName())
        .movieDirector(movie.getMovieDirector())
        .studio(movie.getStudio())
        .releaseYear(movie.getReleaseYear())
        .movieId(movie.getMovieId())
        .poster(posterUrl)
        .build();

        //return dto


        return movieDto2;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
           Movies movie= movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("No movie found"));
           MovieDto movieDto2=MovieDto.builder()
           .cast(movie.getCast())
           .movieName(movie.getMovieName())
           .movieDirector(movie.getMovieDirector())
           .studio(movie.getStudio())
           .releaseYear(movie.getReleaseYear())
           .movieId(movie.getMovieId())
           .poster(movie.getPosterUrl())
           .build();
   
           //return dto
   
   
           return movieDto2;
    }

    @Override
    public List<MovieDto> getAllMovies() {
       List<Movies> movies= movieRepository.findAll();

       List<MovieDto> movieDtos=movies.stream()
                               .map(
                                movie->MovieDto.builder()
                               .cast(movie.getCast())
                               .movieName(movie.getMovieName())
                               .movieDirector(movie.getMovieDirector())
                               .studio(movie.getStudio())
                               .releaseYear(movie.getReleaseYear())
                               .movieId(movie.getMovieId())
                               .poster(movie.getPosterUrl())
                               .build())
                               .collect(Collectors.toList());

    return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer id, MovieDto movieDto, MultipartFile file) throws IOException {
       Movies movie=movieRepository.findById(id).orElseThrow(()->new MovieNotFoundException("no entity found"));
       
       if(!file.isEmpty()){
       String ofilePath= movie.getPoster();
       Files.delete(Path.of(path+File.separator+ofilePath));
       String ufilename=fileService.uploadFile(path, file);
       movie.setPoster(ufilename);
       movie.setPosterUrl(url+"/file/"+ufilename);
       if(movieDto.getCast()!=null) movie.setCast(movieDto.getCast());
       if(movieDto.getMovieDirector()!=null) movie.setMovieDirector(movieDto.getMovieDirector());
       if(movieDto.getMovieName()!=null) movie.setMovieName(movieDto.getMovieName());
       if(movieDto.getReleaseYear()!=null) movie.setReleaseYear(movieDto.getReleaseYear());
       if(movieDto.getStudio()!=null) movie.setStudio(movieDto.getStudio());
       
       movie=movieRepository.save(movie);
       }

       return MovieDto.builder()
       .cast(movie.getCast())
       .movieName(movie.getMovieName())
       .movieDirector(movie.getMovieDirector())
       .studio(movie.getStudio())
       .releaseYear(movie.getReleaseYear())
       .movieId(movie.getMovieId())
       .poster(movie.getPosterUrl())
       .build();


    }

    @Override
    public MovieDto deleteMovie(Integer id) throws IOException {
        
      Movies movie=movieRepository.findById(id).orElseThrow(()->new MovieNotFoundException("not found movie"));
      
      Files.delete(Path.of(path+File.separator+movie.getPoster()));
      movieRepository.delete(movie);
      return MovieDto.builder()
       .cast(movie.getCast())
       .movieName(movie.getMovieName())
       .movieDirector(movie.getMovieDirector())
       .studio(movie.getStudio())
       .releaseYear(movie.getReleaseYear())
       .movieId(movie.getMovieId())
       .poster(movie.getPosterUrl())
       .build();
    }

    @Override
    public MoviePageResponse getAllMoviePageResponse(Integer pageNum, int pageSize) {
        PageRequest pageable=PageRequest.of(pageNum, pageSize);
       Page<Movies>moviesPages= movieRepository.findAll(pageable);
       List<Movies>allMovies=moviesPages.getContent();
       List<MovieDto>movieDtos=allMovies.stream()
       .map(movie->MovieDto.builder()
       .cast(movie.getCast())
       .movieName(movie.getMovieName())
       .movieDirector(movie.getMovieDirector())
       .studio(movie.getStudio())
       .releaseYear(movie.getReleaseYear())
       .movieId(movie.getMovieId())
       .poster(movie.getPosterUrl())
       .build())
       .collect(Collectors.toList());
       return new MoviePageResponse(movieDtos, pageNum, pageSize,(int)moviesPages.getTotalElements(),moviesPages.getTotalPages(), moviesPages.isLast());

    }

    @Override
    public MoviePageResponse getAllMoviePageAndSortResponse(Integer pageNum, int pageSize, String sortBy, String dir) {
       Sort sort=dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
       PageRequest pageable=PageRequest.of(pageNum, pageSize,sort);
       Page<Movies>moviesPages= movieRepository.findAll(pageable);
       List<Movies>allMovies=moviesPages.getContent();
       List<MovieDto>movieDtos=allMovies.stream()
       .map(movie->MovieDto.builder()
       .cast(movie.getCast())
       .movieName(movie.getMovieName())
       .movieDirector(movie.getMovieDirector())
       .studio(movie.getStudio())
       .releaseYear(movie.getReleaseYear())
       .movieId(movie.getMovieId())
       .poster(movie.getPosterUrl())
       .build())
       .collect(Collectors.toList());
       return new MoviePageResponse(movieDtos, pageNum, pageSize,moviesPages.getTotalElements(),moviesPages.getTotalPages(), moviesPages.isLast());

    }
    
}
