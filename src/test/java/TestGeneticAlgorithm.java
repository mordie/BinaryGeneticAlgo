import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

@RunWith(value = Parameterized.class)
public class TestGeneticAlgorithm {

    @Parameterized.Parameter
    public String expected;

    @Parameterized.Parameters
    public static Collection<String> data() {
        return Arrays.asList(new String[]{
                "000",
                "1111",
                "11101",
                "110011",
                "1101010",
                "11010101",
                "110101011",
                "1101010110",
                "11010101101",
                "000111010100",
                "000101011011010010110011110100101001"
        });
    }

    @Test
    public void testRun() {
        double p_m = 0.002;
        double p_c = 0.2;

        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        int length = expected.length();
        ToDoubleFunction<String> fitness = s -> {
            return IntStream.range(0, length)
                    .mapToDouble(i -> expected.charAt(i) == s.charAt(i) ? 1.0 : 0.0)
                    .sum() / (double) length;
        };

        System.out.printf("%s => %s\r\n", expected, algorithm.run(fitness, length, p_c, p_m));
//        System.out.printf("%s => %s\r\n", expected, algorithm.run(fitness, length, p_c, p_m, 5));

    }
}
