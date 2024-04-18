package functions;

public class Bukin extends GenAlgorithm{

    public Bukin(){
        this.Xlower = -15;
        this.Xupper = -5;
        this.Ylower = -3;
        this.Yupper = 3;
        this.minimum = 1000;
    }
    @Override
    public double function(int gen){
        double[] parameters = decoding(gen);
        double number1 = parameters[1] - 0.01 * Math.pow(parameters[0], 2);
        double number2 = parameters[0] + 10;
        number1 = (number1 < 0) ? -number1 : number1;
        number2 = (number2 < 0) ? -number2 : number2;
        return 100 * Math.sqrt(number1) + 0.01 * number2;
    }
}
