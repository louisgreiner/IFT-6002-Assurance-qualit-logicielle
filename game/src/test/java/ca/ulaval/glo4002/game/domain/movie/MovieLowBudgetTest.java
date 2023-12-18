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

class MovieLowBudgetTest {
    private static final String MOVIE_TITLE = "Title";
    private static final MovieType MOVIE_TYPE_B = MovieType.B;
    private static final String CHARACTER_NAME_1 = "Name1";
    private static final String CHARACTER_NAME_2 = "Name2";
    private static final String CHARACTER_NAME_3 = "Name3";
    private static final Type CHARACTER_TYPE = Type.HAMSTER;
    private static final int LOW_SALARY = 100;
    private static final int HIGH_SALARY = 200;

    private Movie movie;
    private CharacterFactory characterFactory;
    private Hamster hamsterWithLowSalary1;
    private Hamster hamsterWithLowSalary2;
    private Hamster hamsterWithHighSalary;
    private Characters characters;

    @BeforeEach
    void initMovie() {
        movie = new MovieLowBudget(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, new BoxOffice(), MovieStatus.CREATING);
    }

    @BeforeEach
    void initCharacters() {
        characterFactory = new CharacterFactory();
        hamsterWithLowSalary1 = (Hamster) characterFactory.create(CHARACTER_NAME_1, CHARACTER_TYPE, LOW_SALARY);
        hamsterWithLowSalary2 = (Hamster) characterFactory.create(CHARACTER_NAME_2, CHARACTER_TYPE, LOW_SALARY);
        hamsterWithHighSalary = (Hamster) characterFactory.create(CHARACTER_NAME_3, CHARACTER_TYPE, HIGH_SALARY);
        characters = new Characters(List.of(hamsterWithLowSalary1, hamsterWithLowSalary2, hamsterWithHighSalary));
    }

    @Test
    void whenGetType_thenReturnTypeB() {
        assertEquals(MOVIE_TYPE_B, movie.getType());
    }

    @Test
    void whenChooseCasting_thenChooseLowSalaryHamsters() {
        movie.addToPotentialCasting(hamsterWithLowSalary1.getName());
        movie.addToPotentialCasting(hamsterWithLowSalary2.getName());
        movie.addToPotentialCasting(hamsterWithHighSalary.getName());

        movie.chooseCasting(characters);

        assertTrue(movie.getCasting().contains(hamsterWithLowSalary1.getName()));
        assertTrue(movie.getCasting().contains(hamsterWithLowSalary2.getName()));
        assertFalse(movie.getCasting().contains(hamsterWithHighSalary.getName()));
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
        Hamster remainingHamsterInCasting = (Hamster) characterFactory.create(CHARACTER_NAME_1, CHARACTER_TYPE, LOW_SALARY);
        movie.addToCasting(remainingHamsterInCasting.getName());
        movie.addToPotentialCasting(hamsterWithLowSalary2.getName());
        movie.addToPotentialCasting(hamsterWithHighSalary.getName());

        movie.chooseCasting(characters);

        assertTrue(movie.getCasting().contains(hamsterWithLowSalary2.getName()));
        assertTrue(movie.getCasting().contains(remainingHamsterInCasting.getName()));
        assertFalse(movie.getCasting().contains(hamsterWithHighSalary.getName()));
    }
}