package ca.ulaval.glo4002.game.interfaces.rest.lawsuits;

import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LawsuitsMapperTest {
    private static final int TURN_NUMBER = 1;
    private static final String NAME = "name";
    private static final String ACTION_CODE = "PL";

    private LawsuitsMapper lawsuitsMapper;
    private Lawsuit lawsuitMock;
    private Lawsuit lawsuit;
    private Lawsuit lawsuitNoLawyer;

    @BeforeEach
    void initLawsuitsMapper() {
        lawsuitsMapper = new LawsuitsMapper();
    }

    @BeforeEach
    void initLawsuitMock() {
        lawsuitMock = mock(Lawsuit.class);
    }

    @BeforeEach
    void initLawsuit() {
        lawsuit = new Lawsuit(TURN_NUMBER, NAME, ACTION_CODE, Optional.of("lawyer"));
        lawsuitNoLawyer = new Lawsuit(TURN_NUMBER, NAME, ACTION_CODE, Optional.empty());
    }

    @Test
    void givenALawsuit_whenMapToDto_thenCallLawsuitMethods() {
        lawsuitsMapper.toDto(lawsuitMock);

        verify(lawsuitMock).getTurnNumber();
        verify(lawsuitMock).getCharacterName();
        verify(lawsuitMock).getActionCode();
        verify(lawsuitMock).getLawyerName();
    }

    @Test
    void givenALawsuitAndALawyer_whenMapToDto_thenDtoHasSameAttributes() {
        LawsuitsDto lawsuitsDto = lawsuitsMapper.toDto(lawsuit);

        assertEquals(lawsuit.getTurnNumber(), lawsuitsDto.turnNumber());
        assertEquals(lawsuit.getCharacterName(), lawsuitsDto.characterName());
        assertEquals(lawsuit.getActionCode(), lawsuitsDto.actionCode());
        assertEquals(lawsuit.getLawyerName().get(), lawsuitsDto.lawyerName());
    }

    @Test
    void givenALawsuitAndNoLawyer_whenMapToDto_thenDtoHasSameAttributes() {
        LawsuitsDto lawsuitsDto = lawsuitsMapper.toDto(lawsuitNoLawyer);

        assertEquals(lawsuitNoLawyer.getTurnNumber(), lawsuitsDto.turnNumber());
        assertEquals(lawsuitNoLawyer.getCharacterName(), lawsuitsDto.characterName());
        assertEquals(lawsuitNoLawyer.getActionCode(), lawsuitsDto.actionCode());
        assertNull(lawsuitsDto.lawyerName());
    }
}