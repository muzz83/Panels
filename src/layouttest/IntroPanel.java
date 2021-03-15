/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layouttest;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Michael
 */

// The IntroPanel for the program.

public class IntroPanel extends JPanel {
    
    private JLabel title = new JLabel("Fitts's Law Experiment");
    private String text = "This is an interactive GUI based on "
            + "Fitts's Law and the original experiment performed by Fitts. "
            + "A random sequence of panels will appear on screen and you will be "
            + "timed while you click between the altermating coloured panels."
            + " Begin by clicking the green panel and then select the next "
            + "green panel. The timer will begin once the first green panel "
            + "has been clicked. Continue to alternate between panels, "
            + "always selecting the green panel until the sequence has "
            + "ended. Focus on speed and accuracy. Your times will be "
            + "displayed at the end of the sequence. Click to begin.";
    private JLabel intro = new JLabel("<html><div style=\"text-align: center;\">" + text + "</html>");
    
    public IntroPanel() {
        
        // Set up the panel, components, and add them to the panel.
        
        setSize(980,650);
        setLayout(null);
        setBackground(Color.lightGray);
        
        title.setFont(title.getFont().deriveFont(35.0f));
        title.setForeground(Color.YELLOW);
        title.setSize(400, 100);
        title.setLocation(300,0);
        intro.setFont(intro.getFont().deriveFont(15.0f));
        intro.setForeground(Color.BLACK);
        intro.setSize(350, 300);
        intro.setLocation(325,150);
        
        add(title);
        add(intro);
    }
}
