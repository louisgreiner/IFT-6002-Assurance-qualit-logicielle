package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class FindLawyerTest {
    private FindLawyer findLawyer;
    private Character character1;
    private Character character2;

    @BeforeEach
    void initFindLawyer() {
        findLawyer = new FindLawyer();
    }

    @BeforeEach
    void initCharacterMock() {
        character1 = mock(Character.class);
        character2 = mock(Character.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        findLawyer.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(findLawyer);
    }

    @Test
    void whenExecute_thenChooseLawyerCalled() {
        Characters characters = new Characters(List.of(character1, character2));

        findLawyer.execute(characters);

        verify(character1).chooseLawyer(characters);
        verify(character2).chooseLawyer(characters);
    }
}