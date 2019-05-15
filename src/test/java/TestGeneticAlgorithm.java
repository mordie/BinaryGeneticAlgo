import org.junit.Test;

import java.util.*;

public class TestGeneticAlgorithm {

    @Test
    public void testRun() {
        Random rnd = new Random();
        int populationSize = 20;
        int chromosomeSize = 50;
        double p_m = 0.02;
        double p_c = 0.01;

        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        Map<String, Double> fitnesses = new HashMap<>();

        List<String> list = new ArrayList<>();
        for (int i=0; i<populationSize; i++)
            list.add(algorithm.generate(chromosomeSize));

        for (String str: list) {
            String mutation = algorithm.mutate(str, p_m);
            double fittness = rnd.nextDouble();
            fitnesses.put(mutation, fittness);
            System.out.printf("%s -> %s :: %f (%s)\r\n", str, mutation, fittness, str.equalsIgnoreCase(mutation));
        }

//        String result = algorithm.run(a -> 0.0, 14, p_c, p_m, 100);
    }
}
