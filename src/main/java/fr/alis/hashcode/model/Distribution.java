package fr.alis.hashcode.model;

import fr.alis.hashcode.engine.Gene;
import fr.alis.hashcode.engine.PossibleSolution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Distribution implements PossibleSolution<Pizza, EvenMorePizzaOutput> {
    private EvenMorePizzaInput input;
    private List<Team> teams = new ArrayList<>();
    private List<Pizza> availablePizza;
    private Random randomGenerator = new Random();

    public Distribution(EvenMorePizzaInput input) {
        this.input = input;
        availablePizza = input.getPizzas();
        initialize();
    }

    public PossibleSolution<Pizza, EvenMorePizzaOutput> copy() {
        return Distribution.builder()
                .availablePizza(this.availablePizza)
                .input(input.copy())
                .randomGenerator(randomGenerator)
                .teams(new ArrayList<>(teams))
                .build();
    }

    private void initialize() {
        int members = 0;
        do {
            members = randomGenerator.nextInt(3) + 2;
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
        } while (members <= availablePizza.size() && deliverable(availablePizza.size()));
    }

    private boolean deliverable(int size) {
        int dividableBy2 = size / 2;
        int dividableBy3 = size / 3;
        return (dividableBy2 >= 1 || dividableBy3 >= 1)
                && ((dividableBy2 > 0 && teamAvailable(2))
                || (dividableBy3 > 0 && teamAvailable(3)));
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

    @Override
    public EvenMorePizzaOutput asOutput() {
        return EvenMorePizzaOutput.builder()
                .totalTeams(teams.size())
                .orders(getOrders())
                .score(getScore())
                .build();
    }

    private List<PizzaOrder> getOrders() {
        return teams.stream().map(team ->
                PizzaOrder.builder()
                        .total(team.getMembers())
                        .pizzaIndexes(getPizzaIndexes(team))
                        .build()
        ).collect(Collectors.toList());
    }

    private List<Integer> getPizzaIndexes(Team team) {
        return team.getPizzas().stream().map(Pizza::getIndex).collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return getTeams().size();
    }

    @Override
    public void swap(Gene<Pizza> geneA, Gene<Pizza> geneB) {
        geneA.swap(geneB);
        geneB.swap(geneA);
    }

    @Override
    public Gene<Pizza> getGene() {
        int teamIndex = randomGenerator.nextInt(teams.size());
        Team team = teams.get(teamIndex);
        int pizzaIndex = randomGenerator.nextInt(team.getPizzas().size());
        team.setReference(pizzaIndex);
        return team.copy();
    }
}
