package fr.alis.hashcode.model;

import fr.alis.hashcode.engine.GAParams;
import fr.alis.hashcode.engine.Gene;
import fr.alis.hashcode.engine.PossibleSolution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
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
        Instant start = Instant.now();
        int members = 0;
        do {
            members = getTeamMembers();
            Team team = new Team();
            team.setMembers(members);
            if (members <= availablePizza.size() && teamAvailable(members)) {
                team.setPizzas(getBestPizza(availablePizza, members));
                team.getPizzas().forEach(pizza -> {
                    availablePizza.removeIf(p -> p.getIndex() == pizza.getIndex());
                });
                teams.add(team);
                decreaseTeam(members);
            }
        } while (members <= availablePizza.size() && deliverable(availablePizza.size()));
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("initializing took %d ms, score: %d%n", timeElapsed, getScore());
    }

    private int getTeamMembers() {
        if (input.getTeamsOfFour() > 0 && availablePizza.size() >= 4) {
            return 4;
        } else if (input.getTeamsOfThree() > 0 && availablePizza.size() >= 3) {
            return 3;
        }
        return 2;
    }

    private List<Pizza> getBestPizza(List<Pizza> availablePizza, int members) {
        return stochastic(availablePizza, members);
    }

    private List<Pizza> stochastic(List<Pizza> availablePizza, int members) {
        Pizza bestPizza = null;
        LinkedList<Pizza> bestPizzas = new LinkedList<>();
        List<String> teamIngredients = bestPizzas.parallelStream().flatMap(p -> p.getIngredients().stream()).collect(Collectors.toList());
        for (int i = 0; i < 1000; i++) {
            int randomIndex = randomGenerator.nextInt(availablePizza.size());
            Pizza pizza = availablePizza.get(randomIndex);
            if (bestPizza == null) {
                bestPizza = pizza;
                bestPizzas.add(pizza);
            }
            else if (getScore(bestPizza, teamIngredients) < getScore(pizza, teamIngredients)) {
                bestPizza = pizza;
                bestPizzas.add(pizza);
            }
        }

        List<Pizza> bestOfTheBest = new LinkedList<>();
        for (int j = 0; j < members; j++) {
            bestOfTheBest.add(simulatedAnnealing(bestPizzas, bestOfTheBest));
        }
        bestPizzas = new LinkedList<>(bestOfTheBest);

        return bestPizzas;
    }


    private Pizza simulatedAnnealing(List<Pizza> availablePizza, List<Pizza> pizzas) {
        Pizza bestPizza = null;
        Pizza currentPizza = null;
        List<String> teamIngredients = pizzas.parallelStream().flatMap(p -> p.getIngredients().stream()).collect(Collectors.toList());

        int temp = 100;
        int startIndex = 0;
        double coolingRate = 0.4;
        while (temp > 1) {
            int index = randomGenerator.nextInt(availablePizza.size() - startIndex) + startIndex;
            Pizza pizza = availablePizza.get(index);
            if (currentPizza == null) {
                currentPizza = pizza;
                bestPizza = pizza;
            } else {
                int currentE = getScore(currentPizza, teamIngredients);
                int nextE = getScore(pizza, teamIngredients);

                if (acceptanceProbability(currentE, nextE, temp) > Math.random()) {
                    currentE = nextE;
                    currentPizza = pizza;
                    startIndex = index;
                }
                if (getScore(bestPizza, teamIngredients) < currentE) {
                    bestPizza = currentPizza;
                }
                temp *= 1 - coolingRate;
            }

        }
        return bestPizza;
    }

    private double acceptanceProbability(int currentE, int nextE, int temp) {
        if (nextE > currentE) {
            return 1;
        }
        return Math.exp((currentE-nextE)/(double)temp);
    }

    private int getScore(Pizza pizza, List<String> teamIngredients) {
        Set<String> ingredients = new HashSet<>(pizza.getIngredients());
        ingredients.addAll(teamIngredients);
        return ingredients.size();
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
        } else if (members == 3) {
            input.setTeamsOfThree(input.getTeamsOfThree() - 1);
        } else {
            input.setTeamsOfFour(input.getTeamsOfFour() - 1);
        }
    }

    private boolean teamAvailable(int members) {
        if (members == 2) {
            return input.getTeamsOfTwo() > 0;
        } else if (members == 3) {
            return input.getTeamsOfThree() > 0;
        } else {
            return input.getTeamsOfFour() > 0;
        }
    }

    public long getScore() {
        return teams.stream().mapToLong(Team::getScore).sum();
    }

    @Override
    public EvenMorePizzaOutput asOutput(GAParams params, int generation) {
        return EvenMorePizzaOutput.builder()
                .totalTeams(teams.size())
                .orders(getOrders())
                .score(getScore())
                .generation(generation)
                .params(params)
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

    @Override
    public void crossover(int reference, Gene<Pizza> gene) {
        getTeams().remove(reference);
        getTeams().add(reference, (Team) gene);
    }

    @Override
    public Gene<Pizza> getValidGene(int reference, PossibleSolution<Pizza, EvenMorePizzaOutput> solution, Gene<Pizza> gene) {
        Team team = (Team) gene;
        return Team.builder()
                .members(team.getMembers())
                .pizzas(getValidPizzas(reference, (Distribution) solution))
                .build();
    }

    private List<Pizza> getValidPizzas(int reference, Distribution solution) {
        return getTeams().stream().flatMap(t -> t.getPizzas().stream())
                .filter(pizza -> !deliveredPizza(reference, solution))
                .collect(Collectors.toList());
    }

    private boolean deliveredPizza(int reference, Distribution solution) {
        return solution.getTeams().stream().limit(reference)
                .flatMap(team -> team.getPizzas().stream()).mapToInt(Pizza::getIndex).anyMatch(i -> i == reference);
    }

    @Override
    public void saveGene(int i, Gene<Pizza> gene) {
        getTeams().remove(i);
        getTeams().add(i, (Team) gene);
    }
}
