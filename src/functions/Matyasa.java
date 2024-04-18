package functions;

public class Matyasa extends GenAlgorithm{
    public Matyasa(){
        this.Xlower = -10;
        this.Xupper = 10;
        this.Ylower = -10;
        this.Yupper = 10;
        this.minimum = 1000;
    }

    @Override
    public double function(int gen) {
        double[] parameters = decoding(gen);
        double x = parameters[0];
        double y = parameters[1];
        return 0.26 * (x * x + y * y) - 0.48 * x * y;
    }
}
