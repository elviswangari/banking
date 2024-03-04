package bank;

import bank.exceptions.AmountException;

public class Accounts {
    // variables
    private int id;
    private String type;
    private double balance;

    // constructor
    public Accounts(int id, String type, double balance) {
        setId(id);
        setType(type);
        setBalance(balance);
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) throws AmountException {
        if (amount < 1) {
            throw new AmountException("Deposit should be greater than 1");
        } else {
            double newBalance = amount + balance;
            setBalance(newBalance);
            DataSource.updateAccountBalance(id, newBalance);
        }
    }

    public void withdraw(double amount) throws AmountException {
        if (amount < 5) {
            throw new AmountException("withdraw amount should be greater than 5");
        } else if (amount < getBalance()) {
            throw new AmountException("Insufficient Balance");
        } else {
            double newBalance = balance - amount;
            setBalance(newBalance);
            DataSource.updateAccountBalance(id, newBalance);
        }
    }
}
