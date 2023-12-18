package ca.ulaval.glo4002.game.application;

import ca.ulaval.glo4002.game.domain.actions.ActionFactory;
import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameServiceTest {
    public static final String NAME = "TestName";

    private GameService gameService;
    private CharactersRepo charactersRepoMock;
    private TurnRepo turnRepoMock;
    private ActionFactory actionFactoryMock;
    private MoviesRepo moviesRepoMock;
    private Turn turnMock;
    private Character characterMock;
    private CharacterFactory characterFactoryMock;
    private MovieFactory movieFactoryMock;
    private Movie movieMock;
    private Characters charactersMock;

    @BeforeEach
    void initMocks() {
        turnRepoMock = mock(TurnRepo.class);
        charactersRepoMock = mock(CharactersRepo.class);
        characterFactoryMock = mock(CharacterFactory.class);
        movieFactoryMock = mock(MovieFactory.class);
        actionFactoryMock = mock(ActionFactory.class);
        moviesRepoMock = mock(MoviesRepo.class);
        turnMock = mock(Turn.class);
        characterMock = mock(Character.class);
        when(characterMock.getType()).thenReturn(Type.RAT);
        movieMock = mock(Movie.class);
        charactersMock = mock(Characters.class);
        when(charactersRepoMock.getAll()).thenReturn(new Characters(List.of(characterMock)));
        gameService = new GameService(turnRepoMock, charactersRepoMock, moviesRepoMock, characterFactoryMock, movieFactoryMock, actionFactoryMock);
    }

    @Test
    void whenNextTurn_thenCallNextTurn() {
        when(turnRepoMock.get()).thenReturn(turnMock);

        GameVisitor gameVisitorMock = mock(GameVisitor.class);
        when(turnMock.getGameVisitor()).thenReturn(gameVisitorMock);

        gameService.nextTurn();

        verify(turnMock, times(1)).nextTurn(any(), any());
    }

    @Test
    void whenNextTurn_thenCallAllOrchestrationActions() {
        when(turnRepoMock.get()).thenReturn(turnMock);
        GameVisitor gameVisitorMock = mock(GameVisitor.class);
        when(turnMock.getGameVisitor()).thenReturn(gameVisitorMock);

        gameService.nextTurn();

        verify(turnRepoMock, times(1)).get();
        verify(turnMock, times(1)).nextTurn(any(), any());
        verify(charactersRepoMock, times(1)).getAll();
        verify(moviesRepoMock, times(1)).getAll();
        verify(actionFactoryMock, times(1)).updateHamstagramAccountsNextTurn();
        verify(actionFactoryMock, times(1)).moviesPayCharacters();
        verify(actionFactoryMock, times(1)).applyMovieBonus();
        verify(actionFactoryMock, times(1)).hamstersPayRepresentedBy();
        verify(actionFactoryMock, times(1)).updateCharactersNextTurn();
        verify(actionFactoryMock, times(1)).updateUnavailability();
        verify(actionFactoryMock, times(1)).eliminateCharacters();
        verify(actionFactoryMock, times(1)).updateMoviesNextTurn();
        verify(actionFactoryMock, times(1)).chinchillasSendHamstagramRequests();
        verify(actionFactoryMock, times(1)).hamsterChooseBestHamstagramRequest();
    }

    @Test
    void whenReset_thenCallDeleteAll() {
        when(turnRepoMock.get()).thenReturn(mock(Turn.class));

        gameService.reset();

        verify(charactersRepoMock).deleteAll();
    }

    @Test
    void whenGetCharacter_thenCallFindByName() {
        gameService.getCharacter(NAME);

        verify(charactersRepoMock).findByName(NAME);
    }

    @Test
    void whenGetHamstagramAccount_thenCallGetHamstagramAccount() {
        Chinchilla chinchillaMock = mock(Chinchilla.class);
        when(charactersRepoMock.getAll()).thenReturn(new Characters(List.of(chinchillaMock)));
        when(chinchillaMock.getName()).thenReturn(NAME);


        gameService.getHamstagramAccount(NAME);

        verify(chinchillaMock).getHamstagramAccount();
    }

    @Test
    void givenNoCharacterFound_whenGetHamstagramAccount_thenReturnEmpty() {
        when(charactersRepoMock.getAll()).thenReturn(charactersMock);
        when(charactersMock.getByNameAndFilter(anyString(), eq(HamstagramUser.class))).thenReturn(Optional.empty());

        Optional<HamstagramAccount> hamstagramAccount = gameService.getHamstagramAccount(NAME);

        assertTrue(hamstagramAccount.isEmpty());
    }

    @Test
    void whenCreateCharacter_thenCallActionCreateCharacter() {
        when(turnRepoMock.get()).thenReturn(turnMock);
        Type characterTypeHamster = Type.HAMSTER;
        int salary = 1000;
        when(characterFactoryMock.create(NAME, characterTypeHamster, salary)).thenReturn(characterMock);

        gameService.createCharacter(NAME, characterTypeHamster, salary);

        verify(turnMock).addAction(actionFactoryMock.createCharacter(characterMock));
    }

    @Test
    void whenCreateNewRattedInContactRequest_thenCallActionRattedInRequest() {
        String senderName = "Sender";
        String receiverName = "Receiver";
        when(turnRepoMock.get()).thenReturn(turnMock);

        gameService.createNewRattedInContactRequest(senderName, receiverName);

        verify(turnMock).addAction(actionFactoryMock.rattedInRequest(senderName, receiverName));
    }

    @Test
    void whenGetRattedInAccount_thenCallGetRattedInAccount() {
        Rat ratMock = mock(Rat.class);
        when(charactersRepoMock.getAll()).thenReturn(new Characters(List.of(ratMock)));
        when(ratMock.getName()).thenReturn(NAME);

        gameService.getRattedInAccount(NAME);

        verify(ratMock).getRattedInAccount();
    }

    @Test
    void givenNoCharacterFound_whenGetRattedInAccount_thenReturnEmpty() {
        when(charactersRepoMock.getAll()).thenReturn(charactersMock);
        when(charactersMock.getByNameAndFilter(anyString(), eq(RattedInUser.class))).thenReturn(Optional.empty());

        Optional<RattedInAccount> rattedInAccount = gameService.getRattedInAccount(NAME);

        assertTrue(rattedInAccount.isEmpty());
    }

    @Test
    void whenUpdateAllRepos_thenCallSaveAll() {
        gameService.updateAllRepos(new Characters(), new ArrayList<>(), turnMock);

        verify(charactersRepoMock).saveAll(any());
        verify(moviesRepoMock).saveAll(any());
        verify(turnRepoMock).update(turnMock);
    }

    @Test
    void whenCreateMovie_thenCallCreateMovieAction() {
        when(turnRepoMock.get()).thenReturn(turnMock);
        MovieType movieTypeA = MovieType.A;
        String movieTitle = "title";
        when(movieFactoryMock.create(movieTitle, movieTypeA)).thenReturn(movieMock);

        gameService.createMovie(movieTitle, movieTypeA);

        verify(turnMock).addCreateMovieAction(actionFactoryMock.createMovie(movieMock));
    }

    @Test
    void whenGetMovies_thenCallGetAll() {
        gameService.getMovies();

        verify(moviesRepoMock).getAll();
    }

    @Test
    void whenCreateGameAction_thenCallCreateGameAction() {
        when(turnRepoMock.get()).thenReturn(turnMock);
        String characterNameFrom = "from";
        String characterNameTo = "to";
        CharacterActionCode actionCode = CharacterActionCode.FR;

        gameService.createCharacterAction(characterNameFrom, characterNameTo, actionCode);

        verify(actionFactoryMock).createCharacterAction(characterNameFrom, characterNameTo, actionCode);
    }

    @Test
    void givenACharacterWithALawsuit_whenGetLawsuits_thenReturnLawsuit() {
        Lawsuit lawsuit = new Lawsuit(1, NAME, "SC", null);
        List<Lawsuit> lawsuits = new ArrayList<>();
        lawsuits.add(lawsuit);
        when(characterMock.getLawsuits()).thenReturn(lawsuits);

        List<Lawsuit> returnedLawsuits = gameService.getLawsuits();

        assertEquals(lawsuits, returnedLawsuits);
    }
}