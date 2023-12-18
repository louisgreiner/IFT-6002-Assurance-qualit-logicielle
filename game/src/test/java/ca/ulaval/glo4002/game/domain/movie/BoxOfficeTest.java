package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BoxOfficeTest {
    private static final String CHARACTER_NAME_1 = "Name1";

    private BoxOffice boxOffice;

    @BeforeEach
    void initBoxOffice() {
        boxOffice = new BoxOffice();
    }

    @Test
    void givenHamsterBoxOfficeShare_whenComputeBonus_thenBonusIsCorrect() {
        float boxOfficeShare = 0.05F;
        int amount = 1000;
        float expectedBonus = 50;

        float bonus = boxOffice.computeBonus(boxOfficeShare, amount);

        assertEquals(expectedBonus, bonus);
    }

    @Test
    void givenChinchillaBoxOfficeShare_whenComputeBonus_thenBonusIsCorrect() {
        float boxOfficeShare = 0.02F;
        int amount = 1000;
        float expectedBonus = 20;

        float bonus = boxOffice.computeBonus(boxOfficeShare, amount);

        assertEquals(expectedBonus, bonus);
    }

    @Test
    void givenCasting_whenPayCharacterBonus_thenCallReceivePaymentForEachCharacter() {
        Hamster hamsterMock = mock(Hamster.class);
        Chinchilla chinchillaMock = mock(Chinchilla.class);
        when(hamsterMock.getManager()).thenReturn(Optional.of(chinchillaMock));

        boxOffice.payCharacterBonus(hamsterMock);

        verify(hamsterMock).receivePayment(anyFloat());
        verify(chinchillaMock).receivePayment(anyFloat());
    }

    @Test
    void givenCasting_whenCalculateAmount_thenAmountIsCorrect() {
        int hamsterFollowersNumber = 100;
        Hamster hamsterMock = mock(Hamster.class);
        when(hamsterMock.getName()).thenReturn(CHARACTER_NAME_1);
        when(hamsterMock.getHamstagramFollowersNumber()).thenReturn(hamsterFollowersNumber);
        int chinchillaFollowersNumber = 200;
        Chinchilla chinchillaMock = mock(Chinchilla.class);
        when(hamsterMock.getManager()).thenReturn(Optional.of(chinchillaMock));
        when(chinchillaMock.getHamstagramFollowersNumber()).thenReturn(chinchillaFollowersNumber);
        int expectedAmount = 503000;
        Characters characters = new Characters(List.of(hamsterMock));
        List<String> casting = List.of(CHARACTER_NAME_1);

        boxOffice.calculateAmount(casting, characters);

        assertEquals(expectedAmount, boxOffice.getAmount());
    }

    @Test
    void givenTwoHamstersWithSameChinchilla_whenCalculateAmount_thenAmountIsCorrect() {
        int hamsterFollowersNumber = 100;
        Hamster hamsterMock = mock(Hamster.class);
        when(hamsterMock.getName()).thenReturn(CHARACTER_NAME_1);
        when(hamsterMock.getHamstagramFollowersNumber()).thenReturn(hamsterFollowersNumber);
        int chinchillaFollowersNumber = 200;
        Chinchilla chinchillaMock = mock(Chinchilla.class);
        when(hamsterMock.getManager()).thenReturn(Optional.of(chinchillaMock));
        when(chinchillaMock.getHamstagramFollowersNumber()).thenReturn(chinchillaFollowersNumber);
        int expectedAmount = 504000;
        Characters characters = new Characters(List.of(hamsterMock));
        List<String> casting = List.of(CHARACTER_NAME_1, CHARACTER_NAME_1);

        boxOffice.calculateAmount(casting, characters);

        assertEquals(expectedAmount, boxOffice.getAmount());
    }

    @Test
    void whenDoubleRevenue_thenMultiplierDoubled() {
        boxOffice.doubleRevenue();
        int expectedMultiplier = 2;

        assertEquals(expectedMultiplier, boxOffice.getMultiplier());
    }
}