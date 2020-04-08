package io.vineesh.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.vineesh.moviecatalogservice.model.CatalogItem;
import io.vineesh.moviecatalogservice.model.Movie;
import io.vineesh.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        //For each movie ID, call movie info service and get details
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+ rating.getMovieId(), Movie.class);
        //Put them all together
        return new CatalogItem(movie.getName(),"Test",rating.getRating());
    }
    private CatalogItem getFallbackCatalogItem(Rating rating) {

        return new CatalogItem("Name not found","Test",rating.getRating());
    }

}
