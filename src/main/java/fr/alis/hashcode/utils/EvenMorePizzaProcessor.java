package fr.alis.hashcode.utils;

import fr.alis.hashcode.model.*;

import java.util.Random;

public class EvenMorePizzaProcessor {
    private Random randomGenerator = new Random();
    public EvenMorePizzaOutput process(EvenMorePizzaInput input, GAParams params) {

        Population population = new Population(params.getPopulationSize(), input.copy());

        // do the fucking job
        int generation = 0;
        double delta = 1;
        while (!terminationCondition(generation, params.getMaxGeneration(), delta)) {
            double oldAverageScore = population.getAverageScore();
            population = evolvePopulation(population, input.copy(), params);
            double newAverageScore = population.getAverageScore();
            //delta = Math.abs(newAverageScore - oldAverageScore);
            ++generation;
            System.out.println(String.format("g: %d, fittest score: %d",
                    generation, population.getFittestSolution().getScore()));
        }

        // find out the best distribution
        return population.getFittestSolution().asOutput();
    }

    private boolean terminationCondition(int generation, int maxGeneration, double delta) {
        return generation >= maxGeneration || delta < 0.001;
    }

    private Population evolvePopulation(Population population, EvenMorePizzaInput input, GAParams params) {
        Population newPopulation = new Population(population.size(), input.copy());

        /*for (int i = 0; i < population.size(); i++) {
            PossibleSolution firstIndividual = randomSelection(population);
            PossibleSolution secondIndividual = randomSelection(population);

            PossibleSolution newIndividual = crossover(firstIndividual, secondIndividual);
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

    private PossibleSolution crossover(PossibleSolution firstIndividual, PossibleSolution secondIndividual) {
        return null;
    }

    private PossibleSolution randomSelection(Population population, EvenMorePizzaInput input, int tournamentSize) {
        Population newPopulation = new Population(tournamentSize, input.copy());

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = (int) (Math.random() * population.size());
            newPopulation.saveSolution(i, population.getSolution(randomIndex));
        }
        return newPopulation.getFittestSolution();
    }
}
