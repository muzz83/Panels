/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package layouttest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import static layouttest.Button.captureData;
import static layouttest.Button.permutations;

/**
 *
 * @author Michael
 */
public class LayoutTest extends JFrame {

    /**
     * @param args the command line arguments
     */
    
    // Main method below, sets up the frame and adds the intro panel
    // and a mouse listener which then calls the paintFrame method below.
    
    public static void main(String[] args) throws Exception {
                
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final int[][] randomPermutations = makeRandom(permutations);
        final Button button1 = new Button();
        final Button button2 = new Button();        
        final IntroPanel intro = new IntroPanel();
        intro.setLocation(100,100);
        
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                frame.remove(intro);
                frame.repaint();
                frame.removeMouseListener(this);
                paintFrame(frame, button1, button2, randomPermutations, 0);                
            }            
        });
        
        frame.setLayout(null);
        frame.add(intro);
        frame.setBounds(20, 20, 1200, 900);
        frame.setVisible(true);
    }
    
    // The paintFrame method is the method that is called to add buttons
    // in their various positions on the screen. The buttons have action listeners
    // added to them which are set up to respond appropriately as the program
    // runs.
    
    public static void paintFrame(final JFrame frame, final Button button1, 
            final Button button2, final int[][] randomPermutations, int permutationsCount) {
        
        final double[] id = calculateID(randomPermutations);
        
        int horizontalPos1 = (1200 - (randomPermutations[permutationsCount][0] 
                + randomPermutations[permutationsCount][1]))/2;
        int horizontalPos2 = horizontalPos1 + randomPermutations[permutationsCount][1];
        
        final Button nextButton1 = new Button();
        final Button nextButton2 = new Button();
        
        button2.swapColour();
        
        button1.setSize(randomPermutations[permutationsCount][0], 600);
        button1.setLocation(horizontalPos1, 100);        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (button1.buttonColourOn.equals(Color.green)) {
                    if (button1.count == 0) {
                        button1.clock.start();
                    }
                    button1.swapColour();
                    button2.swapColour();
                    button1.count++;
                }
            }
        });
        
        button2.setSize(randomPermutations[permutationsCount][0], 600);
        button2.setLocation(horizontalPos2, 100);        
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (button2.buttonColourOn.equals(Color.green)) {
                    if (button2.count == 3) {
                        button1.clock.stop();
                        button2.removeAll();
                        frame.remove(button1);
                        frame.remove(button2);
                        frame.repaint();
                        captureData[button2.permutationsCount][1] = (button1.clock.getElapsedTime())/7;
                        captureData[button2.permutationsCount][0] = id[button2.permutationsCount];
                        button2.permutationsCount++;
                        if (button2.permutationsCount < randomPermutations.length) {
                            paintFrame(frame, nextButton1, nextButton2, 
                                    randomPermutations, button2.permutationsCount);
                        }
                        else {
                            final FinishedPanel finishedPanel = new FinishedPanel();
                            finishedPanel.setLocation(100,100);
                            frame.add(finishedPanel);
                            frame.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mousePressed(MouseEvent e) {
                                    final Plot plot = new Plot();
                                    frame.remove(finishedPanel);
                                    frame.removeMouseListener(this);
                                    frame.add(plot);
                                    frame.repaint();
                                    frame.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mousePressed(MouseEvent e) {
                                            frame.removeMouseListener(this);
                                            frame.remove(plot);
                                            frame.repaint();
                                            button2.permutationsCount = 0;
                                            captureData = new double[12][2];
                                            final int[][] randomPermutations = makeRandom(permutations);
                                            paintFrame(frame,nextButton1,nextButton2,randomPermutations,0);                                            
                                        }                                        
                                    });
                                }
                            });
                        }                            
                    }
                    button1.swapColour();
                    button2.swapColour();
                    button2.count++;
                }
            }
        });        
        frame.add(button1);
        frame.add(button2);
    }
    
    // The makeRandom method takes and array of integers and returns an
    // array of equal length containing the original integers in random
    // locations in the array.
    
    public static int[] makeRandom(int[] inputArray) {
        
        int[] randomArray = new int[inputArray.length];
        ArrayList<Integer> newArrayList = new ArrayList<Integer>();
        ArrayList<Integer> randomArrayList = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        
        for (int i = 0; i < inputArray.length; i++) {            
            newArrayList.add(inputArray[i]);            
        }
        
        for (int i = 0; i < inputArray.length; i++) {        
            int randomInt = randomGenerator.nextInt(newArrayList.size());
            randomArrayList.add(newArrayList.get(randomInt));
            newArrayList.remove(randomInt);        
        }
        
        for (int i = 0; i < inputArray.length; i++) {
            randomArray[i] = randomArrayList.get(i);
        }
        return randomArray;
    }
    
    // The makeRandom overloads the previous makeRandom method. It takes
    // a 2 dimensional array and, using the makeRandom method above, returns
    // a 2 dimensional array with the inner arrays located in random positions.
    // It is used to make the permutations of button widths and distances
    // random.
    
    public static int[][] makeRandom(int[][] inputArray) {
        
        int[] indices = new int[inputArray.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        int[] randomIndices = makeRandom(indices);
        int[][] randomArray = new int[inputArray.length][inputArray[0].length];
        for (int i = 0; i < randomArray.length; i++) {
            for (int j = 0; j < randomArray[0].length; j++) {
                randomArray[i][j] = inputArray[randomIndices[i]][j];
            }
        }
        return randomArray;
    }
    
    // Calculates the index of difficulty, using Fitts's model for ID.
    
    public static double[] calculateID(int[][] inputArray) {
        
        double[] indexOfDifficulty = new double[inputArray.length];
        for (int i = 0; i < indexOfDifficulty.length; i++) {
            indexOfDifficulty[i] = (Math.log((2*(double)inputArray[i][1])/inputArray[i][0]))/Math.log(2);
        }        
        return indexOfDifficulty;
    }
}