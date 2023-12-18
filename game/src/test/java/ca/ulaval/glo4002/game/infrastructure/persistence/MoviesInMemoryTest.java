package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.BoxOffice;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoviesInMemoryTest {
    private static final String TITLE = "Movie title";

    private MoviesInMemory moviesInMemory;
    private Movie movie;

    @BeforeEach
    void initMoviesInMemory() {
        moviesInMemory = new MoviesInMemory();
    }

    @BeforeEach
    void initMovie() {
        movie = createMovie(MovieStatus.CREATING);
    }

    private Movie createMovie(MovieStatus movieStatus) {
        return createMovie(movieStatus, new BoxOffice());
    }

    private Movie createMovie(MovieStatus movieStatus, BoxOffice boxOffice) {
        return new Movie(new ArrayList<>(), new ArrayList<>(), TITLE, boxOffice, movieStatus) {
            @Override
            public MovieType getType() {
                return null;
            }

            @Override
            public void chooseCasting(Characters characters) {
            }
        };
    }

    @AfterEach
    void resetMoviesInMemory() {
        moviesInMemory.deleteAll();
    }

    @Test
    void givenNoMovieInMemory_whenIsEmpty_thenReturnTrue() {
        assertTrue(moviesInMemory.isEmpty());
    }

    @Test
    void givenAMovieInMemory_whenDeleteAll_thenMoviesIsEmpty() {
        moviesInMemory.create(movie);

        moviesInMemory.deleteAll();

        assertTrue(moviesInMemory.isEmpty());
    }

    @Test
    void givenAMovieInMemory_whenGetAll_thenReturnMovie() {
        List<Movie> expectedMovies = List.of(movie);
        moviesInMemory.create(movie);

        List<Movie> foundMovie = moviesInMemory.getAll();

        assertEquals(expectedMovies, foundMovie);
    }

    @Test
    void givenAMovieInMemoryAndUpdatedMovie_whenSaveAll_thenReplaceMovie() {
        moviesInMemory.create(movie);
        Movie updatedMovie = createMovie(MovieStatus.CREATING);
        List<Movie> updatedMovies = List.of(updatedMovie);

        moviesInMemory.saveAll(updatedMovies);

        assertEquals(updatedMovies, moviesInMemory.getAll());
    }
}