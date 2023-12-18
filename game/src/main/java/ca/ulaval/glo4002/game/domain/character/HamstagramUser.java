package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public interface HamstagramUser {
    float HARASSMENT_COMPLAINT_HAMSTAGRAM_FOLLOWERS_LOSS_RATE = 0.7F;
    int HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER = 3;
    int REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER = 2;
    float SCANDAL_HAMSTAGRAM_FOLLOWERS_LOSS_RATE = 0.4F;
    int SCANDAL_UNAVAILABILITY_TURN_NUMBER = 2;

    HamstagramAccount getHamstagramAccount();

    void updateHamstagramAccountsNextTurn();

    String getName();

    int getHamstagramFollowersNumber();

    void doRealityShow();

    void receiveScandal();

    void promoteMovie(List<Movie> movies);
}