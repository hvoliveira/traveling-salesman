package br.com.facamp.com738;

import java.util.ArrayList;
import java.util.List;

public class Population {

    List<Chromosome> individuals;
    int populationSize;
    private static Class type;

    public static void setType(Class type) {
        Population.type = type;
    }

    public Population(int populationSize, boolean init) {
        individuals = new ArrayList<>();
        this.populationSize = populationSize;
        if (init) {
            for (int i = 0; i < populationSize; i++) {
                Chromosome tour = new Tour();
                tour.generateIndividual();
                individuals.add(tour);
            }
        }
    }

    public void evolve() {
        Population pop = new Population(populationSize, false);
        if (GA.getElitismOffset() > 0) {
            for (int i = 0; i < GA.getElitismOffset(); i++) {
                pop.individuals.add(getFittest());
            }
        }
        for (int i = GA.getElitismOffset(); i < pop.populationSize; i++) {
            // Select parents
            Tour parent1 = tournamentSelection(Tour.class);
            Tour parent2 = tournamentSelection(Tour.class);
            // Crossover parents
            List<Tour> children = parent1.crossover(parent2);
            // Add child to new population
            pop.individuals.add(children.get(0));
        }

        // Mutate the new population a bit to add some new genetic material
        for (Chromosome chr : pop.individuals) {
            chr.mutate();
        }
        this.individuals = pop.individuals;
    }

    private <T extends Chromosome> T tournamentSelection(Class<T> type) {
        // Create a tournament population
        Population tournament = new Population(GA.getTournamentSize(), false);
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < GA.getTournamentSize(); i++) {
            int randomId = (int) (Math.random() * populationSize);
            tournament.individuals.add(individuals.get(randomId));
        }
        // Get the fittest tour 
        return type.cast(tournament.getFittest());
    }

    public Chromosome getFittest() {
        Chromosome fittest = individuals.get(0);
        for (Chromosome individual : individuals) {
            if (individual.getFitness() > fittest.getFitness()) {
                fittest = individual;
            }
        }
        return fittest;
    }

}
