package br.com.facamp.com738;

import java.util.List;

public abstract class Chromosome implements Comparable {
    
    double fitness;
    double normFitness;
    
    public abstract void generateIndividual();
    
    public abstract void mutate();
    
    public abstract List<? extends Chromosome> crossover(Chromosome other);

    public abstract double getFitness();

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
        @Override
    public int compareTo(Object o) {
        Chromosome c = (Chromosome) o;
        if(this.normFitness > c.normFitness)
            return 1;
        else if(this.normFitness == c.normFitness)
            return 0;
        else
            return -1;
    }   
}
