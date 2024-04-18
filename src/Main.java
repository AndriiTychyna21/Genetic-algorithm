import functions.*;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args){
        GenAlgorithm schaffer = new Schaffer();
        GenAlgorithm bukin = new Bukin();
        GenAlgorithm matyasa = new Matyasa();
        double approximate = 0;

        double[] answer = schaffer.search();
        System.out.println("Schaffer function N. 2");
        System.out.println("x = " + answer[0]);
        System.out.println("y = " + answer[1]);
        System.out.println("f = " + schaffer.minimum);
        approximate += schaffer.minimum;
        for (int i = 0; i < 999; i++){
            schaffer = new Schaffer();
            schaffer.search();
            approximate += schaffer.minimum;
        }
        approximate /= 1000;
        System.out.println("Approximate result: " + approximate);
        approximate = 0;

        answer = bukin.search();
        System.out.println("\nBukin function N.6");
        System.out.println("x = " + answer[0]);
        System.out.println("y = " + answer[1]);
        System.out.println("f = " + bukin.minimum);
        approximate += bukin.minimum;
        for (int i = 0; i < 999; i++){
            bukin = new Bukin();
            bukin.search();
            approximate += bukin.minimum;
        }
        approximate /= 1000;
        System.out.println("Approximate result: " + approximate);
        approximate = 0;

        answer = matyasa.search();
        System.out.println("\nMatyas function");
        System.out.println("x = " + answer[0]);
        System.out.println("y = " + answer[1]);
        System.out.println("f = " + matyasa.minimum);
        approximate += matyasa.minimum;
        for (int i = 0; i < 999; i++){
            matyasa = new Matyasa();
            matyasa.search();
            approximate += matyasa.minimum;
        }
        approximate /= 1000;
        System.out.println("Approximate result: " + approximate);

    }
}
