package ca.ulaval.glo4002.game.interfaces.rest.movie;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.movie.BoxOffice;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieHighBudget;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieResourceTest {
    private static final String MOVIE_TYPE_STRING = "A";
    private static final String MOVIE_TITLE = "title";

    private GameService gameServiceMock;
    private MovieResource movieResource;
    private BaseMovieDto baseMovieDto;

    @BeforeEach
    void initMovies() {
        baseMovieDto = new BaseMovieDto(MOVIE_TITLE, MOVIE_TYPE_STRING);
    }

    @BeforeEach
    void initMovieResource() {
        gameServiceMock = mock(GameService.class);
        MovieMapper movieMapperMock = mock(MovieMapper.class);
        movieResource = new MovieResource(gameServiceMock, movieMapperMock);
    }

    @Test
    void whenApiCreateMovie_thenCallCreateMovie() {
        movieResource.createMovie(baseMovieDto);

        verify(gameServiceMock).createMovie(any(), any());
    }

    @Test
    void whenApiCreateMovie_thenReturnOKResponse() {
        Response response = movieResource.createMovie(baseMovieDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenApiGetMovies_thenCallGetMovies() {
        movieResource.getMovies();

        verify(gameServiceMock).getMovies();
    }

    @Test
    void givenMovie_whenApiGetMovies_thenReturnMovieResponse() {
        Movie movie = new MovieHighBudget(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, new BoxOffice(), MovieStatus.CREATING);
        when(gameServiceMock.getMovies()).thenReturn(List.of(movie));
        int expectedMovieResponsesSize = 1;

        List<MovieResponseDto> movieResponse = movieResource.getMovies();

        assertEquals(expectedMovieResponsesSize, movieResponse.size());
    }
}