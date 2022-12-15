import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CodeEditor extends JFrame {
    private JTextArea textArea;
    private JTextArea lineNumberArea;
    private int currentLineNumber;

    public CodeEditor() {
        // Set the title and default close operation
        super("Code Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JTextArea with a set preferred size
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(600, 400));

        // Create a JTextArea for the line numbers with a fixed width
        lineNumberArea = new JTextArea(1, 5);
        lineNumberArea.setEditable(false);

        // Add the JTextAreas to a scroll pane to make them scrollable
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(lineNumberArea);

        // Create a toolbar with a "Load" button
        JToolBar toolBar = new JToolBar();
        JButton loadButton = new JButton(new ImageIcon("file_icon.png"));
        loadButton.setPreferredSize(new Dimension(30, 30));
        loadButton.setToolTipText("Load file");
        toolBar.add(loadButton);

        // Add an action listener to the "Load" button to open a file
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(CodeEditor.this);

                // If a file was selected, load its contents into the text area
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Open the selected file and read its contents
                        FileReader reader = new FileReader(fileChooser.getSelectedFile());
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }

                        // Set the text area's text to the contents of the file
                        textArea.setText(stringBuilder.toString());

                        // Close the file reader
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Add the toolbar and scroll pane to the frame
// Add the toolbar and scroll pane to the frame
        add(toolBar, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);

// Update the line number area when the text area's document is changed
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });

// Set the initial line numbers
        updateLineNumbers();

// Pack the frame to the preferred size of its contents
        pack();
    }

    private void updateLineNumbers() {
        // Get the line count from the text area's document
        Element root = textArea.getDocument().getDefaultRootElement();
        int lineCount = root.getElementIndex(textArea.getDocument().getLength()) + 1;

        // Update the line number area with the line count
        String lineNumbers = "";
        for (int i = 0; i < lineCount; i++) {
            lineNumbers += i + 1 + "\n";
        }
        lineNumberArea.setText(lineNumbers);
    }

    public static void main(String[] args) {
        // Create a new CodeEditor instance and make it visible
        CodeEditor editor = new CodeEditor();
        editor.setVisible(true);
    }
}
