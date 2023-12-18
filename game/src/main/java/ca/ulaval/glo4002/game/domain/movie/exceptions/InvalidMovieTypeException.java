package ca.ulaval.glo4002.game.domain.movie.exceptions;

public class InvalidMovieTypeException extends RuntimeException {
    public InvalidMovieTypeException(String movieType) {
        super("Movie type is invalid : " + movieType);
    }
}
