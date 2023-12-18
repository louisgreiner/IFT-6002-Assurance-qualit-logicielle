package ca.ulaval.glo4002.game.domain.actions;

import ca.ulaval.glo4002.game.domain.actions.nextTurn.*;
import ca.ulaval.glo4002.game.domain.actions.user.CreateCharacter;
import ca.ulaval.glo4002.game.domain.actions.user.CreateMovie;
import ca.ulaval.glo4002.game.domain.actions.user.RattedInRequest;
import ca.ulaval.glo4002.game.domain.actions.user.character.*;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class GameVisitor implements Visitor {
    private final Characters characters;
    private final List<Movie> movies;
    private final int turnNumber;

    public GameVisitor(Characters characters, List<Movie> movies, int turnNumber) {
        this.characters = characters;
        this.movies = movies;
        this.turnNumber = turnNumber;
    }

    public Characters getCharacters() {
        return characters;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    // API actions
    public void visit(CreateCharacter createCharacter) {
        createCharacter.execute(characters);
    }

    public void visit(RattedInRequest rattedInRequest) {
        rattedInRequest.execute(characters);
    }

    public void visit(CreateMovie createMovie) {
        createMovie.execute(movies);
    }

    // Next turn actions
    public void visit(UpdateHamstagramAccountsNextTurn updateHamstagramAccountsNextTurn) {
        updateHamstagramAccountsNextTurn.execute(characters);
    }

    public void visit(MoviesPayCharacters moviesPayCharacters) {
        moviesPayCharacters.execute(movies, characters);
    }

    public void visit(HamstersPayRepresentedBy hamstersPayRepresentedBy) {
        hamstersPayRepresentedBy.execute(characters);
    }

    public void visit(UpdateCharactersNextTurn updateCharactersNextTurn) {
        updateCharactersNextTurn.execute(characters);
    }

    public void visit(UpdateUnavailability updateUnavailability) {
        updateUnavailability.execute(characters);
    }

    public void visit(ApplyMovieBonus applyMovieBonus) {
        applyMovieBonus.execute(characters.getListOf(Hamster.class), movies);
    }

    public void visit(EliminateCharacters eliminateCharacters) {
        eliminateCharacters.execute(characters, movies);
    }

    public void visit(UpdateMoviesNextTurn updateMoviesNextTurn) {
        updateMoviesNextTurn.execute(movies, characters);
    }

    public void visit(ChinchillasSendHamstagramRequests chinchillasSendHamstagramRequests) {
        chinchillasSendHamstagramRequests.execute(characters);
    }

    public void visit(HamsterChooseBestHamstagramRequest hamsterChooseBestHamstagramRequest) {
        hamsterChooseBestHamstagramRequest.execute(characters);
    }

    // Game actions
    public void visit(RealityShow realityShow) {
        realityShow.execute(characters, movies);
    }

    public void visit(PromoteMovie promoteMovie) {
        promoteMovie.execute(characters, movies);
    }

    public void visit(SpreadGossip spreadGossip) {
        spreadGossip.execute(characters, movies, turnNumber);
    }

    public void visit(Scandal scandal) {
        scandal.execute(characters, movies, turnNumber);
    }

    public void visit(HarassmentComplaint harrasmentComplaint) {
        harrasmentComplaint.execute(characters, movies, turnNumber);
    }

    public void visit(FindLawyer resolveLawsuit) {
        resolveLawsuit.execute(characters);
    }

    public void visit(PayLawyers payLawyers) {
        payLawyers.execute(characters);
    }

    public void visit(ResolveLawsuits resolveLawsuits) {
        resolveLawsuits.execute(characters);
    }
}
