package ca.ulaval.glo4002.game.domain.actions;

import ca.ulaval.glo4002.game.domain.actions.nextTurn.*;
import ca.ulaval.glo4002.game.domain.actions.user.CreateCharacter;
import ca.ulaval.glo4002.game.domain.actions.user.CreateMovie;
import ca.ulaval.glo4002.game.domain.actions.user.RattedInRequest;
import ca.ulaval.glo4002.game.domain.actions.user.character.*;

public interface Visitor {
    // API actions
    void visit(CreateCharacter createCharacter);

    void visit(RattedInRequest rattedInRequest);

    void visit(CreateMovie createMovie);

    // Next turn actions
    void visit(UpdateHamstagramAccountsNextTurn updateHamstagramAccountsNextTurn);

    void visit(MoviesPayCharacters moviesPayCharacters);

    void visit(HamstersPayRepresentedBy hamstersPayRepresentedBy);

    void visit(UpdateCharactersNextTurn updateCharactersNextTurn);

    void visit(UpdateUnavailability updateUnavailability);

    void visit(ApplyMovieBonus applyMovieBonus);

    void visit(EliminateCharacters eliminateCharacters);

    void visit(UpdateMoviesNextTurn updateMoviesNextTurn);

    void visit(ChinchillasSendHamstagramRequests chinchillasSendHamstagramRequests);

    void visit(HamsterChooseBestHamstagramRequest hamsterChooseBestHamstagramRequest);

    // Game actions
    void visit(RealityShow realityShow);

    void visit(PromoteMovie promoteMovie);

    void visit(Scandal scandal);

    void visit(SpreadGossip spreadGossip);

    void visit(HarassmentComplaint harrasmentComplaint);

    void visit(FindLawyer findLawyer);

    void visit(PayLawyers payLawyers);

    void visit(ResolveLawsuits resolveLawsuits);
}
