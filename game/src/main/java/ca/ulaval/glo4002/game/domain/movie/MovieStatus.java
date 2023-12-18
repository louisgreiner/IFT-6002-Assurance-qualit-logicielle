package ca.ulaval.glo4002.game.domain.movie;

public enum MovieStatus {
    CREATING("CREATING"),
    POTENTIAL_CASTING("POTENTIAL_CASTING"),
    FILMING("FILMING"),
    SCREENING("SCREENING"),
    BOX_OFFICE("BOX_OFFICE"),
    ENDED("ENDED");

    private final String movieStatus;

    MovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    @Override
    public String toString() {
        return movieStatus;
    }
}
