package ca.ulaval.glo4002.game.interfaces.rest.hamstagram;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.*;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HamstagramAccountMapperTest {
    private static final String OWNER_NAME = "OwnerName";
    private static final String CHARACTER_NAME = "name";
    private static final int CHARACTER_SALARY = 100;
    private static final Type CHARACTER_TYPE = Type.HAMSTER;

    private HamstagramAccountMapper hamstagramAccountMapper;
    private HamstagramAccount hamstagramAccountMock;
    private HamstagramAccount hamstagramAccount;
    private Character characterMock;
    private Character character;
    private HamstagramManager hamstagramManagerMock;

    @BeforeEach
    void initHamstagramAccountMapper() {
        hamstagramAccountMapper = new HamstagramAccountMapper();
    }

    @BeforeEach
    void initHamstagramAccount() {
        HamstagramAccountFactory hamstagramAccountFactory = new HamstagramAccountFactory();
        hamstagramAccount = hamstagramAccountFactory.create(OWNER_NAME);
    }

    @BeforeEach
    void initHamstagramAccountMock() {
        hamstagramAccountMock = mock(HamstagramAccount.class);
    }

    @BeforeEach
    void initCharacter() {
        CharacterFactory characterFactory = new CharacterFactory();
        character = characterFactory.create(CHARACTER_NAME, CHARACTER_TYPE, CHARACTER_SALARY);
    }

    @BeforeEach
    void initCharacterMock() {
        characterMock = mock(Character.class);
    }

    @BeforeEach
    void initHamstagramManagerMock() {
        hamstagramManagerMock = mock(HamstagramManager.class);
    }

    @Test
    void givenAnHamstaramAccountAndManagerPresent_whenMapToDto_thenCallHamstagramAccountMethods() {
        when(characterMock.getManager()).thenReturn(Optional.ofNullable(hamstagramManagerMock));

        hamstagramAccountMapper.toDto(hamstagramAccountMock, characterMock);

        verify(hamstagramAccountMock).getOwnerName();
        verify(hamstagramAccountMock).getFollowersNumber();
        verify(characterMock).getCelebrityRepresented();
        verify(characterMock, times(2)).getManager();
        verify(hamstagramManagerMock).getName();
    }

    @Test
    void givenAnHamstagramAccountAndManagerEmpty_whenMapToDto_thenCallHamstagramAccountMethods() {
        when(characterMock.getManager()).thenReturn(Optional.empty());

        hamstagramAccountMapper.toDto(hamstagramAccountMock, characterMock);

        verify(hamstagramAccountMock).getOwnerName();
        verify(hamstagramAccountMock).getFollowersNumber();
        verify(characterMock).getCelebrityRepresented();
        verify(characterMock, times(1)).getManager();
        verify(hamstagramManagerMock, never()).getName();
    }

    @Test
    void givenAnHamstagramAccount_whenMapToDto_thenDtoHasSameAttributes() {
        HamstagramAccountResponseDto hamstagramAccountResponseDto = hamstagramAccountMapper.toDto(hamstagramAccount, character);

        List<String> expectedRepresent = character.getCelebrityRepresented().stream().map(HamstagramCelebrity::getName).toList();
        assertEquals(hamstagramAccount.getOwnerName(), hamstagramAccountResponseDto.username());
        assertEquals(hamstagramAccount.getFollowersNumber(), hamstagramAccountResponseDto.nbFollowers());
        assertEquals(expectedRepresent, hamstagramAccountResponseDto.represent());
    }
}