import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private static final Random rnd = new Random();

    private char flip(char c) {
        return (c == 31) ? '0' : '1';
    }

    public String generate(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<length; i++) {
            if (rnd.nextDouble() > 0.5) {sb.append("0");} else {sb.append("1");}
        }
        return sb.toString();
    }

    public String[] select(Map<String, Double> fitnesses) {
        List<String> result = fitnesses.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        return result.toArray(new String[]{});
    }

    public String mutate(String chromosome, double p) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (char c: chromosome.toCharArray()) {
            if (random.nextDouble() > p) {sb.append(c);} else {sb.append(flip(c));}
        }
        return sb.toString();
    }

    private String[] crossover(String chromosome1, String chromosome2) {
        // TODO: Implement the crossover method
        return new String[]{};
    }

    public String run(ToDoubleFunction<String> fitness, int length, double p_c, double p_m) {
        // TODO: Implement the run method
        return "000";
    }

    public String run(ToDoubleFunction<String> fitness, int length, double p_c, double p_m, int iterations) {
        // TODO: Implement the run method
        return "000";
    }
}