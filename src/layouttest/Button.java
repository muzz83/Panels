/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layouttest;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author muss0013
 */

// This is the class that is used as buttons on the screen.
// This class also contains variables that are used to capture data
// and various counters required to keep track of where the program is
// in its sequence.

public class Button extends JButton {
    
        public int count = 0;
        public static int permutationsCount = 0;
        public Color buttonColourOn = null;
        private Color buttonColourOff = null;
        public StopWatch clock = new StopWatch();        
        public static double[][] captureData = new double[12][2];
        public static int[][] permutations = {{24,96},{24,192},{24,384},
            {24,768},{48,96},{48,192},{48,384},{48,768},{96,96},{96,192},
            {96,384},{96,768}};
        
        public Button() {
            buttonColourOn = Color.green;
            buttonColourOff = Color.white;
            setBackground(buttonColourOn);
        }
        
        // Swaps the colour of the button.
        
        public void swapColour() {
            Color temp = buttonColourOn;
            buttonColourOn = buttonColourOff;
            buttonColourOff = temp;
            setBackground(buttonColourOn);
        }
}
