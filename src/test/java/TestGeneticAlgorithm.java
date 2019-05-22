import org.junit.Test;

import java.util.*;
import java.util.function.DoubleFunction;
import java.util.function.ToDoubleFunction;

public class TestGeneticAlgorithm {

    @Test
    public void testRun() {
        Random rnd = new Random();
        int populationSize = 20;
        int chromosomeSize = 50;
        double p_m = 0.02;
        double p_c = 0.01;

        GeneticAlgorithm algorithm = new GeneticAlgorithm();

        algorithm.run(s -> rnd.nextDouble(), chromosomeSize, p_c, p_m);

    }
}
