package ca.ulaval.glo4002.game.interfaces.rest.movie;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final Logger logger = LoggerFactory.getLogger(MovieResource.class);
    private final GameService gameService;
    private final MovieMapper movieMapper;

    @Inject
    public MovieResource(GameService gameService, MovieMapper movieMapper) {
        this.gameService = gameService;
        this.movieMapper = movieMapper;
    }

    @POST
    public Response createMovie(BaseMovieDto baseMovieDto) {
        gameService.createMovie(baseMovieDto.title(), MovieType.fromString(baseMovieDto.type()));
        logger.info("Post Movie " + baseMovieDto.type() + " : " + baseMovieDto.title());
        return Response.status(Response.Status.OK).build();
    }

    @GET
    public List<MovieResponseDto> getMovies() {
        List<Movie> movies = gameService.getMovies();
        logger.info("Get Movies : " + movies.stream().map(Movie::getTitle).collect(Collectors.joining("; ")));
        return movies.stream().map(movieMapper::toDto).collect(Collectors.toList());
    }
}
