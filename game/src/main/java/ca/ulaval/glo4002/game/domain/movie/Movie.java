package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.exceptions.InvalidMovieStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Movie {
    private final String title;
    private final BoxOffice boxOffice;
    List<String> casting;
    List<String> potentialCasting;
    private MovieStatus status;

    public Movie(List<String> casting, List<String> potentialCasting, String title, BoxOffice boxOffice, MovieStatus status) {
        this.casting = casting;
        this.potentialCasting = potentialCasting;
        this.title = title;
        this.boxOffice = boxOffice;
        this.status = status;
    }

    public Movie(String title) {
        this(new ArrayList<>(), new ArrayList<>(), title, new BoxOffice(), MovieStatus.CREATING);
    }

    public List<String> getCasting() {
        if (status.equals(MovieStatus.BOX_OFFICE)) {
            return new ArrayList<>();
        } else {
            return casting;
        }
    }

    public List<String> getPotentialCasting() {
        return potentialCasting;
    }

    public String getTitle() {
        return title;
    }

    public int getBoxOfficeAmount() {
        return boxOffice.getAmount();
    }

    public MovieStatus getStatus() {
        return status;
    }

    protected void setStatus(MovieStatus status) {
        this.status = status;
    }

    public abstract MovieType getType();

    public void updateNextTurn(Characters characters) {
        switch (status) {
            case CREATING -> setStatus(MovieStatus.POTENTIAL_CASTING);
            case POTENTIAL_CASTING -> updateNextTurnPotentialCasting(characters);
            case FILMING -> updateNextTurnFilming(characters);
            case SCREENING -> updateNextTurnScreening(characters);
            case BOX_OFFICE -> updateNextTurnBoxOffice(characters);
            case ENDED -> {
            }
            default -> throw new InvalidMovieStatusException(status);
        }
    }

    private void updateNextTurnPotentialCasting(Characters characters) {
        selectPotentialCasting(characters);
        if (hasCompletePotentialCasting(characters)) {
            setStatus(MovieStatus.FILMING);
        }
    }

    private void updateNextTurnFilming(Characters characters) {
        if (hasCompletePotentialCasting(characters)) {
            chooseCasting(characters);
            startFilming(characters);
            setStatus(MovieStatus.SCREENING);
        } else {
            setStatus(MovieStatus.POTENTIAL_CASTING);
            updateNextTurn(characters);
        }
    }

    private void updateNextTurnScreening(Characters characters) {
        if (hasCompleteCasting()) {
            endFilming(characters);
            setStatus(MovieStatus.BOX_OFFICE);
        } else {
            selectPotentialCasting(characters);
            setStatus(MovieStatus.FILMING);
            updateNextTurn(characters);
        }
    }

    private void updateNextTurnBoxOffice(Characters characters) {
        boxOffice.calculateAmount(casting, characters);
        casting.clear();
        setStatus(MovieStatus.ENDED);
    }

    protected void addToPotentialCasting(String actorName) {
        potentialCasting.add(actorName);
    }

    protected void addToCasting(String actorName) {
        casting.add(actorName);
    }

    private void selectPotentialCasting(Characters characters) {
        List<Hamster> hamsters = characters.getListOf(Hamster.class);
        potentialCasting.clear();
        for (Hamster hamster : hamsters) {
            if (hamster.canBeCast()) {
                addToPotentialCasting(hamster.getName());
            }
        }
    }

    private boolean hasCompletePotentialCasting(Characters characters) {
        potentialCasting.removeIf(hamsterName -> {
            Optional<Hamster> hamster = characters.getByNameAndFilter(hamsterName, Hamster.class);
            return hamster.map(value -> !value.canBeCast()).orElse(true);
        });
        return potentialCasting.size() + casting.size() >= 2;
    }

    private boolean hasCompleteCasting() {
        return casting.size() >= 2;
    }

    public void removeHamster(String actorName) {
        potentialCasting.remove(actorName);
        casting.remove(actorName);
    }

    public void removeEliminatedHamsters(List<String> hamstersAlive) {
        potentialCasting.removeIf(hamsterName -> !hamstersAlive.contains(hamsterName));
        casting.removeIf(hamsterName -> !hamstersAlive.contains(hamsterName));
    }

    public abstract void chooseCasting(Characters characters);

    public void payCharacters(Characters characters) {
        payHamstersSalary(characters);
        payCharactersBonus(characters);
    }

    private void payHamstersSalary(Characters characters) {
        if (status.equals(MovieStatus.SCREENING)) {
            for (String actorName : casting) {
                characters.getByNameAndFilter(actorName, Hamster.class).ifPresent(Hamster::receiveSalary);
            }
        }
    }

    private void payCharactersBonus(Characters characters) {
        if (status.equals(MovieStatus.BOX_OFFICE)) {
            for (String actorName : casting) {
                characters.getByNameAndFilter(actorName, Hamster.class).ifPresent(boxOffice::payCharacterBonus);
            }
        }
    }

    private void startFilming(Characters characters) {
        potentialCasting.clear();
        for (String actorName : casting) {
            characters.getByNameAndFilter(actorName, Hamster.class).ifPresent(Hamster::startFilming);
        }
    }

    private void endFilming(Characters characters) {
        for (String actorName : casting) {
            characters.getByNameAndFilter(actorName, Hamster.class).ifPresent(Hamster::endFilming);
        }
    }

    public void applyBonus() {
        boxOffice.doubleRevenue();
    }
}