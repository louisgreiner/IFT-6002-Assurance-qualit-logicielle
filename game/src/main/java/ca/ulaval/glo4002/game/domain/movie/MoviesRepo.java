package ca.ulaval.glo4002.game.domain.movie;

import java.util.List;

public interface MoviesRepo {

    void create(Movie movie);

    List<Movie> getAll();

    void deleteAll();

    void saveAll(List<Movie> movies);
}
