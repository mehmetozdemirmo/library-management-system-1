import java.io.*;
import java.util.*;

public class ATMApp {

    public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        File file = new File("account.txt");

        if (!file.exists()) return accounts;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    String pin = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.add(new Account(username, pin, balance));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading accounts.");
        }
        return accounts;
    }

    public static void saveAllAccounts(List<Account> accounts) {
        try (FileWriter writer = new FileWriter("account.txt")) {
            for (Account acc : accounts) {
                writer.write(acc.getUsername() + "," + acc.getPin() + "," + acc.getBalance() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Account> accounts = loadAccounts();

        System.out.print("Enter your username: ");
        String inputUsername = input.nextLine();

        Account currentAccount = null;
        for (Account acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(inputUsername)) {
                currentAccount = acc;
                break;
            }
        }

        if (currentAccount == null) {
            System.out.println("User not found. Creating a new account...");
            System.out.print("Set a PIN for your new account: ");
            String newPin = input.nextLine();
            currentAccount = new Account(inputUsername, newPin, 0.0);
            accounts.add(currentAccount);
            saveAllAccounts(accounts);
            System.out.println("Account created successfully.");
        } else {
            System.out.print("Enter your PIN: ");
            String inputPin = input.nextLine();

            if (!currentAccount.authenticate(inputPin)) {
                System.out.println("Incorrect PIN. Access denied.");
                input.close();
                return;
            }
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("1. Show Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Change PIN");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Current balance: $" + currentAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = input.nextDouble();
                    input.nextLine();
                    currentAccount.deposit(depositAmount);
                    saveAllAccounts(accounts);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = input.nextDouble();
                    input.nextLine();
                    if (currentAccount.withdraw(withdrawAmount)) {
                        saveAllAccounts(accounts);
                    }
                    break;
                case 4:
                    System.out.print("Enter current PIN: ");
                    String currentPin = input.nextLine();
                    if (currentAccount.authenticate(currentPin)) {
                        System.out.print("Enter new PIN: ");
                        String newPin = input.nextLine();
                        currentAccount.setPin(newPin);
                        saveAllAccounts(accounts);
                        System.out.println("PIN changed successfully.");
                    } else {
                        System.out.println("Incorrect current PIN. PIN change failed.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        input.close();
    }
}
