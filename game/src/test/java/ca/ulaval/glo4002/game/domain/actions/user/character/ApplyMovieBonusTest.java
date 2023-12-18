package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ApplyMovieBonusTest {
    private ApplyMovieBonus applyMovieBonus;
    private Hamster hamsterMock;
    private Visitor visitorMock;

    @BeforeEach
    void initAction() {
        applyMovieBonus = new ApplyMovieBonus();
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterMock = mock(Hamster.class);
    }

    @BeforeEach
    void initVisitorMock() {
        visitorMock = mock(Visitor.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        applyMovieBonus.accept(visitorMock);

        verify(visitorMock).visit(applyMovieBonus);
    }

    @Test
    void whenExecute_thenCallApplyMovieBonus() {
        applyMovieBonus.execute(List.of(hamsterMock), new ArrayList<>());

        verify(hamsterMock).applyMovieBonus(any());
    }
}