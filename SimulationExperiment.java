package upei.project;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

/**
 * The SimulationExperiment class runs a series of games between players with different strategies
 * and collects data to analyze strategy performance.
 */
public class SimulationExperiment {
    private final int numberOfGames;

    /**
     * Constructor for the SimulationExperiment class.
     *
     * @param numberOfGames the number of games to simulate.
     */
    public SimulationExperiment(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    /**
     * Runs the simulation experiment between two strategies.
     *
     * @param strategy1 the first player's strategy.
     * @param strategy2 the second player's strategy.
     * @return a map containing the results of the simulation for each strategy.
     */
    public Map<String, Integer> run(Strategy strategy1, Strategy strategy2) {
        // Initialize results for each strategy
        Map<String, Integer> strategyResults = new HashMap<>();
        strategyResults.put(strategy1.getClass().getSimpleName() + " Wins", 0);
        strategyResults.put(strategy2.getClass().getSimpleName() + " Wins", 0);
        strategyResults.put("Draws", 0);

        // Simulate games
        for (int i = 0; i < numberOfGames; i++) {
            System.out.println("Game " + (i + 1) + " of " + numberOfGames);

            // Create players with the specified strategies
            Player player1 = new Player("Black", strategy1);
            Player player2 = new Player("White", strategy2);

            // Initialize a new game
            Game game = new Game(player1, player2);
            game.startGame();

            // Determine the winner
            String winner = game.determineWinner(); // Returns "Player 1", "Player 2", or "Draw"
            if (winner.equals("Player 1")) {
                strategyResults.put(strategy1.getClass().getSimpleName() + " Wins",
                        strategyResults.get(strategy1.getClass().getSimpleName() + " Wins") + 1);
            } else if (winner.equals("Player 2")) {
                strategyResults.put(strategy2.getClass().getSimpleName() + " Wins",
                        strategyResults.get(strategy2.getClass().getSimpleName() + " Wins") + 1);
            } else {
                strategyResults.put("Draws", strategyResults.get("Draws") + 1);
            }
        }

        return strategyResults;
    }

    /**
     * Prints the results of the simulation experiment.
     *
     * @param results a map containing the results of the simulation.
     */
    public void printResults(Map<String, Integer> results) {
        System.out.println("Simulation Results:");
        results.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
