import java.io.*;
import java.util.*;

class User implements Serializable {
    private String username;
    private String password;
    private double balance;

    public User(String username, String password, double initialBalance) {
        this.username = username;
        this.password = password;
        this.balance = initialBalance;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}

class BankingSystem {
    private HashMap<String, User> users = new HashMap<>();
    private static final String DATA_FILE = "users.dat";

    public BankingSystem() {
        loadUsers();
    }

    // User registration
    public void registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else {
            User newUser = new User(username, password, 0);
            users.put(username, newUser);
            saveUsers();
            System.out.println("Registration successful.");
        }
    }

    // User login
    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful.");
            return user;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    // Save user data to file
    private void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }

    // Load user data from file
    private void loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (HashMap<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        }
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();
        User currentUser = null;

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                bankingSystem.registerUser(username, password);
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                currentUser = bankingSystem.loginUser(username, password);

                if (currentUser != null) {
                    while (true) {
                        System.out.println("\n1. View Balance");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Logout");
                        System.out.print("Choose an option: ");
                        int userChoice = scanner.nextInt();

                        if (userChoice == 1) {
                            System.out.println("Your balance: $" + currentUser.getBalance());
                        } else if (userChoice == 2) {
                            System.out.print("Enter amount to deposit: ");
                            double amount = scanner.nextDouble();
                            currentUser.deposit(amount);
                        } else if (userChoice == 3) {
                            System.out.print("Enter amount to withdraw: ");
                            double amount = scanner.nextDouble();
                            currentUser.withdraw(amount);
                        } else if (userChoice == 4) {
                            System.out.println("Logging out...");
                            currentUser = null;
                            break;
                        } else {
                            System.out.println("Invalid option. Try again.");
                        }
                    }
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}
