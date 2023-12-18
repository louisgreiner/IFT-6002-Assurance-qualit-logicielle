package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieHighBudgetTest {
    private static final String MOVIE_TITLE = "Title";
    private static final MovieType MOVIE_TYPE_A = MovieType.A;
    private static final String CHARACTER_NAME_1 = "Name1";
    private static final String CHARACTER_NAME_2 = "Name2";
    private static final String CHARACTER_NAME_3 = "Name3";
    private static final Type CHARACTER_TYPE = Type.HAMSTER;
    private static final int SALARY = 100;
    private static final int HIGH_RATIO_SALARY = 10;
    private static final int AVERAGE_RATIO_SALARY = 20;
    private static final int LOW_RATIO_SALARY = 100;

    private Movie movie;
    private CharacterFactory characterFactory;
    private Hamster hamsterWithHighRatio;
    private Hamster hamsterWithAverageRatio;
    private Hamster hamsterWithLowRatio;
    private Characters characters;

    @BeforeEach
    void initMovie() {
        movie = new MovieHighBudget(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, new BoxOffice(), MovieStatus.CREATING);
    }

    @BeforeEach
    void initCharacter() {
        characterFactory = new CharacterFactory();
        hamsterWithHighRatio = (Hamster) characterFactory.create(CHARACTER_NAME_1, CHARACTER_TYPE, HIGH_RATIO_SALARY);
        hamsterWithAverageRatio = (Hamster) characterFactory.create(CHARACTER_NAME_2, CHARACTER_TYPE, AVERAGE_RATIO_SALARY);
        hamsterWithLowRatio = (Hamster) characterFactory.create(CHARACTER_NAME_3, CHARACTER_TYPE, LOW_RATIO_SALARY);
        characters = new Characters(List.of(hamsterWithHighRatio, hamsterWithAverageRatio, hamsterWithLowRatio));
    }

    @Test
    void whenGetType_thenReturnTypeA() {
        assertEquals(MOVIE_TYPE_A, movie.getType());
    }

    @Test
    void whenChooseCasting_thenChooseHigherRatioHamsters() {
        movie.addToPotentialCasting(hamsterWithHighRatio.getName());
        movie.addToPotentialCasting(hamsterWithAverageRatio.getName());
        movie.addToPotentialCasting(hamsterWithLowRatio.getName());

        movie.chooseCasting(characters);

        assertTrue(movie.getCasting().contains(hamsterWithHighRatio.getName()));
        assertTrue(movie.getCasting().contains(hamsterWithAverageRatio.getName()));
        assertFalse(movie.getCasting().contains(hamsterWithLowRatio.getName()));
    }

    @Test
    void whenChooseCasting_thenInverseAvailabilityOfCasting() {
        Hamster hamsterMock1 = mock(Hamster.class);
        when(hamsterMock1.getName()).thenReturn(CHARACTER_NAME_1);
        when(hamsterMock1.getType()).thenReturn(CHARACTER_TYPE);
        Hamster hamsterMock2 = mock(Hamster.class);
        when(hamsterMock2.getName()).thenReturn(CHARACTER_NAME_2);
        when(hamsterMock2.getType()).thenReturn(CHARACTER_TYPE);
        Characters characters = new Characters(List.of(hamsterMock1, hamsterMock2));
        movie.addToPotentialCasting(hamsterMock1.getName());
        movie.addToPotentialCasting(hamsterMock2.getName());

        movie.chooseCasting(characters);

        verify(hamsterMock1).makeNotAvailable();
        verify(hamsterMock2).makeNotAvailable();
    }

    @Test
    void givenACharacterInCastingAndCharactersInPotentialCasting_whenChooseCasting_thenAddLowerSalaryCharacterToCasting() {
        Hamster remainingHamsterInCasting = (Hamster) characterFactory.create(CHARACTER_NAME_1, CHARACTER_TYPE, SALARY);
        movie.addToCasting(remainingHamsterInCasting.getName());
        movie.addToPotentialCasting(hamsterWithHighRatio.getName());
        movie.addToPotentialCasting(hamsterWithLowRatio.getName());

        movie.chooseCasting(characters);

        assertTrue(movie.getCasting().contains(hamsterWithHighRatio.getName()));
        assertTrue(movie.getCasting().contains(remainingHamsterInCasting.getName()));
        assertFalse(movie.getCasting().contains(hamsterWithLowRatio.getName()));
    }
}