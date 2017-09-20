package br.com.facamp.com738;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Tour extends Chromosome {

    private List<City> tour;
    static List<City> cities;

    public Tour() {
        this.fitness = 0;
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
        if (fitness == 0) {
            fitness = 1 / (double) getTourDistance();
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
        Tour child1 = new Tour();
        Tour child2 = new Tour();
        Tour parent = (Tour) other;
        int pos1 = (int) (tour.size() * Math.random());
        int pos2 = (int) (tour.size() * Math.random());
        while(pos1 == pos2)
            pos2 = (int) (tour.size() * Math.random());
        for (int i = 0; i < tour.size(); i++) {
            if(pos1 < pos2) {
                if(i <= pos1) {
                    child1.getTour().add(tour.get(i));
                    child2.getTour().add(parent.getTour().get(i));
                } else if (i > pos1 && i <= pos2) {
                    child2.getTour().add(tour.get(i));
                    child1.getTour().add(parent.getTour().get(i));
                } else {
                    child1.getTour().add(tour.get(i));
                    child2.getTour().add(parent.getTour().get(i));
                }
            } else {
                if(i <= pos2) {
                    child1.getTour().add(tour.get(i));
                    child2.getTour().add(parent.getTour().get(i));
                } else if (i > pos2 && i <= pos1) {
                    child2.getTour().add(tour.get(i));
                    child1.getTour().add(parent.getTour().get(i));
                } else {
                    child1.getTour().add(tour.get(i));
                    child2.getTour().add(parent.getTour().get(i));
                }
            }         
        }
        List<Tour> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
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
        }
        return geneString;
    }
}
