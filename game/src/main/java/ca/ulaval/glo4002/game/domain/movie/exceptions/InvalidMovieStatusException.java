package ca.ulaval.glo4002.game.domain.movie.exceptions;

import ca.ulaval.glo4002.game.domain.movie.MovieStatus;

public class InvalidMovieStatusException extends RuntimeException {
    public InvalidMovieStatusException(MovieStatus status) {
        super("Movie status is invalid : " + status.toString());
    }

}
