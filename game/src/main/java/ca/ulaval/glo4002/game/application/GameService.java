package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionFactory;
import ca.ulaval.glo4002.game.domain.actions.user.character.CharacterActionCode;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.*;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.MovieType;
import ca.ulaval.glo4002.game.domain.movie.MoviesRepo;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.turn.Turn;
import ca.ulaval.glo4002.game.domain.turn.TurnRepo;
import jakarta.inject.Inject;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final TurnRepo turnRepo;
    private final CharactersRepo charactersRepo;
    private final MoviesRepo moviesRepo;
    private final CharacterFactory characterFactory;
    private final MovieFactory movieFactory;
    private final ActionFactory actionFactory;

    @Inject
    public GameService(TurnRepo turnRepo, CharactersRepo charactersRepo, MoviesRepo moviesRepo, CharacterFactory characterFactory, MovieFactory movieFactory, ActionFactory actionFactory) {
        this.turnRepo = turnRepo;
        this.charactersRepo = charactersRepo;
        this.moviesRepo = moviesRepo;
        this.characterFactory = characterFactory;
        this.movieFactory = movieFactory;
        this.actionFactory = actionFactory;
    }

    public int nextTurn() {
        Turn turn = turnRepo.get();
        Characters characters = charactersRepo.getAll();
        List<Movie> movies = moviesRepo.getAll();

        setUpNextTurnActions(turn);

        turn.nextTurn(characters, movies);

        updateAllRepos(turn.getGameVisitor().getCharacters(), turn.getGameVisitor().getMovies(), turn);
        return turn.getTurnNumber();
    }

    private void setUpNextTurnActions(Turn turn) {
        turn.addAction(actionFactory.updateHamstagramAccountsNextTurn());
        turn.addAction(actionFactory.moviesPayCharacters());
        turn.addAction(actionFactory.applyMovieBonus());
        turn.addAction(actionFactory.hamstersPayRepresentedBy());
        turn.addAction(actionFactory.updateCharactersNextTurn());
        turn.addAction(actionFactory.updateUnavailability());
        turn.addAction(actionFactory.eliminateCharacters());
        turn.addAction(actionFactory.updateMoviesNextTurn());
        turn.addAction(actionFactory.chinchillasSendHamstagramRequests());
        turn.addAction(actionFactory.hamsterChooseBestHamstagramRequest());
        turn.addAction(actionFactory.findLawyer());
        turn.addAction(actionFactory.payLawyers());
        turn.addAction(actionFactory.resolveLawsuits());
    }

    void updateAllRepos(Characters characters, List<Movie> movies, Turn turn) {
        charactersRepo.saveAll(characters);
        moviesRepo.saveAll(movies);
        turnRepo.update(turn);
    }

    public void reset() {
        Turn turn = turnRepo.get();
        turn.reset();
        charactersRepo.deleteAll();
        moviesRepo.deleteAll();
        turnRepo.update(turn);
    }

    public void createCharacter(String name, Type type, int salary) {
        Turn turn = turnRepo.get();
        Character character = characterFactory.create(name, type, salary);
        Action createCharacterAction = actionFactory.createCharacter(character);
        turn.addAction(createCharacterAction);
        turnRepo.update(turn);
    }

    public Character getCharacter(String name) {
        return charactersRepo.findByName(name);
    }

    public Optional<HamstagramAccount> getHamstagramAccount(String ownerName) {
        Characters characters = charactersRepo.getAll();
        Optional<HamstagramUser> hamstagramUser = characters.getByNameAndFilter(ownerName, HamstagramUser.class);
        return hamstagramUser.map(HamstagramUser::getHamstagramAccount);
    }

    public void createNewRattedInContactRequest(String senderName, String receiverName) {
        Turn turn = turnRepo.get();
        Action createRattedInRequestAction = actionFactory.rattedInRequest(senderName, receiverName);
        turn.addAction(createRattedInRequestAction);
        turnRepo.update(turn);
    }

    public Optional<RattedInAccount> getRattedInAccount(String userName) {
        Characters characters = charactersRepo.getAll();
        Optional<RattedInUser> rattedInUser = characters.getByNameAndFilter(userName, RattedInUser.class);
        return rattedInUser.map(RattedInUser::getRattedInAccount);
    }

    public void createMovie(String title, MovieType type) {
        Turn turn = turnRepo.get();
        Movie movie = movieFactory.create(title, type);
        Action createMovieAction = actionFactory.createMovie(movie);
        turn.addCreateMovieAction(createMovieAction);
        turnRepo.update(turn);
    }

    public List<Movie> getMovies() {
        return moviesRepo.getAll();
    }

    public void createCharacterAction(String from, String to, CharacterActionCode actionCode) {
        Turn turn = turnRepo.get();
        Action createCharacterAction = actionFactory.createCharacterAction(from, to, actionCode);
        turn.addAction(createCharacterAction, from, to);
        turnRepo.update(turn);
    }

    public List<Lawsuit> getLawsuits() {
        List<Character> characters = charactersRepo.getAll();
        List<Lawsuit> lawsuits = new ArrayList<>();
        for (Character character : characters) {
            lawsuits.addAll(character.getLawsuits());
        }
        return lawsuits;
    }
}
