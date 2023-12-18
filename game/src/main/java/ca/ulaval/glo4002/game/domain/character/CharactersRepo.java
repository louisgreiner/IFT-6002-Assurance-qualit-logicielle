package ca.ulaval.glo4002.game.domain.character;

import java.util.List;

public interface CharactersRepo {
    void create(Character character);

    Character findByName(String name);

    void deleteAll();

    Characters getAll();

    void saveAll(Characters characters);

    void updateHamsters(List<Hamster> casting);
}