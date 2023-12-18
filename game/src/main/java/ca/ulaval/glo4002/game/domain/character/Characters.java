package ca.ulaval.glo4002.game.domain.character;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Characters extends ArrayList<Character> {

    public Characters() {
        super();
    }

    public Characters(List<Character> characters) {
        super(characters);
    }

    public <T> Optional<T> getByNameAndFilter(String name, Class<T> characterType) {
        return this.stream()
                .filter(character -> character.getName().equals(name) && characterType.isInstance(character))
                .map(characterType::cast)
                .findFirst();
    }

    public Optional<Character> getByName(String from) {
        return this.stream()
                .filter(character -> character.getName().equals(from))
                .findFirst();
    }

    public <T> List<T> getListOf(Class<T> characterType) {
        return this.stream()
                .filter(characterType::isInstance)
                .map(characterType::cast)
                .toList();
    }

    public <T> List<String> getNameListOf(Class<T> characterType) {
        return this.stream()
                .filter(characterType::isInstance)
                .map(Character::getName)
                .toList();
    }
}
