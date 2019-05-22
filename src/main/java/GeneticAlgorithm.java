import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
    private static final Random rnd = new Random();

    private char flip(char c) {
        return (c == '1') ? '0' : '1';
    }

    private String generate(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<length; i++) {
            if (rnd.nextDouble() > 0.5) {sb.append("0");} else {sb.append("1");}
        }
        return sb.toString();
    }

    private String[] select(Map<String, Double> fitnesses) {
        List<String> result = fitnesses.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        return result.toArray(new String[]{});
    }

    private String mutate(String chromosome, double p) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (char c: chromosome.toCharArray()) {
            if (random.nextDouble() > p) {sb.append(c);} else {sb.append(flip(c));}
        }
        return sb.toString();
    }

    private String[] crossover(String chromosome1, String chromosome2) {
        int length = chromosome1.length(); int cut = rnd.nextInt(length);
        String chromosome3 = chromosome1.substring(0, cut) + chromosome2.substring(cut, length);
        String chromosome4 = chromosome2.substring(0, cut) + chromosome1.substring(cut, length);
        return new String[]{chromosome3, chromosome4};
    }

    public String run(ToDoubleFunction<String> fitness, int length, double p_c, double p_m) {
        Map<String, Double> collection = IntStream.range(0, length).boxed()
                .map(i -> generate(length))
                .collect(Collectors.toMap(Function.identity(), s-> fitness.applyAsDouble(s)));
        String[] selection = select(collection);
        return selection[0];
    }

    public String run(ToDoubleFunction<String> fitness, int length, double p_c, double p_m, int iterations) {
        // TODO: Implement the run method
        return "000";
    }
}