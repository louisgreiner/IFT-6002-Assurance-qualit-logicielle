package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class UpdateUnavailabilityTest {
    private UpdateUnavailability updateUnavailability;
    private Hamster hamsterMock;
    private Chinchilla chinchillaMock;

    @BeforeEach
    void initUpdateUnavailability() {
        updateUnavailability = new UpdateUnavailability();
    }

    @BeforeEach
    void initCharactersMock() {
        hamsterMock = mock(Hamster.class);
        chinchillaMock = mock(Chinchilla.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        updateUnavailability.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(updateUnavailability);
    }

    @Test
    void whenExecute_thenCallDecrementUnavailability() {
        Characters characters = new Characters(List.of(hamsterMock, chinchillaMock));
        when(hamsterMock.getType()).thenReturn(Type.HAMSTER);
        when(chinchillaMock.getType()).thenReturn(Type.CHINCHILLA);

        updateUnavailability.execute(characters);

        verify(hamsterMock).decrementUnavailability();
        verify(chinchillaMock).decrementUnavailability();
    }
}