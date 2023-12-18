package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Type;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieTest {
    private static final String MOVIE_TITLE = "TestMovieTitle";
    private static final String CHARACTER_NAME_1 = "Name1";
    private static final String CHARACTER_NAME_2 = "Name2";
    private static final Type CHARACTER_TYPE_HAMSTER = Type.HAMSTER;
    private static final int FOLLOWERS_NUMBER = 12000;
    private static final String CHARACTER_NAME = "Bob";

    private Movie movie;
    private Hamster hamsterActorOneMock;
    private Hamster hamsterActorTwoMock;
    private Characters charactersWithMock;

    @BeforeEach
    void initHamsters() {
        HamstagramAccount hamstagramAccountMock = mock(HamstagramAccount.class);
        when(hamstagramAccountMock.getFollowersNumber()).thenReturn(FOLLOWERS_NUMBER);
        hamsterActorOneMock = mock(Hamster.class);
        when(hamsterActorOneMock.getType()).thenReturn(CHARACTER_TYPE_HAMSTER);
        when(hamsterActorOneMock.getName()).thenReturn(CHARACTER_NAME_1);
        when(hamsterActorOneMock.getHamstagramAccount()).thenReturn(hamstagramAccountMock);
        hamsterActorTwoMock = mock(Hamster.class);
        when(hamsterActorTwoMock.getType()).thenReturn(CHARACTER_TYPE_HAMSTER);
        when(hamsterActorTwoMock.getName()).thenReturn(CHARACTER_NAME_2);
        when(hamsterActorTwoMock.getHamstagramAccount()).thenReturn(hamstagramAccountMock);
        charactersWithMock = new Characters(List.of(hamsterActorOneMock, hamsterActorTwoMock));
    }

    private Movie createMovie(MovieStatus movieStatus) {
        return createMovie(movieStatus, new BoxOffice());
    }

    private Movie createMovie(MovieStatus movieStatus, BoxOffice boxOffice) {
        return new Movie(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, boxOffice, movieStatus) {
            @Override
            public MovieType getType() {
                return null;
            }

            @Override
            public void chooseCasting(Characters characters) {
                casting.addAll(characters.getNameListOf(Hamster.class));
            }
        };
    }

    @BeforeEach
    void initMovie() {
        movie = new Movie(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, new BoxOffice(), MovieStatus.CREATING) {
            @Override
            public MovieType getType() {
                return null;
            }

            @Override
            public void chooseCasting(Characters characters) {
                casting.addAll(potentialCasting);
            }
        };
    }

    @Test
    void givenPotentialCastingComplete_whenUpdateNextTurn_thenChangeStatusFilming() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.FILMING;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void givenCompletePotentialCasting_whenUpdateNextTurn_thenChangeStatusScreening() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.SCREENING;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void givenScreening_whenUpdateNextTurn_thenChangeStatusBoxOffice() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.BOX_OFFICE;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void givenBoxOffice_whenUpdateNextTurn_thenChangeStatusEnded() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.ENDED;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void givenFilmingAndNotCompletePotentialCasting_whenUpdateNextTurn_thenChangeStatusPotentialCasting() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        when(hamsterActorOneMock.canBeCast()).thenReturn(false);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(false);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.POTENTIAL_CASTING;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void givenScreeningAndNotCompleteCasting_whenUpdateNextTurn_thenChangeStatusPotentialCasting() {
        when(hamsterActorOneMock.canBeCast()).thenReturn(true);
        when(hamsterActorTwoMock.canBeCast()).thenReturn(true);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        movie.updateNextTurn(charactersWithMock);
        movie.removeEliminatedHamsters(new ArrayList<>());
        when(hamsterActorOneMock.canBeCast()).thenReturn(false);

        movie.updateNextTurn(charactersWithMock);

        MovieStatus expectedMovieStatus = MovieStatus.POTENTIAL_CASTING;
        assertEquals(expectedMovieStatus, movie.getStatus());
    }

    @Test
    void whenAddToCasting_thenAddToCasting() {
        movie.addToCasting(hamsterActorOneMock.getName());

        assertTrue(movie.getCasting().contains(hamsterActorOneMock.getName()));
    }

    @Test
    void whenRemoveHamster_thenRemoveHamster() {
        movie.addToCasting(hamsterActorOneMock.getName());

        movie.removeHamster(hamsterActorOneMock.getName());

        assertFalse(movie.getCasting().contains(hamsterActorOneMock.getName()));
    }

    @Test
    void givenStatusScreening_whenPayCharacters_thenPayHamsterSalary() {
        Movie movieScreening = createMovie(MovieStatus.SCREENING);
        movieScreening.addToCasting(hamsterActorOneMock.getName());

        movieScreening.payCharacters(charactersWithMock);

        verify(hamsterActorOneMock).receiveSalary();
    }

    @Test
    void givenStatusBoxOffice_whenPayCharacters_thenPayCharactersBonus() {
        BoxOffice boxOfficeMock = mock(BoxOffice.class);
        Movie movieBoxOffice = createMovie(MovieStatus.BOX_OFFICE, boxOfficeMock);
        movieBoxOffice.addToCasting(hamsterActorOneMock.getName());

        movieBoxOffice.payCharacters(charactersWithMock);

        verify(boxOfficeMock).payCharacterBonus(any());
    }

    @Test
    void whenApplyBonus_thenApplyBonus() {
        BoxOffice boxOfficeMock = mock(BoxOffice.class);
        Movie movieBoxOffice = createMovie(MovieStatus.BOX_OFFICE, boxOfficeMock);

        movieBoxOffice.applyBonus();

        verify(boxOfficeMock).doubleRevenue();
    }

    @Test
    void givenBoxOffice_whenGetCasting_thenReturnEmptyList() {
        Movie boxOfficeMovie = createMovie(MovieStatus.BOX_OFFICE);
        boxOfficeMovie.casting = List.of(CHARACTER_NAME);

        List<String> casting = boxOfficeMovie.getCasting();

        assertTrue(casting.isEmpty());
    }

    @Test
    void givenNotBoxOffice_whenGetCasting_thenReturnCasting() {
        Movie potentialCastingMovie = createMovie(MovieStatus.POTENTIAL_CASTING);
        potentialCastingMovie.setStatus(MovieStatus.POTENTIAL_CASTING);
        potentialCastingMovie.casting = List.of(CHARACTER_NAME);

        List<String> casting = potentialCastingMovie.getCasting();

        assertEquals(potentialCastingMovie.casting, casting);
    }
}