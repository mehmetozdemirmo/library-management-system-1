import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryGUI {
    private Library library;
    private JFrame frame;
    private JTextField titleField, authorField, idField;
    private JTextArea outputArea;

    public LibraryGUI() {
        library = new Library();
        frame = new JFrame("Library Management System");

        // Panel and Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        // Fields
        titleField = new JTextField();
        authorField = new JTextField();
        idField = new JTextField();
        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Buttons
        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton listButton = new JButton("List Books");

        // Panel Content
        panel.add(new JLabel("Book Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        panel.add(new JLabel("Book ID (Remove/Borrow/Return):"));
        panel.add(idField);

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(listButton);

        // Button Events
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            library.addBook(title, author);
            clearInputs();
        });

        removeButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                library.removeBook(id);
            } catch (NumberFormatException ex) {
                showError("Invalid ID");
            }
            clearInputs();
        });

        borrowButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                library.borrowBook(id);
            } catch (NumberFormatException ex) {
                showError("Invalid ID");
            }
            clearInputs();
        });

        returnButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                library.returnBook(id);
            } catch (NumberFormatException ex) {
                showError("Invalid ID");
            }
            clearInputs();
        });

        listButton.addActionListener(e -> {
            outputArea.setText(library.getBookList());
        });

        // Main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void clearInputs() {
        titleField.setText("");
        authorField.setText("");
        idField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI());
    }
}
