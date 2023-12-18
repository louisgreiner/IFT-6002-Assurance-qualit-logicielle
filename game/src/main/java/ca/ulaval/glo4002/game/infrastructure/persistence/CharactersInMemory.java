package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.CharactersRepo;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import org.jvnet.hk2.annotations.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CharactersInMemory implements CharactersRepo {
    private static final LinkedHashMap<String, Character> characters = new LinkedHashMap<>();

    @Override
    public void create(Character character) {
        characters.putIfAbsent(character.getName(), character);
    }

    @Override
    public Character findByName(String name) {
        Character character = characters.get(name);
        if (character == null) {
            throw new NotFoundCharacterException(name);
        }
        return character;
    }

    public boolean isEmpty() {
        return characters.isEmpty();
    }

    @Override
    public void deleteAll() {
        characters.clear();
    }

    @Override
    public Characters getAll() {
        return new Characters(new ArrayList<>(characters.values()));
    }

    @Override
    public void saveAll(Characters charactersUpdated) {
        deleteAll();
        for (Character character : charactersUpdated) {
            create(character);
        }
    }

    @Override
    public void updateHamsters(List<Hamster> casting) {
        for (Character character : casting) {
            characters.replace(character.getName(), character);
        }
    }
}
