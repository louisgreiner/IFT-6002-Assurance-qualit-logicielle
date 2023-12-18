package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class RattedInRequestTest {
    private static final String SENDER_NAME = "SenderName";
    private static final String RECEIVER_NAME = "ReceiverName";

    private Chinchilla senderMock;
    private Chinchilla receiverMock;
    private RattedInRequest rattedInRequest;
    private Characters characters;

    @BeforeEach
    void initCharacters() {
        senderMock = mock(Chinchilla.class);
        receiverMock = mock(Chinchilla.class);
        when(senderMock.getName()).thenReturn(SENDER_NAME);
        when(receiverMock.getName()).thenReturn(RECEIVER_NAME);
        characters = new Characters(List.of(senderMock, receiverMock));
    }

    @BeforeEach
    void initRattedInRequest() {
        rattedInRequest = new RattedInRequest(SENDER_NAME, RECEIVER_NAME);
    }

    @Test
    void givenValidCharacters_whenExecute_thenCallAddRattedInContact() {
        when(receiverMock.acceptRattedInRequest(senderMock)).thenReturn(true);

        rattedInRequest.execute(characters);

        verify(senderMock).addRattedInContact(receiverMock);
        verify(receiverMock).addRattedInContact(senderMock);
    }

    @Test
    void givenRefusedRequest_whenExecute_thenDoNothing() {
        when(receiverMock.acceptRattedInRequest(senderMock)).thenReturn(false);

        rattedInRequest.execute(characters);

        verify(senderMock, never()).addRattedInContact(any());
        verify(receiverMock, never()).addRattedInContact(any());
    }

    @Test
    void givenCharacterNotFound_whenExecute_thenDoNothing() {
        Characters characters = new Characters(List.of(receiverMock));

        rattedInRequest.execute(characters);

        verify(senderMock, never()).addRattedInContact(any());
        verify(receiverMock, never()).addRattedInContact(any());
        verify(receiverMock, never()).acceptRattedInRequest(any());
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        rattedInRequest.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(rattedInRequest);
    }
}