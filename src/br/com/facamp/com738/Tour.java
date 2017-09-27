package br.com.facamp.com738;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Tour extends Chromosome {

    private List<City> tour;
    static List<City> cities;

    public Tour() {
        this.fitness = -1;
        this.tour = new LinkedList<>();
    }

    public Tour(List tour) {
        this.tour = tour;
    }

    @Override
    public void generateIndividual() {
        for (City city : cities)
            tour.add(city);
        Collections.shuffle(tour);
    }

    @Override
    public double getFitness() {
        if (fitness == -1) {
            fitness = 1000000/getTourDistance();
        }
        return fitness;
    }

    public double getTourDistance() {
        double tourDistance = 0;
        for (int i = 0; i < tour.size(); i++) {
            City fromCity = tour.get(i);
            City destinationCity;
            if (i + 1 < tour.size())
                destinationCity = tour.get(i + 1);
            else
                destinationCity = tour.get(0);
            tourDistance += fromCity.distanceTo(destinationCity);
        }
        return tourDistance;
    }
    
    @Override
    public void mutate() {
        if(Math.random() < GA.getMutationRate()) {
            int i = (int) (tour.size() * Math.random());
            int j = (int) (tour.size() * Math.random());
            while(i == j)
                j = (int) (tour.size() * Math.random());
            City aux = tour.get(i);
            tour.set(i, tour.get(j));
            tour.set(j, aux);
        }
    }

    @Override
    public List<Tour> crossover(Chromosome other) {
        Tour child = new Tour();
        for (int i = 0; i < tour.size(); i++) {
            child.tour.add(null);
        }
        Tour parent = (Tour) other;

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent.tour.size());
        int endPos = (int) (Math.random() * parent.tour.size());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < tour.size(); i++) {
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.tour.set(i, parent.tour.get(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.tour.set(i, parent.tour.get(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < tour.size(); i++) {
            // If child doesn't have the city add it
            if (!child.tour.contains(tour.get(i))) {
                // Loop to find a spare position in the child's tour
                for (int j = 0; j < child.tour.size(); j++) {
                    // Spare position found, add city
                    if (child.tour.get(j) == null) {
                        child.tour.set(j, tour.get(i));
                        break;
                    }
                }
            }
        }
        List<Tour> children = new ArrayList<>();
        children.add(child);
        return children;
    }

    public List<City> getTour() {
        if(tour == null)
            tour = new LinkedList<>();
        return tour;
    }
    
    public void setTour(List<City> tour) {
        this.tour = tour;
    }

    public static List<City> getCities() {
        if(cities == null) {
            cities = new ArrayList<>();
        }
        return cities;
    }
    
    public static void addCity(City city) {
        if(cities == null) {
            cities = new ArrayList<>();
        }
        cities.add(city);
    }
    
    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tour.size(); i++) {
            geneString += tour.get(i)+"|";
            if(i == tour.size()/2)
               geneString += "\n";
        }
        return geneString;
    }
}
