package functions;

public class Schaffer extends GenAlgorithm{
    public Schaffer(){
        this.Xlower = -100;
        this.Xupper = 100;
        this.Ylower = -100;
        this.Yupper = 100;
        this.minimum = 1000;
    }
    @Override
    public double function(int gen){
        double[] parameters = decoding(gen);
        double number1 = parameters[0] * parameters[0] - parameters[1] * parameters[1];
        number1 = Math.pow(Math.sin(number1), 2);
        double number2 = parameters[0] * parameters[0] + parameters[1] * parameters[1];
        number2 = Math.pow(1 + 0.001 * number2, 2);
        return 0.5 + (number1 - 0.5)/number2;
    }
}
