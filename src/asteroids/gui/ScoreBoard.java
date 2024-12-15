package asteroids.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.util.Map;
import java.util.HashMap;

import javax.swing.JPanel;

import asteroids.utils.FileManager;

/**
 * <p>The transparent score board component of the game. </p>
 * <p>TODO: read from file and sort scores, then display
 * the highest score
 * 
 * <p>Notice that all the elements were draw in one paintComponent
 * method. Since this JComponent has the size of the whole game
 * screen and only print white text in somewhere on the screen. 
 */
public class ScoreBoard extends JPanel {

    private int score;
    private int lives;
    private int highScore;

    public Map<String, Integer> nameToScore;

    /**
     * Update high score display.
     * @param score
     */
    public void setHighScore(int score) {
        this.highScore = score;
    }

    private void readHighScoreFromFile() {
        String scores = FileManager.readFile(FileManager.SCORES_FILE);
        String[] lines = scores.split("\n");
        for (String line: lines) {
            String[] nameAndScore = line.split(" ");
            try {
                int number = Integer.parseInt(nameAndScore[1]);
                if (number > this.highScore) {
                    this.highScore = number;
                }
                nameToScore.put(nameAndScore[0], number);
            } catch (Exception e) {
                System.out.println("Error during parsing a score. Skipping." + e.getMessage());
            }
        }
    }

    public ScoreBoard() {
        super();
        this.lives = 3;
        this.score = 0;

        this.highScore = 0;
        this.nameToScore = new HashMap<>();
        readHighScoreFromFile();
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Update score display. If there is a score higher than the highest score, take it 
     * as new highest score (so to avoid reading from the score file)
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
        if (this.score > this.highScore) {
            this.highScore = score;
        }
    }

    public int getScore() {
        return this.score;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Configure text style
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 20));

        // Draw text
        g.drawString("Lives: " + lives + "  Score: " + score + "  High Score:" + highScore, 80, 40);
    }
}
