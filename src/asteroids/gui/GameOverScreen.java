package asteroids.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import asteroids.utils.FileManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The component that holds all the components showing
 * up when the game is over.
 */
public class GameOverScreen extends JComponent {

    public boolean isOpen = false;
    public boolean isEnterNameOpen = false;

    JLabel textLabel = new JLabel() {

        private static final String text = "GAME OVER";

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Configure text style
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 40));

            // Get font metrics
            FontMetrics metrics = g.getFontMetrics();

            // Calculate text width and height
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            // Calculate text position
            int x = (this.getWidth() - textWidth) / 2;
            int y = (this.getHeight() - textHeight) / 2;

            // Draw text
            g.drawString(text, x, y);
        }
    };

    JLabel enterNameLabel = new JLabel() {

        private static final String text = "ENTER YOUR NAME";

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Configure text style
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 40));

            // Get font metrics
            FontMetrics metrics = g.getFontMetrics();

            // Calculate text width and height
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            // Calculate text position
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - textHeight) / 2;

            // Draw text
            g.drawString(text, x, y);
        }
    };

    // Text field for entering name.
    JTextField nameInput = new JTextField();

    // Button to confirm the entered name.
    JButton confirmEnterName = new JButton();

    // Reference to the scoreboard component.
    private ScoreBoard scoreBoardRef;

    public GameOverScreen(Dimension size, ScoreBoard scoreBoardRef) {
        this.scoreBoardRef = scoreBoardRef;

        this.setSize(size);

        textLabel.setSize(size);
        textLabel.setVisible(false);
        textLabel.setOpaque(false);
        this.add(textLabel);

        enterNameLabel.setSize(size);
        enterNameLabel.setVisible(false);
        enterNameLabel.setOpaque(false);
        this.add(enterNameLabel);

        nameInput.setSize(new Dimension(this.getWidth() / 2, 80));
        nameInput.setVisible(true);
        nameInput.setOpaque(false);
        nameInput.setBorder(BorderFactory.createEmptyBorder());
        nameInput.setForeground(Color.WHITE);
        nameInput.setBackground(this.getBackground());
        // ? Start a timer to simulate blinking cursor
        nameInput.setLocation(this.getWidth() / 2 - 180, 270);
        nameInput.setFont(new Font("Monospaced", Font.PLAIN, 26));

        confirmEnterName.setSize(80, 60);
        confirmEnterName.setLocation(this.getWidth() / 2 - 180, 340);
        confirmEnterName.setContentAreaFilled(false);
        confirmEnterName.setBorderPainted(false);
        confirmEnterName.setOpaque(false);
        confirmEnterName.setForeground(Color.WHITE);
        confirmEnterName.setFont(new Font("Monospaced", Font.PLAIN, 24));
        confirmEnterName.setVisible(true);
        confirmEnterName.setText("OK");
        confirmEnterName.setEnabled(true);
        confirmEnterName.addActionListener(e -> {
            String content = nameInput.getText() + " " + scoreBoardRef.getScore();
            System.out.println("[confirmEnterName] submit content " + content);
            FileManager.writeOrCreate(FileManager.SCORES_FILE, content);
            System.out.println("[confirmEnterName] write to txt.");

            this.setVisible(false);
        });
    }

    public void open() {
        System.out.println("[GameOverScreen::open]");

        this.isOpen = true;
        textLabel.setVisible(isOpen);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                openEnterName();
            }
        }, 2000);
    }

    /**
     * When 2 secs elapsed, the "GAME OVER" caption will be gone.
     * And the "Enter your name" (a textfield input, button) 
     * will appear.
     */
    public void openEnterName() {
        isEnterNameOpen = true;
        textLabel.setVisible(false);
        enterNameLabel.setVisible(true);
        this.add(nameInput);
        nameInput.requestFocusInWindow();

        this.add(confirmEnterName);     

        // TODO: click OK to start a new game
    }

    public void update() {
        if (isEnterNameOpen) {
            this.remove(textLabel);
            this.add(enterNameLabel);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.textLabel.paintComponents(g);
    }

}
