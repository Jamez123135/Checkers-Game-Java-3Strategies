package upei.project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testAllStrategyPairings() {
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

                System.out.println("Testing " + strategy1.getClass().getSimpleName() + " vs " +
                        strategy2.getClass().getSimpleName());

                // Run simulation experiment
                SimulationExperiment experiment = new SimulationExperiment(numberOfGames);
                Map<String, Integer> results = experiment.run(strategy1, strategy2);

                // Print results for this pairing
                System.out.println(results);

                // Validate results
                int totalGames = results.values().stream().mapToInt(Integer::intValue).sum();
                assertEquals(numberOfGames, totalGames, "Total games should match the specified number");
                assertTrue(results.get("Draws") >= 0, "Draws cannot be negative");
                assertTrue(results.get(strategy1.getClass().getSimpleName() + " Wins") >= 0,
                        "Wins for " + strategy1.getClass().getSimpleName() + " cannot be negative");
                assertTrue(results.get(strategy2.getClass().getSimpleName() + " Wins") >= 0,
                        "Wins for " + strategy2.getClass().getSimpleName() + " cannot be negative");

                // Save results for summary
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
