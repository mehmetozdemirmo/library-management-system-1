public class Account {
    private String username;
    private String pin;
    private double balance;

    public Account(String username, String pin, double balance) {
        this.username = username;
        this.pin = pin;
        this.balance = balance;
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("$" + amount + " has been deposited. Current balance: $" + this.balance);
    }

    public boolean withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient balance! Transaction failed.");
            return false;
        }
        this.balance -= amount;
        System.out.println("$" + amount + " has been withdrawn. Current balance: $" + this.balance);
        return true;
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
