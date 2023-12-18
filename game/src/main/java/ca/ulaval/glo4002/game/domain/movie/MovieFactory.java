package ca.ulaval.glo4002.game.domain.movie;

public class MovieFactory {
    public Movie create(String title, MovieType movieType) {
        return switch (movieType) {
            case A -> new MovieHighBudget(title);
            case B -> new MovieLowBudget(title);
        };
    }
}
