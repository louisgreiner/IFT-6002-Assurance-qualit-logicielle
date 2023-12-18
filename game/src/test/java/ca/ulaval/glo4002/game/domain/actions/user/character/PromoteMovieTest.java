package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PromoteMovieTest {
    private static final String CHARACTER_NAME = "CharacterName";
    private static final String DIFFERENT_NAME = "DifferentName";
    private static final int LOW_REPUTATION = 59;
    private static final int MINIMAL_FOLLOWERS_NUMBER = 9000;
    private static final int REPUTATION = 60;
    private static final int LOW_FOLLOWERS = 8999;

    private PromoteMovie promoteMovie;
    private Hamster characterMock;

    @BeforeEach
    void initPromoteMovie() {
        promoteMovie = new PromoteMovie(CHARACTER_NAME);
    }

    @BeforeEach
    void initHamsterMock() {
        characterMock = mock(Hamster.class);
        when(characterMock.getName()).thenReturn(CHARACTER_NAME);
        when(characterMock.getReputation()).thenReturn(REPUTATION);
        when(characterMock.getHamstagramFollowersNumber()).thenReturn(MINIMAL_FOLLOWERS_NUMBER);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        promoteMovie.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(promoteMovie);
    }

    @Test
    void givenDifferentCharacterName_whenCanExecuteAction_thenReturnFalse() {
        when(characterMock.getName()).thenReturn(DIFFERENT_NAME);

        boolean canExecuteAction = promoteMovie.canExecuteAction(characterMock, CHARACTER_NAME);

        assertFalse(canExecuteAction);
    }

    @Test
    void givenCharacterWithLowReputation_whenCanExecuteAction_thenReturnFalse() {
        when(characterMock.getReputation()).thenReturn(LOW_REPUTATION);

        boolean canExecuteAction = promoteMovie.canExecuteAction(characterMock, CHARACTER_NAME);

        assertFalse(canExecuteAction);
    }

    @Test
    void givenEnoughFollowers_whenHasEnoughFollowers_thenReturnTrue() {
        when(characterMock.getHamstagramFollowersNumber()).thenReturn(MINIMAL_FOLLOWERS_NUMBER);

        boolean hasEnoughFollowers = promoteMovie.hasEnoughFollowers(characterMock);

        assertTrue(hasEnoughFollowers);
    }

    @Test
    void givenLowFollowers_whenHasEnoughFollowers_thenReturnFalse() {
        when(characterMock.getHamstagramFollowersNumber()).thenReturn(LOW_FOLLOWERS);

        boolean hasEnoughFollowers = promoteMovie.hasEnoughFollowers(characterMock);

        assertFalse(hasEnoughFollowers);
    }

    @Test
    void givenCharacterWithAllConditions_whenCanExecuteAction_thenReturnTrue() {
        boolean canExecuteAction = promoteMovie.canExecuteAction(characterMock, CHARACTER_NAME);

        assertTrue(canExecuteAction);
    }

    @Test
    void givenCharacterWithAllConditions_whenExecute_thenCallPromoteMovie() {
        Characters characters = new Characters();
        characters.add(characterMock);
        List<Movie> movies = new ArrayList<>();

        promoteMovie.execute(characters, movies);

        verify(characterMock).promoteMovie(movies);
    }
}