package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EvenMorePizzaProcessor {
    private Random randomGenerator = new Random();
    public EvenMorePizzaOutput process(Population population, GAParams params) {
        PossibleSolution fittestSolution = null;
        int generation = 0;
        while (!terminationCondition(generation, params.getMaxGeneration())) {
            population = evolvePopulation(population, params);
            ++generation;

            if (fittestSolution == null || population.getFittestSolution().getScore() > fittestSolution.getScore()) {
                fittestSolution = population.getFittestSolution().copy();
                System.out.printf("g: %d, fittest score: %d%n",
                        generation, population.getFittestSolution().getScore());
            }

        }

        // find out the best distribution
        return fittestSolution.asOutput();
    }

    private boolean terminationCondition(int generation, int maxGeneration) {
        return generation >= maxGeneration;
    }

    private Population evolvePopulation(Population population, GAParams params) {
        Population newPopulation = new Population(population.size(), population.getInput().copy());
        newPopulation.initialize();
        /*for (int i = 0; i < population.size(); i++) {
            PossibleSolution firstIndividual = randomSelection(population, params.getTournamentSize());
            PossibleSolution secondIndividual = randomSelection(population, params.getTournamentSize());

            PossibleSolution newIndividual = crossover(firstIndividual, secondIndividual, params.getCrossoverRate());
            newPopulation.saveSolution(i, newIndividual);
        }*/

        for (int i = 0; i < newPopulation.size(); i++) {
            newPopulation.saveSolution(i, mutate(newPopulation.getSolution(i), params.getMutationRate()));
        }

        return newPopulation;
    }

    private PossibleSolution mutate(PossibleSolution solution, double mutationRate) {
        int size = solution.getTeams().size();
        for (int i = 0; i < size; i++) {
            if (Math.random() <= mutationRate) {
                int teamAIndex = randomGenerator.nextInt(size);
                int teamBIndex = randomGenerator.nextInt(size);
                Team teamA = solution.getTeams().get(teamAIndex);
                Team teamB = solution.getTeams().get(teamBIndex);

                int pizzaAIndex = randomGenerator.nextInt(teamA.getPizzas().size());
                int pizzaBIndex = randomGenerator.nextInt(teamB.getPizzas().size());

                Pizza pizzaA = teamA.getPizzas().get(pizzaAIndex);
                Pizza pizzaB = teamB.getPizzas().get(pizzaBIndex);

                teamA.getPizzas().remove(pizzaAIndex);
                teamA.getPizzas().add(pizzaAIndex, pizzaB);

                teamB.getPizzas().remove(pizzaBIndex);
                teamB.getPizzas().add(pizzaBIndex, pizzaA);
            }
        }
        return solution;
    }

    private PossibleSolution crossover(PossibleSolution firstIndividual,
                                       PossibleSolution secondIndividual,
                                       double crossoverRate) {

        PossibleSolution newSolution = firstIndividual.copy();
        int size = newSolution.getTeams().size();
        for (int i = 0; i < size; i++) {
            if (Math.random() < crossoverRate) {
                int randomIndex = randomGenerator.nextInt(size);
                Team team = newSolution.getTeams().get(randomIndex);
                List<Team> teamsOfB = getTeamsOf(secondIndividual, team.getMembers());
                int bound = teamsOfB.size();
                if (bound > 0) {
                    int randomBIndex = randomGenerator.nextInt(bound);
                    Team team2 = teamsOfB.get(randomBIndex);

                    newSolution.getTeams().remove(randomIndex);
                    newSolution.getTeams().add(randomIndex, team2);
                }


            }
        }
        return newSolution;
    }

    private List<Team> getTeamsOf(PossibleSolution solution, int teamSize) {
        return solution.getTeams().stream()
                .filter(team -> team.getMembers() == teamSize).collect(Collectors.toList());
    }

    private PossibleSolution randomSelection(Population population, int tournamentSize) {
        Population newPopulation = new Population(tournamentSize, population.getInput().copy());
        newPopulation.initialize();
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = (int) (Math.random() * population.size());
            newPopulation.saveSolution(i, population.getSolution(randomIndex));
        }
        return newPopulation.getFittestSolution();
    }
}
