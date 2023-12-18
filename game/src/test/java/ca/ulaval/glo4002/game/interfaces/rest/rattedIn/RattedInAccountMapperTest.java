package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import ca.ulaval.glo4002.game.domain.character.RattedInUser;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RattedInAccountMapperTest {
    private static final String OWNER_NAME = "OwnerName";
    private static final RattedInStatus RATTED_IN_STATUS = RattedInStatus.OPEN_TO_WORK;

    private RattedInAccountMapper rattedInAccountMapper;
    private RattedInAccount rattedInAccount;
    private RattedInAccount rattedInAccountMock;

    @BeforeEach
    void initRattedInAccountMapper() {
        rattedInAccountMapper = new RattedInAccountMapper();
    }

    @BeforeEach
    void initRattedInAccount() {
        RattedInAccountFactory rattedInAccountFactory = new RattedInAccountFactory();
        rattedInAccount = rattedInAccountFactory.create(OWNER_NAME, RATTED_IN_STATUS);
    }

    @BeforeEach
    void initRattedInAccountMock() {
        rattedInAccountMock = mock(RattedInAccount.class);
    }

    @Test
    void givenAnRattedInAccount_whenMapToDto_thenCallRattedInAccountMethods() {
        when(rattedInAccountMock.getRattedInStatus()).thenReturn(RATTED_IN_STATUS);

        rattedInAccountMapper.toDto(rattedInAccountMock);

        verify(rattedInAccountMock).getOwnerName();
        verify(rattedInAccountMock).getRattedInStatus();
        verify(rattedInAccountMock).getContacts();
    }

    @Test
    void givenAnRattedInAccount_whenMapToDto_thenDtoHasSameAttributes() {
        RattedInAccountResponseDto rattedInAccountResponseDto = rattedInAccountMapper.toDto(rattedInAccount);

        List<String> expectedContacts = rattedInAccount.getContacts().stream().map(RattedInUser::getName).toList();
        assertEquals(rattedInAccount.getOwnerName(), rattedInAccountResponseDto.username());
        assertEquals(rattedInAccount.getRattedInStatus().toString(), rattedInAccountResponseDto.status());
        assertEquals(expectedContacts, rattedInAccountResponseDto.contacts());
    }
}