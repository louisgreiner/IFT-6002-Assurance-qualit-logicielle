package ca.ulaval.glo4002.game.domain.actions;

import ca.ulaval.glo4002.game.domain.actions.nextTurn.*;
import ca.ulaval.glo4002.game.domain.actions.user.CreateCharacter;
import ca.ulaval.glo4002.game.domain.actions.user.CreateMovie;
import ca.ulaval.glo4002.game.domain.actions.user.RattedInRequest;
import ca.ulaval.glo4002.game.domain.actions.user.character.*;
import ca.ulaval.glo4002.game.domain.actions.user.character.exceptions.InvalidActionCodeException;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.movie.Movie;

public class ActionFactory {
    // API actions
    public Action createCharacter(Character character) {
        return new CreateCharacter(character);
    }

    public Action rattedInRequest(String senderName, String receiverName) {
        return new RattedInRequest(senderName, receiverName);
    }

    public Action createMovie(Movie movie) {
        return new CreateMovie(movie);
    }

    // Next turn actions
    public Action updateHamstagramAccountsNextTurn() {
        return new UpdateHamstagramAccountsNextTurn();
    }

    public Action moviesPayCharacters() {
        return new MoviesPayCharacters();
    }

    public Action hamstersPayRepresentedBy() {
        return new HamstersPayRepresentedBy();
    }

    public Action updateCharactersNextTurn() {
        return new UpdateCharactersNextTurn();
    }

    public Action updateUnavailability() {
        return new UpdateUnavailability();
    }

    public Action applyMovieBonus() {
        return new ApplyMovieBonus();
    }

    public Action eliminateCharacters() {
        return new EliminateCharacters();
    }

    public Action updateMoviesNextTurn() {
        return new UpdateMoviesNextTurn();
    }

    public Action chinchillasSendHamstagramRequests() {
        return new ChinchillasSendHamstagramRequests();
    }

    public Action hamsterChooseBestHamstagramRequest() {
        return new HamsterChooseBestHamstagramRequest();
    }

    public Action findLawyer() {
        return new FindLawyer();
    }

    public Action payLawyers() {
        return new PayLawyers();
    }

    public Action resolveLawsuits() {
        return new ResolveLawsuits();
    }

    // Game actions
    public Action createCharacterAction(String from, String to, CharacterActionCode characterActionCode) {
        return switch (characterActionCode) {
            case RS -> new RealityShow(from);
            case PO -> new PromoteMovie(from);
            case FR -> new SpreadGossip(from, to);
            case SC -> new Scandal(from, to);
            case PL -> new HarassmentComplaint(from, to);
            default -> throw new InvalidActionCodeException(characterActionCode.toString());
        };
    }
}
