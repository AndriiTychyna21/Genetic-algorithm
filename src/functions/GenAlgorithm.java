package functions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public abstract class GenAlgorithm {
    private final int genSize = 20;
    protected double Xupper;
    protected double Xlower;
    protected double Yupper;
    protected double Ylower;

    private final double crossoverChance = 1;
    private final double mutationChance = 0.48;
    private final double inversionChance = 0.285;

    public double minimum;
    private ArrayList<Point> generation = new ArrayList<Point>();
    private ArrayList<Point> ngeneration = new ArrayList<Point>();    //next generation + their fitness

    abstract public double function(int gen);

    private int crossover(int parent1, int parent2){
        int child1 = 0, child2 = 0;
        int genReader = 0b1;
        for (int i = 0; i < 16; i++){
            child1 = (parent1 & genReader) | child1;
            child2 = (parent2 & genReader) | child2;
            genReader = genReader << 1;
        }
        for (int i = 16; i < 32; i++){
            child1 = (parent2 & genReader) | child1;
            child2 = (parent1 & genReader) | child2;
            genReader = genReader << 1;
        }
        Random random = new Random();
        return random.nextInt() % 2 == 0 ? child1 : child2;
    }

    private int mutation(int gen){
        Random random = new Random();
        int mutationBit = 0b1 << (random.nextInt() % 16);
        gen = gen ^ mutationBit;
        return gen;
    }

    private int inversion(int gen){
        int inversion = 0b1111_1111_1111_1111_1111_1111_1111_1111;
        gen = gen ^ inversion;
        return gen;
    }

    private int getChild(int parent1, int parent2){
        int child = crossover(parent1, parent2);
        Random random = new Random();
        if (random.nextDouble() < mutationChance) child = mutation(child);
        if (random.nextDouble() < inversionChance) child = inversion(child);
        return child;
    }

    protected double[] decoding(int gen){
        double[] parameters = new double[2];
        double stepX = (Xupper - Xlower) / 65535;
        double stepY = (Yupper - Ylower) / 65535;
        int decodeGen = 0b1111_1111_1111_1111;
        int XstepsNumber = gen & decodeGen;
        gen = gen >> 16;
        int YstepsNumber = gen & decodeGen;
        parameters[0] = Xlower + stepX * XstepsNumber;
        parameters[1] = Ylower + stepY * YstepsNumber;
        return parameters;
    }
    public double[] search(){
        int bestgen = 0;
        int gen;
        double fitness;
        Random random = new Random();
        for (int i = 0; i < genSize; i++){
            generation.add(new Point(random.nextInt(), 0));
            fitness = function(generation.get(i).gen);
            if (fitness < minimum){
                minimum = fitness;
                bestgen = generation.get(i).gen;
            }
        }
        Point nextGenerationChild;
        for (int i = 0; i < 1000; i++){
            for (int child = 0; child < 2 * genSize || ngeneration.size() < genSize; child++) {
                int parentNumber = random.nextInt() % genSize;
                /*parentNumber = (parentNumber < 0) ? -parentNumber : parentNumber;
                Point parent1 = generation.get(parentNumber);
                parentNumber = random.nextInt() % genSize;
                parentNumber = (parentNumber < 0) ? -parentNumber : parentNumber;
                Point parent2 = generation.get(parentNumber);*/
                Point parent1 = generation.get(probability(generation));
                Point parent2 = generation.get(probability(generation));
                if (parent1 == parent2) {
                    child--;
                    continue;
                }
                if (random.nextDouble() < crossoverChance) gen = getChild(parent1.gen, parent2.gen);
                else continue;
                fitness = function(gen);
                nextGenerationChild = new Point(gen, fitness);
                if (fitness < minimum){
                    minimum = fitness;
                    bestgen = gen;
                }

                ngeneration.add(nextGenerationChild);
            }
            ngeneration.sort(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.fitness == o2.fitness) return 0;
                    return o1.fitness < o2.fitness ? -1 : 1;
                }
            });
            generation.clear();
            //int rand_elem;
              for (int element = 0; element < genSize; element++){
                  generation.add(ngeneration.get(element));
                  /*rand_elem = random.nextInt(ngeneration.size()) ;
                  generation.add(ngeneration.get(rand_elem));
                  ngeneration.remove(rand_elem);
                  */
            }

            ngeneration.clear();
        }
        return decoding(bestgen);
    }

    public int probability(ArrayList<Point> array){
        ArrayList<Double> newarray = new ArrayList<>();
        double s = 0;
        for (int i = 0; i < array.size(); i++){
            s += array.get(i).fitness;
        }
        for (int i = 0; i < array.size(); i++){
            newarray.add(1 - (array.get(i).fitness/s));
        }
        s = 0;
        for (int i = 0; i < array.size(); i++){
            s += newarray.get(i);
        }
        newarray.set(0, newarray.get(0)/s);
        for (int i = 1; i < newarray.size(); i++){
            newarray.set(i, newarray.get(i-1) + (newarray.get(i)/s));
        }
        Random rand = new Random();
        double r = rand.nextDouble();
        for (int chance = 0; chance < array.size() - 1; chance++){
            if (r < newarray.get(chance)) return chance;
        }
        return newarray.size() - 1;
    }
}
