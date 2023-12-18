package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public interface HamstagramCelebrity {
    void chooseBestHamstagramRequest();

    void handleHamstagramRequestFrom(Chinchilla sender);

    void removeManager();

    void applyBonusFromChinchilla(List<Movie> movies);

    String getName();
}