package io.vineesh.movieinfoservice.resources;

import io.vineesh.movieinfoservice.models.Movie;
import io.vineesh.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequestMapping("/movies")
public class MovieResource {
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId){
//        return new Movie(movieId, "Test Name");
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey, MovieSummary.class);
        return  new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
