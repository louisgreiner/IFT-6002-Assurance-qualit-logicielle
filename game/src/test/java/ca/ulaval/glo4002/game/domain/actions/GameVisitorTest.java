package ca.ulaval.glo4002.game.domain.actions;

import ca.ulaval.glo4002.game.domain.actions.nextTurn.*;
import ca.ulaval.glo4002.game.domain.actions.user.CreateCharacter;
import ca.ulaval.glo4002.game.domain.actions.user.CreateMovie;
import ca.ulaval.glo4002.game.domain.actions.user.RattedInRequest;
import ca.ulaval.glo4002.game.domain.actions.user.character.*;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class GameVisitorTest {
    private static final int TURN_NUMBER = 23;

    private GameVisitor gameVisitor;
    private Characters charactersMock;
    private List<Movie> moviesMock;

    @BeforeEach
    void initVisitor() {
        charactersMock = mock(Characters.class);
        Movie movieMock = mock(Movie.class);
        moviesMock = List.of(movieMock);
        gameVisitor = new GameVisitor(charactersMock, moviesMock, TURN_NUMBER);
    }

    @Test
    void whenVisitCreateCharacter_thenCreateCharacterIsExecuted() {
        CreateCharacter createCharacterMock = mock(CreateCharacter.class);

        gameVisitor.visit(createCharacterMock);

        verify(createCharacterMock).execute(charactersMock);
    }

    @Test
    void whenVisitRattedInRequest_thenRattedInRequestIsExecuted() {
        RattedInRequest rattedInRequestMock = mock(RattedInRequest.class);

        gameVisitor.visit(rattedInRequestMock);

        verify(rattedInRequestMock).execute(charactersMock);
    }

    @Test
    void whenVisitCreateMovie_thenCreateMovieIsExecuted() {
        CreateMovie createMovieMock = mock(CreateMovie.class);

        gameVisitor.visit(createMovieMock);

        verify(createMovieMock).execute(moviesMock);
    }

    @Test
    void whenVisitUpdateHamstagramAccountsNextTurn_thenUpdateHamstagramAccountsNextTurnIsExecuted() {
        UpdateHamstagramAccountsNextTurn updateHamstagramAccountsNextTurn = mock(UpdateHamstagramAccountsNextTurn.class);

        gameVisitor.visit(updateHamstagramAccountsNextTurn);

        verify(updateHamstagramAccountsNextTurn).execute(charactersMock);
    }

    @Test
    void whenVisitMoviesPayCharacters_thenMoviesPayCharactersIsExecuted() {
        MoviesPayCharacters moviesPayCharactersMock = mock(MoviesPayCharacters.class);

        gameVisitor.visit(moviesPayCharactersMock);

        verify(moviesPayCharactersMock).execute(moviesMock, charactersMock);
    }

    @Test
    void whenVisitHamstersPayRepresentedBy_thenHamstersPayRepresentedByIsExecuted() {
        HamstersPayRepresentedBy hamstersPayRepresentedByMock = mock(HamstersPayRepresentedBy.class);

        gameVisitor.visit(hamstersPayRepresentedByMock);

        verify(hamstersPayRepresentedByMock).execute(charactersMock);
    }

    @Test
    void whenVisitUpdateCharactersNextTurn_thenUpdateCharactersNextTurnIsExecuted() {
        UpdateCharactersNextTurn updateCharactersNextTurnMock = mock(UpdateCharactersNextTurn.class);

        gameVisitor.visit(updateCharactersNextTurnMock);

        verify(updateCharactersNextTurnMock).execute(charactersMock);
    }

    @Test
    void whenVisitUpdateUnavailability_thenUpdateUnavailabilityIsExecuted() {
        UpdateUnavailability updateUnavailabilityMock = mock(UpdateUnavailability.class);

        gameVisitor.visit(updateUnavailabilityMock);

        verify(updateUnavailabilityMock).execute(charactersMock);
    }

    @Test
    void whenVisitApplyMovieBonus_thenApplyMovieBonusIsExecuted() {
        ApplyMovieBonus applyMovieBonusMock = mock(ApplyMovieBonus.class);
        when(charactersMock.getListOf(Hamster.class)).thenReturn(new ArrayList<>());

        gameVisitor.visit(applyMovieBonusMock);

        verify(applyMovieBonusMock).execute(charactersMock.getListOf(Hamster.class), moviesMock);
    }

    @Test
    void whenVisitEliminateCharacters_thenEliminateCharactersIsExecuted() {
        EliminateCharacters eliminateCharactersMock = mock(EliminateCharacters.class);

        gameVisitor.visit(eliminateCharactersMock);

        verify(eliminateCharactersMock).execute(charactersMock, moviesMock);
    }

    @Test
    void whenVisitUpdateMoviesNextTurn_thenUpdateMoviesNextTurnIsExecuted() {
        UpdateMoviesNextTurn updateMoviesNextTurnMock = mock(UpdateMoviesNextTurn.class);

        gameVisitor.visit(updateMoviesNextTurnMock);

        verify(updateMoviesNextTurnMock).execute(moviesMock, charactersMock);
    }

    @Test
    void whenVisitChinchillasSendHamstagramRequests_thenChinchillasSendHamstagramRequestsIsExecuted() {
        ChinchillasSendHamstagramRequests chinchillasSendHamstagramRequestsMock = mock(ChinchillasSendHamstagramRequests.class);

        gameVisitor.visit(chinchillasSendHamstagramRequestsMock);

        verify(chinchillasSendHamstagramRequestsMock).execute(charactersMock);
    }

    @Test
    void whenVisitHamsterChooseBestHamstagramRequest_thenHamsterChooseBestHamstagramRequestIsExecuted() {
        HamsterChooseBestHamstagramRequest hamsterChooseBestHamstagramRequestMock = mock(HamsterChooseBestHamstagramRequest.class);

        gameVisitor.visit(hamsterChooseBestHamstagramRequestMock);

        verify(hamsterChooseBestHamstagramRequestMock).execute(charactersMock);
    }

    @Test
    void whenVisitRealityShow_thenRhalityShowIsExecuted() {
        RealityShow realityShowMock = mock(RealityShow.class);

        gameVisitor.visit(realityShowMock);

        verify(realityShowMock).execute(charactersMock, moviesMock);
    }

    @Test
    void whenVisitPromoteMovie_thenPromoteMovieIsExecuted() {
        PromoteMovie promoteMovieMock = mock(PromoteMovie.class);
        when(charactersMock.getListOf(Hamster.class)).thenReturn(new ArrayList<>());
        when(charactersMock.getListOf(Chinchilla.class)).thenReturn(new ArrayList<>());

        gameVisitor.visit(promoteMovieMock);

        verify(promoteMovieMock).execute(charactersMock, moviesMock);
    }

    @Test
    void whenVisitSpreadGossip_thenSpreadGossipIsExecuted() {
        SpreadGossip spreadGossipMock = mock(SpreadGossip.class);

        gameVisitor.visit(spreadGossipMock);

        verify(spreadGossipMock).execute(charactersMock, moviesMock, TURN_NUMBER);
    }

    @Test
    void whenVisitScandal_thenScandalIsExecuted() {
        Scandal scandalMock = mock(Scandal.class);

        gameVisitor.visit(scandalMock);

        verify(scandalMock).execute(charactersMock, moviesMock, TURN_NUMBER);
    }

    @Test
    void whenVisitHarassmentComplaint_thenHarassmentComplaintIsExecuted() {
        HarassmentComplaint harassmentComplaintMock = mock(HarassmentComplaint.class);

        gameVisitor.visit(harassmentComplaintMock);

        verify(harassmentComplaintMock).execute(charactersMock, moviesMock, TURN_NUMBER);
    }

    @Test
    void whenVisitFindLawyer_thenFindLawyerIsExecuted() {
        FindLawyer findLawyerMock = mock(FindLawyer.class);

        gameVisitor.visit(findLawyerMock);

        verify(findLawyerMock).execute(charactersMock);
    }

    @Test
    void whenVisitPayLawyers_thenPayLawyersIsExecuted() {
        PayLawyers payLawyersMock = mock(PayLawyers.class);

        gameVisitor.visit(payLawyersMock);

        verify(payLawyersMock).execute(charactersMock);
    }

    @Test
    void whenVisitResolveLawsuits_thenResolveLawsuitsIsExecuted() {
        ResolveLawsuits resolveLawsuitsMock = mock(ResolveLawsuits.class);

        gameVisitor.visit(resolveLawsuitsMock);

        verify(resolveLawsuitsMock).execute(charactersMock);
    }
}