package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Rat;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ScandalTest {
    private static final String FROM_NAME = "fromName";
    private static final String TO_NAME = "toName";
    private static final int TURN_NUMBER = 23;

    private Hamster hamsterFromMock;
    private Hamster hamsterToMock;
    private Rat ratToMock;
    private Scandal scandal;

    @BeforeEach
    void initScandal() {
        scandal = new Scandal(FROM_NAME, TO_NAME);
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterFromMock = mock(Hamster.class);
        when(hamsterFromMock.getName()).thenReturn(FROM_NAME);
        when(hamsterFromMock.getReputation()).thenReturn(70);
        hamsterToMock = mock(Hamster.class);
        when(hamsterToMock.getName()).thenReturn(TO_NAME);
    }

    @BeforeEach
    void initRatMock() {
        ratToMock = mock(Rat.class);
        when(ratToMock.getName()).thenReturn(TO_NAME);
        when(ratToMock.getReputation()).thenReturn(70);
        when(ratToMock.getType()).thenReturn(Type.RAT);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        scandal.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(scandal);
    }

    @Test
    void givenCharacterFromWithoutEnoughReputation_whenExecute_thenCallCallConsequences() {
        Characters characters = new Characters(List.of(hamsterFromMock, hamsterToMock));
        when(hamsterFromMock.getReputation()).thenReturn(1);

        scandal.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(hamsterFromMock).receiveLawsuit(anyString(), anyInt(), any(), any());
        verify(hamsterToMock, never()).loseReputation(anyInt());
        verify(hamsterToMock, never()).receiveScandal();
    }

    @Test
    void givenCharacterFromWithEnoughReputationAndNotHamstagramUserTo_whenExecute_thenCallCallConsequences() {
        Characters characters = new Characters(List.of(hamsterFromMock, ratToMock));

        scandal.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(hamsterFromMock).receiveLawsuit(anyString(), anyInt(), any(), any());
        verify(ratToMock).loseReputation(anyInt());
    }

    @Test
    void givenCharacterFromWithEnoughReputationAndHamstagramUserTo_whenExecute_thenCallCallConsequences() {
        Characters characters = new Characters(List.of(hamsterFromMock, hamsterToMock));

        scandal.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(hamsterFromMock).receiveLawsuit(anyString(), anyInt(), any(), any());
        verify(hamsterToMock).loseReputation(anyInt());
        verify(hamsterToMock).receiveScandal();
    }
}