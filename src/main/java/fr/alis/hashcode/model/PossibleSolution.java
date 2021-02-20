package fr.alis.hashcode.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class PossibleSolution {
    private EvenMorePizzaInput input;
    private List<Team> teams = new ArrayList<>();
    private List<Pizza> availablePizza;
    private Random randomGenerator = new Random();
    private int populationSize;

    public PossibleSolution(EvenMorePizzaInput input) {
        this.input = input;
        populationSize = input.getAvailablePizza();
        availablePizza = input.getPizzas();
        initialize();
    }

    private void initialize() {
        int members = 0;
        do {
            members = randomGenerator.nextInt(2) + 2;
            Team team = new Team();
            team.setMembers(members);
            if (members <= availablePizza.size() && teamAvailable(members)) {
                for (int j = 0; j < members; j++) {
                    int pizzaIndex = randomGenerator.nextInt(availablePizza.size());
                    Pizza pizza = availablePizza.get(pizzaIndex);
                    team.getPizzas().add(pizza);
                    availablePizza.remove(pizzaIndex);
                }
                teams.add(team);
                decreaseTeam(members);
            }
        } while (members <= availablePizza.size() || availablePizza.size() > 1 );
    }

    private void decreaseTeam(int members) {
        if (members == 2) {
            input.setTeamsOfTwo(input.getTeamsOfTwo() - 1);
        }
        else if (members == 3) {
            input.setTeamsOfThree(input.getTeamsOfThree() - 1);
        }
        else {
            input.setTeamsOfFour(input.getTeamsOfFour() - 1);
        }
    }

    private boolean teamAvailable(int members) {
        if (members == 2) {
            return input.getTeamsOfTwo() > 0;
        }
        else if (members == 3) {
            return input.getTeamsOfThree() > 0;
        }
        else {
            return input.getTeamsOfFour() > 0;
        }
    }

    public long getScore() {
        return teams.stream().mapToLong(Team::getScore).sum();
    }
}
