package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ChinchillasSendHamstagramRequestsTest {
    private ChinchillasSendHamstagramRequests chinchillasSendHamstagramRequests;
    private Hamster hamsterMock;
    private Characters charactersMock;
    private Chinchilla chinchillaMock;

    @BeforeEach
    void initChinchillasSendHamstagramRequests() {
        chinchillasSendHamstagramRequests = new ChinchillasSendHamstagramRequests();
    }

    @BeforeEach
    void initMock() {
        hamsterMock = mock(Hamster.class);
        charactersMock = mock(Characters.class);
        chinchillaMock = mock(Chinchilla.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        chinchillasSendHamstagramRequests.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(chinchillasSendHamstagramRequests);
    }

    @Test
    void whenExecute_thenChinchillasSendHamstagramRequest() {
        when(charactersMock.getListOf(Hamster.class)).thenReturn(List.of(hamsterMock));
        when(charactersMock.getListOf(Chinchilla.class)).thenReturn(List.of(chinchillaMock));
        List<Chinchilla> chinchillasMock = List.of(chinchillaMock);
        List<Hamster> hamsters = List.of(hamsterMock);

        chinchillasSendHamstagramRequests.execute(charactersMock);

        verify(chinchillaMock, times(chinchillasMock.size())).sendHamstagramRequests(hamsters);
    }

}