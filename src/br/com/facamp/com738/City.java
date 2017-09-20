package br.com.facamp.com738;

public class City {
     private final int x;
     private final int y;
     
     public City() {
         this.x = (int) (Math.random() * 200);
         this.y = (int) (Math.random() * 200);
     }
     
     public City(int x, int y) {
         this.x = x;
         this.y = y;
     }
     
     public double distanceTo(City city) {
         int xDistance = Math.abs(getX() - city.getX());
         int yDistance = Math.abs(getY() - city.getY());
         return Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
     }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    @Override
    public String toString(){
        return getX() + ", " + getY();
    }
}
