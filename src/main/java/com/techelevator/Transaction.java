package com.techelevator;

public class Transaction {
	// data types
	private double currentBalance;
	private int depositAmount;
	private int currentBalanceInCents;
	private int quarters;
	private int nickels;
	private int dimes;
	private double balanceBeforeChangeGiven;
	private double balanceAfterChangeGiven;

	// constructor
	public Transaction() {
		currentBalance = 0;
	}

	public int getQuarters() {
		return quarters;
	}

	public void setQuarters(int quarters) {
		this.quarters = quarters;
	}

	public int getNickels() {
		return nickels;
	}

	public void setNickels(int nickels) {
		this.nickels = nickels;
	}

	public int getDimes() {
		return dimes;
	}

	public void setDimes(int dimes) {
		this.dimes = dimes;
	}

	// getters and setters
	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public int getCurrentBalanceInCents() {
		this.currentBalanceInCents = (int) (currentBalance * 100);
		return this.currentBalanceInCents;
	}

	public void setCurrentBalanceInCents(int currentBalanceInCents) {
		this.currentBalanceInCents = currentBalanceInCents;
	}

	public String returnChange(int currentBalanceInCents) {
		balanceBeforeChangeGiven = getCurrentBalance();
		String change = "";
		while (currentBalanceInCents > 0) {
			if (currentBalanceInCents >= 25) {
				currentBalanceInCents -= 25;
				quarters += 1;
			} else if (currentBalanceInCents >= 10) {
				currentBalanceInCents -= 10;
				dimes += 1;
			} else if (currentBalanceInCents >= 5) {
				currentBalanceInCents -= 5;
				nickels += 1;
			} else {
				change = "no change";
			}
		}
		currentBalance = 0;
		balanceAfterChangeGiven = getCurrentBalance();
		return change = "Quarters: " + quarters + "\nDimes: " + dimes + "\nNickels: " + nickels;
		
	}
	
	public void resetCoins(int coinValue) {
		quarters = coinValue;
		nickels = coinValue;
		dimes = coinValue;
	}

	public double getBalanceBeforeChangeGiven() {
		return balanceBeforeChangeGiven;
	}

	public double getBalanceAfterChangeGiven() {
		return balanceAfterChangeGiven;
	}

}
