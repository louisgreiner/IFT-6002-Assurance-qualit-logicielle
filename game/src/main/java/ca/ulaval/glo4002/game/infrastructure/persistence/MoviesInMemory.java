package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MoviesRepo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MoviesInMemory implements MoviesRepo {

    private static final LinkedHashMap<String, Movie> movies = new LinkedHashMap<>();

    @Override
    public void create(Movie movie) {
        movies.putIfAbsent(movie.getTitle(), movie);
    }

    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public void deleteAll() {
        movies.clear();
    }

    public boolean isEmpty() {
        return movies.isEmpty();
    }

    @Override
    public void saveAll(List<Movie> movies) {
        deleteAll();
        for (Movie movie : movies) {
            create(movie);
        }
    }
}
