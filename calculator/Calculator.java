import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Calculator {

    public static double readNumber(Scanner input) {
        while (true) {
            System.out.print("Enter a number: ");
            String entry = input.nextLine();

            try {
                return Double.parseDouble(entry);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static char readOperator(Scanner input) {
        while (true) {
            System.out.print("Enter an operator (+, -, *, /, =): ");
            String entry = input.nextLine();

            if (entry.length() == 1) {
                char operator = entry.charAt(0);
                if (operator == '+' || operator == '-' || operator == '*' || operator == '/' || operator == '=') {
                    return operator;
                }
            }

            System.out.println("Invalid operator. Please try again.");
        }
    }

    public static double calculateResult(List<Double> numbers, List<Character> operators) {
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
                        System.out.println("Error: Division by zero");
                        return Double.NaN;
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
        Scanner input = new Scanner(System.in);
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        while (true) {
            double number = readNumber(input);
            numbers.add(number);

            char operator = readOperator(input);
            if (operator == '=') {
                break;
            }

            operators.add(operator);
        }

        double result = calculateResult(numbers, operators);
        System.out.println("Result: " + result);
    }
}
