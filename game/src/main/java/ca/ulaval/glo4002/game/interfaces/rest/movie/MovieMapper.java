package ca.ulaval.glo4002.game.interfaces.rest.movie;

import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class MovieMapper {
    public MovieResponseDto toDto(Movie movie) {
        String title = movie.getTitle();
        String type = movie.getType().toString();
        List<String> potentialCasting = movie.getPotentialCasting();
        List<String> casting = movie.getCasting();
        int boxOffice = movie.getBoxOfficeAmount();

        return new MovieResponseDto(title, type, potentialCasting, casting, boxOffice);
    }
}
