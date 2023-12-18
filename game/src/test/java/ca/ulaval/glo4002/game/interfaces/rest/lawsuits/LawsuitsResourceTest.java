package ca.ulaval.glo4002.game.interfaces.rest.lawsuits;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LawsuitsResourceTest {
    private GameService gameServiceMock;
    private LawsuitsResource lawsuitsResource;

    @BeforeEach
    void initMovieResource() {
        gameServiceMock = mock(GameService.class);
        LawsuitsMapper lawsuitsMapperMock = mock(LawsuitsMapper.class);
        lawsuitsResource = new LawsuitsResource(gameServiceMock, lawsuitsMapperMock);
    }

    @Test
    void whenApiGetLawsuits_thenCallGetLawsuits() {
        lawsuitsResource.getLawsuits();

        verify(gameServiceMock).getLawsuits();
    }

    @Test
    void givenLawsuit_whenApiGetLawsuits_thenReturnLawsuitsDto() {
        Lawsuit lawsuit = new Lawsuit(1, "CharacterName", "PL", Optional.of("LawyerName"));
        when(gameServiceMock.getLawsuits()).thenReturn(List.of(lawsuit));
        int expectedLawsuitsResponsesSize = 1;

        List<LawsuitsDto> lawsuitsResponse = lawsuitsResource.getLawsuits();

        assertEquals(expectedLawsuitsResponsesSize, lawsuitsResponse.size());
    }
}