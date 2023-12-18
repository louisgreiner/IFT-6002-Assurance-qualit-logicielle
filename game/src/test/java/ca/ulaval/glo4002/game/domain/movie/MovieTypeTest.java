package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.movie.exceptions.InvalidMovieTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTypeTest {

    public static final String INVALID_MOVIE_TYPE_STRING = "Y";

    @Test
    void givenInvalidMovieType_whenFromString_thenThrowException() {
        assertThrows(InvalidMovieTypeException.class, () -> MovieType.fromString(INVALID_MOVIE_TYPE_STRING));
    }

    @Test
    void givenValidMovieType_whenFromString_thenReturnType() {
        assertEquals(MovieType.A, MovieType.fromString("A"));
    }
}