import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
    private static final Random rnd = new Random();
    Map<String, Double> collection;
    double delta = 0.1;

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
                .map(e -> {e.setValue(e.getValue() * e.getValue() * rnd.nextDouble()); return e;})
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        return new String[]{result.get(0), result.get(1)};
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
        int population_size = 10 * length;
        delta = 1 / length;
        collection = IntStream.range(0, population_size).boxed()
                .map(i -> generate(length))
                .distinct()
                .collect(Collectors.toMap(Function.identity(), s-> fitness.applyAsDouble(s)));

        String bestFit;
        Double best;
        do {
            String[] selected = select(collection);
            String[] newborns = crossover(selected[0], selected[1]);
            collection.put(newborns[0], fitness.applyAsDouble(newborns[0]));
            collection.put(newborns[1], fitness.applyAsDouble(newborns[1]));
            for (int i = 0; i < 3; i++) {
                Map.Entry<String, Double> leastFit =
                    collection.entrySet().stream()
                        .sorted(Comparator.comparing(Map.Entry::getValue))
                        .findFirst()
                        .get();
                collection.remove(leastFit);
            }

            bestFit = collection.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(e -> e.getKey())
                    .findFirst()
                    .orElse(selected[0]);
            best = fitness.applyAsDouble(bestFit);

            System.out.printf("size: %d\tbestFit: %s (%s == %s)\r\n", collection.size(), bestFit, best, 1 - delta);
        } while (best < 1 - delta );

        return bestFit;
    }

    public String run(ToDoubleFunction<String> fitness, int length, double p_c, double p_m, int iterations) {
        int population_size = 10 * length;
        delta = 1 / length;
        collection = IntStream.range(0, population_size).boxed()
                .map(i -> generate(length))
                .distinct()
                .collect(Collectors.toMap(Function.identity(), s-> fitness.applyAsDouble(s)));

        String bestFit;
        Double best;
        do {
            String[] selected = select(collection);
            String[] newborns = crossover(selected[0], selected[1]);
            collection.put(newborns[0], fitness.applyAsDouble(newborns[0]));
            collection.put(newborns[1], fitness.applyAsDouble(newborns[1]));
            Map.Entry<String, Double> leastFit = collection.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .findFirst()
                    .get();
            collection.remove(leastFit);

            bestFit = collection.entrySet().stream()
                    .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                    .map(e -> e.getKey())
                    .findFirst()
                    .orElse(selected[0]);
            best = fitness.applyAsDouble(bestFit);

//            System.out.printf("%03d:: size: %d\tbestFit: %s (%s == %s)\r\n", iterations--, collection.size(), bestFit, best, 1 - delta);
        } while (best < 1 - delta && iterations > 0);

        return bestFit;
    }
}