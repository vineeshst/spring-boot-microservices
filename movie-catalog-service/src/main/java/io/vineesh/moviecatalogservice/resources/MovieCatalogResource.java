package io.vineesh.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.vineesh.moviecatalogservice.model.CatalogItem;
import io.vineesh.moviecatalogservice.model.Movie;
import io.vineesh.moviecatalogservice.model.Rating;
import io.vineesh.moviecatalogservice.model.UserRating;
import io.vineesh.moviecatalogservice.services.MovieInfo;
import io.vineesh.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {


    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId")  String userId){
        UserRating ratings = userRatingInfo.getUserRating(userId);

        return ratings.getUserRating().stream().map(rating -> {
            return movieInfo.getCatalogItem(rating);
        })
                .collect(Collectors.toList());
    }





//    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId")  String userId){
//        return Arrays.asList(new CatalogItem("No movie", "", 0));
//    }
}
/* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/"+ rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/