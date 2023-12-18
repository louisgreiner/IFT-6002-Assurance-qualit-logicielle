package ca.ulaval.glo4002.game.interfaces.rest.movie;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieMapperTest {
    private static final String MOVIE_TITLE = "title";
    private static final MovieType MOVIE_TYPE = MovieType.A;

    private MovieMapper movieMapper;
    private Movie movieMock;
    private Movie movie;

    @BeforeEach
    void initMovieMapper() {
        movieMapper = new MovieMapper();
    }

    @BeforeEach
    void initMovieMock() {
        movieMock = mock(Movie.class);
    }

    @BeforeEach
    void initMovie() {
        MovieFactory movieFactory = new MovieFactory();
        movie = movieFactory.create(MOVIE_TITLE, MOVIE_TYPE);
    }

    @Test
    void givenAMovie_whenMapToDto_thenCallsMovieMethods() {
        when(movieMock.getType()).thenReturn(MOVIE_TYPE);

        movieMapper.toDto(movieMock);

        verify(movieMock).getTitle();
        verify(movieMock).getType();
        verify(movieMock).getPotentialCasting();
        verify(movieMock).getCasting();
        verify(movieMock).getBoxOfficeAmount();
    }

    @Test
    void givenAMovie_whenMapToDto_thenDtoHasSameAttributes() {
        MovieResponseDto movieResponseDto = movieMapper.toDto(movie);

        assertEquals(movie.getTitle(), movieResponseDto.title());
        assertEquals(movie.getType().toString(), movieResponseDto.type());
        assertEquals(movie.getPotentialCasting(), movieResponseDto.potentialCasting());
        assertEquals(movie.getCasting(), movieResponseDto.casting());
        assertEquals(movie.getBoxOfficeAmount(), movieResponseDto.boxOffice());
    }
}