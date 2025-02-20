package test;

import javax.swing.*;

public class Example {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Multi-line Text Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JTextArea
        JTextArea textArea = new JTextArea(10, 30); // 10 rows and 30 columns
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap at word boundaries

        // Add JTextArea to a JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        // Display the frame
        frame.setVisible(true);
    }
}
