import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CalculatorGUI extends JFrame {

    private JTextField display;
    private StringBuilder inputExpression;

    public CalculatorGUI() {
        inputExpression = new StringBuilder();

        setTitle("Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "C", "+",
                "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("C")) {
                inputExpression.setLength(0);
                display.setText("");
            } else if (command.equals("=")) {
                String expr = inputExpression.toString();
                try {
                    double result = evaluateExpression(expr);
                    display.setText(String.valueOf(result));
                    inputExpression.setLength(0);
                    inputExpression.append(result);
                } catch (Exception ex) {
                    display.setText("Error");
                    inputExpression.setLength(0);
                }
            } else {
                if ("+-*/".contains(command)) {
                    if (inputExpression.length() == 0) return;
                    char lastChar = inputExpression.charAt(inputExpression.length() - 1);
                    if ("+-*/".indexOf(lastChar) >= 0) return;
                }

                if (command.equals(".")) {

                    int i = inputExpression.length() - 1;
                    while (i >= 0 && (Character.isDigit(inputExpression.charAt(i)) || inputExpression.charAt(i) == '.')) {
                        if (inputExpression.charAt(i) == '.') return;
                        i--;
                    }
                }

                inputExpression.append(command);
                display.setText(inputExpression.toString());
            }
        }
    }

    private double evaluateExpression(String expr) throws Exception {
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        StringBuilder numberBuffer = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.') {
                numberBuffer.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (numberBuffer.length() == 0) {
                    throw new Exception("Invalid expression");
                }
                numbers.add(Double.parseDouble(numberBuffer.toString()));
                numberBuffer.setLength(0);
                operators.add(c);
            } else {
                throw new Exception("Invalid character");
            }
        }
        if (numberBuffer.length() > 0) {
            numbers.add(Double.parseDouble(numberBuffer.toString()));
        } else {
            throw new Exception("Invalid expression");
        }

        return calculateResult(numbers, operators);
    }

    private double calculateResult(List<Double> numbers, List<Character> operators) throws Exception {
        for (int i = 0; i < operators.size(); ) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                double left = numbers.get(i);
                double right = numbers.get(i + 1);
                double result;

                if (op == '*') {
                    result = left * right;
                } else {
                    if (right == 0) {
                        throw new Exception("Division by zero");
                    }
                    result = left / right;
                }

                numbers.set(i, result);
                numbers.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }

        while (!operators.isEmpty()) {
            char op = operators.get(0);
            double left = numbers.get(0);
            double right = numbers.get(1);
            double result;

            if (op == '+') {
                result = left + right;
            } else {
                result = left - right;
            }

            numbers.set(0, result);
            numbers.remove(1);
            operators.remove(0);
        }

        return numbers.get(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}
