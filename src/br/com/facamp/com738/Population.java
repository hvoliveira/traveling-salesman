package br.com.facamp.com738;

import java.util.ArrayList;
import java.util.Collections;
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
                pop.individuals.add(this.getFittest());
            }
        }
        for (int i = GA.getElitismOffset(); i < pop.populationSize; i++) {
            // Select parents
            Tour parent1 = rouletteSelection(Tour.class);
            Tour parent2 = rouletteSelection(Tour.class);
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

    public <T extends Chromosome> T rouletteSelection(Class<T> type) {
        // normalize fitness
        double totalFitness = 0.0;
        for (Chromosome individual : individuals) {
            totalFitness += individual.getFitness();
        }
        for (Chromosome individual : individuals) {
            individual.normFitness = individual.getFitness() / totalFitness;
        }
        // sort individuals
        Collections.sort(individuals);
        // select individual
        double seed = Math.random();
        double cumFitness = 0.0;
        cumFitness = individuals.get(0).normFitness;
        for (int i = 1; i <= populationSize; i++) {
            if(cumFitness > seed)
                return type.cast(individuals.get(i-1));
            cumFitness += individuals.get(i).normFitness;             
        }
        return null;
    }
}
