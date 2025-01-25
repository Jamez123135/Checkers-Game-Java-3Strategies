package upei.project;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
public class Main {
    public static void main(String[] args) {
        // Number of games per pairing
        int numberOfGames = 20;

        // List of strategies
        List<Strategy> strategies = List.of(
                new AggressiveStrategy(),
                new DefensiveStrategy(),
                new RandomStrategy()
        );

        // Collect all results
        List<String> allResults = new ArrayList<>();

        // Run simulations for all pairs of strategies
        for (int i = 0; i < strategies.size(); i++) {
            for (int j = i + 1; j < strategies.size(); j++) {
                Strategy strategy1 = strategies.get(i);
                Strategy strategy2 = strategies.get(j);

                System.out.println("Comparing " + strategy1.getClass().getSimpleName() + " vs " +
                        strategy2.getClass().getSimpleName());
                SimulationExperiment experiment = new SimulationExperiment(numberOfGames);
                Map<String, Integer> results = experiment.run(strategy1, strategy2);
                experiment.printResults(results);

                // Collect results
                String resultSummary = strategy1.getClass().getSimpleName() + " vs " +
                        strategy2.getClass().getSimpleName() + ": " + results;
                allResults.add(resultSummary);
            }
        }

        // Print all results at the bottom
        System.out.println("\n--- Overall Results Summary ---");
        allResults.forEach(System.out::println);
    }
}
*/


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private int exampleVar = 0;

    public void setExampleVar(int inVal)
    {
        exampleVar = 0;
    }

    public int getExampleVar()
    {
        return exampleVar;
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}
