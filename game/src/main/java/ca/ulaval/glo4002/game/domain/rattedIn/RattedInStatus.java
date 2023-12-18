package ca.ulaval.glo4002.game.domain.rattedIn;

public enum RattedInStatus {
    BUSY("busy"),
    OPEN_TO_WORK("openToWork"),
    NA("N/A");

    private final String status;

    RattedInStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
