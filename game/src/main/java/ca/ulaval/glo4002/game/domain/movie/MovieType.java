package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.movie.exceptions.InvalidMovieTypeException;

public enum MovieType {
    A("A"),
    B("B");

    private final String movieType;

    MovieType(String movieType) {
        this.movieType = movieType;
    }

    public static MovieType fromString(String movieTypeString) {
        for (MovieType movieType : MovieType.values()) {
            if (movieType.movieType.equalsIgnoreCase(movieTypeString)) {
                return movieType;
            }
        }
        throw new InvalidMovieTypeException(movieTypeString);
    }

    @Override
    public String toString() {
        return movieType;
    }
}
