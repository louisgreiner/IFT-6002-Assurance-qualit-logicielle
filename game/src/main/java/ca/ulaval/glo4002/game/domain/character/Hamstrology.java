package ca.ulaval.glo4002.game.domain.character;

import java.util.List;

public class Hamstrology {
    public HamstagramManager chooseChinchilla(List<HamstagramManager> managers, Hamster hamster) {
        String hamsterName = hamster.getName();
        HamstagramManager managerSelected = managers.get(0);
        int asciiDistance = Integer.MAX_VALUE;

        for (HamstagramManager newManager : managers) {
            String chinchillaName = newManager.getName();

            int distanceChar = computeDistanceOfFirstChar(chinchillaName, hamsterName);

            if (distanceChar < asciiDistance) {
                asciiDistance = distanceChar;
                managerSelected = newManager;
            } else if (distanceChar == asciiDistance && shouldChooseNewChinchilla(newManager, managerSelected)) {
                managerSelected = newManager;
            }
        }
        managerSelected.representNewHamster(hamster);

        return managerSelected;
    }

    int computeDistanceOfFirstChar(String chinchillaName, String hamsterName) {
        char firstChinchillaChar = chinchillaName.charAt(0);
        char firstHamsterChar = hamsterName.charAt(0);

        return Math.abs(firstChinchillaChar - firstHamsterChar);
    }

    boolean shouldChooseNewChinchilla(HamstagramManager newManager, HamstagramManager currentManager) {
        int managerReputationDifference = newManager.getReputation() - currentManager.getReputation();
        return managerReputationDifference > 0 || (managerReputationDifference == 0 && getRandomBoolean());
    }

    private boolean getRandomBoolean() {
        double threshold = 0.5;
        return Math.random() < threshold;
    }
}