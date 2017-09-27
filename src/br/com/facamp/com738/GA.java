package br.com.facamp.com738;

import java.util.ArrayList;
import java.util.List;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class GA {

    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static int elitismOffset = 1;
    
    private static XYSeries graphDataset = new XYSeries("Tour distance", false);

    public static double getMutationRate() {
        return mutationRate;
    }

    public static int getTournamentSize() {
        return tournamentSize;
    }

    public static int getElitismOffset() {
        return elitismOffset;
    }

    public static void main(String[] args) {
        City city = new City(60, 200);
        Tour.addCity(city);
        City city2 = new City(180, 200);
        Tour.addCity(city2);
        City city3 = new City(80, 180);
        Tour.addCity(city3);
        City city4 = new City(140, 180);
        Tour.addCity(city4);
        City city5 = new City(20, 160);
        Tour.addCity(city5);
        City city6 = new City(100, 160);
        Tour.addCity(city6);
        City city7 = new City(200, 160);
        Tour.addCity(city7);
        City city8 = new City(140, 140);
        Tour.addCity(city8);
        City city9 = new City(40, 120);
        Tour.addCity(city9);
        City city10 = new City(100, 120);
        Tour.addCity(city10);
        City city11 = new City(180, 100);
        Tour.addCity(city11);
        City city12 = new City(60, 80);
        Tour.addCity(city12);
        City city13 = new City(120, 80);
        Tour.addCity(city13);
        City city14 = new City(180, 60);
        Tour.addCity(city14);
        City city15 = new City(20, 40);
        Tour.addCity(city15);
        City city16 = new City(100, 40);
        Tour.addCity(city16);
        City city17 = new City(200, 40);
        Tour.addCity(city17);
        City city18 = new City(20, 20);
        Tour.addCity(city18);
        City city19 = new City(60, 20);
        Tour.addCity(city19);
        City city20 = new City(160, 20);
        Tour.addCity(city20);
        Population pop = new Population(100, true);
        Tour fittest = (Tour) pop.getFittest();
        System.out.println("Initial distance: " + fittest.getTourDistance());
        // Evolve population for n generations
        pop.evolve();
        for (int i = 0; i < 200; i++) {
            pop.evolve();
            Tour t = (Tour) pop.getFittest();
            graphDataset.add(i, t.getTourDistance());
        }

        // Print final results
        System.out.println("Finished");
        fittest = (Tour) pop.getFittest();
        System.out.println("Final distance: " + fittest.getTourDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
        
        displayGraph();

    }
    
    public static void displayGraph() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(graphDataset);
        LineChart chart = new LineChart(
                "Fitness evolution",
                "Fitness evolution", dataset);

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

}
