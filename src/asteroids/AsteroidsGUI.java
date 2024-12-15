package asteroids;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import asteroids.gui.GameOverScreen;
import asteroids.gui.ScoreBoard;

public class AsteroidsGUI {

    // The width and height of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 600;

    // The dimension of the window.
    private static final Dimension windowSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

    // The dimension of the scoreboard.
    private static final Dimension scoreBoardSize = new Dimension(WINDOW_WIDTH, 60);

    // The game and renderer objects.
    private static AsteroidsGame game;
    private static AsteroidsRenderer renderer;

    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * 
     * @param args none
     */
    public static void main(String[] args) {

        // Initialize GUI components
        JFrame window = new JFrame();
        JLayeredPane layeredPane = new JLayeredPane();
        ScoreBoard scoreBoard = new ScoreBoard();
        GameOverScreen gameOverScreen = new GameOverScreen(windowSize, scoreBoard);

        // Initialize the game and renderer
        game = new AsteroidsGame(windowSize);
        renderer = new AsteroidsRenderer(game);

        // Configure the window
        window.setTitle("Asteroids");
        window.setSize(windowSize);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO: add confirmation dialog before closing the game

        // Configure the layered pane
        layeredPane.setLocation(0, 0);
        layeredPane.setSize(windowSize);
        layeredPane.setBackground(Color.BLACK);
        layeredPane.setLayout(null);
        layeredPane.setOpaque(true);

        // Configure the scoreboard
        scoreBoard.setLocation(0, 0);
        scoreBoard.setSize(scoreBoardSize);
        scoreBoard.setOpaque(false);
        // TODO: read high score from file

        // Update the scoreboard or show game over screen when the lives change
        game.addLivesUpdateHandler((lives) -> {
            scoreBoard.setLives(lives);
            if (lives == 0)
                gameOverScreen.open();
        });

        // Update the scoreboard when the score changes
        game.addScoreUpdateHandler((score) -> {
            scoreBoard.setScore(score);
        });

        // Configure the game over screen
        gameOverScreen.setLocation(0, 0);
        gameOverScreen.setOpaque(false);

        // Configure the renderer
        renderer.setSize(windowSize);

        // Add the components to the layered pane and the window
        layeredPane.add(renderer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(scoreBoard, JLayeredPane.MODAL_LAYER);
        layeredPane.add(gameOverScreen, JLayeredPane.POPUP_LAYER);
        window.add(layeredPane);
        window.setVisible(true);

        // Start the game
        renderer.start();
    }
}
