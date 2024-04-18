package functions;

public class Point {
    public int gen;
    public double fitness;

    public Point(){
        gen = 0;
        fitness = 1000;
    }

    public Point(int gen, double fitness) {
        this.gen = gen;
        this.fitness = fitness;
    }

    @Override
    public String toString(){
        return ""+gen;
    }
}
