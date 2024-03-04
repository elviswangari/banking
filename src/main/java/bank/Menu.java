package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
    private Scanner scanner;

    public static void main(String[] args) {
        System.err.println("WELCOME TO GLOBE BANK INTERNATIONAL!");
        Menu menu = new Menu();
        menu.scanner = new Scanner(System.in);

        Customer customer = menu.authenticateUser();

        if (customer != null) {
            Accounts account = DataSource.getAccount(customer.getAccountId());
            menu.showMenu(customer, account);
        } else {

        }

        menu.scanner.close();
    }

    private void showMenu(Customer customer, Accounts account) {
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        int selection = 0;

        while (selection != 4 && customer.isAuthenticated()) {
            System.out.println("============================");
            System.out.println("please select an option");
            System.out.println("1: Deposit");
            System.out.println("2: Withdraw");
            System.out.println("3: Balance");
            System.out.println("4: Exit");
            System.out.println("============================");

            selection = scanner.nextInt();
            double amount = 0;
            switch (selection) {
                case 1:
                    System.out.println("How much would you like to deposit");
                    amount = scanner.nextDouble();
                    try {
                        account.deposit(amount);
                    } catch (AmountException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please try again");
                    }
                    break;
                case 2:
                    System.out.println("How much would you like to withdraw");
                    amount = scanner.nextDouble();
                    try {
                        account.withdraw(amount);
                    } catch (AmountException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please try again");
                    }
                    break;
                case 3:
                    System.out.println("Current balance " + account.getBalance());
                    break;

                case 4:
                    Authenticator.logout(customer);
                    System.out.println("Thankyou for banking with us");
                default:
                    break;
            }
        }
    }

    private Customer authenticateUser() {
        System.out.println("Enter your username");
        String username = scanner.next();

        System.out.println("Enter your password");
        String password = scanner.next();

        Customer customer = null;

        try {
            customer = Authenticator.login(username, password);
        } catch (LoginException e) {
            e.printStackTrace();
        }
        return customer;

    }
}
