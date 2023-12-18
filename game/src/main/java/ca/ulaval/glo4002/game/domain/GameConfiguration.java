package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.actions.ActionFactory;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.CharactersRepo;
import ca.ulaval.glo4002.game.domain.movie.MovieFactory;
import ca.ulaval.glo4002.game.domain.movie.MoviesRepo;
import ca.ulaval.glo4002.game.domain.turn.TurnRepo;
import ca.ulaval.glo4002.game.infrastructure.persistence.CharactersInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.MoviesInMemory;
import ca.ulaval.glo4002.game.infrastructure.persistence.TurnInMemory;
import ca.ulaval.glo4002.game.interfaces.rest.character.CharacterMapper;
import ca.ulaval.glo4002.game.interfaces.rest.hamstagram.HamstagramAccountMapper;
import ca.ulaval.glo4002.game.interfaces.rest.lawsuits.LawsuitsMapper;
import ca.ulaval.glo4002.game.interfaces.rest.movie.MovieMapper;
import ca.ulaval.glo4002.game.interfaces.rest.rattedIn.RattedInAccountMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class GameConfiguration extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(GameService.class);
        bind(TurnInMemory.class).to(TurnRepo.class);
        bind(CharactersInMemory.class).to(CharactersRepo.class);
        bind(MoviesInMemory.class).to(MoviesRepo.class);
        bindAsContract(CharacterFactory.class);
        bindAsContract(ActionFactory.class);
        bindAsContract(MovieFactory.class);
        bindAsContract(CharacterMapper.class);
        bindAsContract(HamstagramAccountMapper.class);
        bindAsContract(MovieMapper.class);
        bindAsContract(RattedInAccountMapper.class);
        bindAsContract(LawsuitsMapper.class);
    }
}
