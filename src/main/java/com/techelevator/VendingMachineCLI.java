package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	
	
	// audit file needs to clear each time the program is ran (?)


	// log current date and time
	public String dateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
		return sdf.format(date);

	}

	// private vending machine
	private Menu menu;
	private VendingMachine vm;
	private Transaction transaction;

	// create scanner
	Scanner userInput = new Scanner(System.in);

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	String file = "VendingMachineAudit.txt";

	// create audit file
	public void createAudit() {
		File myFile = new File("VendingMachineAudit.txt");
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			System.out.println("File creation failed");
			e.printStackTrace();
		}
	}


	// write to audit file
	public void printAudit(String message) {
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
			writer.write(message);
			writer.close();
			// writer.println("Hello");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}

	}

	// if balance = 0 when selecting product, prompt user to deposit money
	// displays name of product when selected
	
	public void selectProduct() {
		// vm.populateVendingMachine();
		if (transaction.getCurrentBalance() == 0) {
			System.out.println("Your current balance is: $0.00.  Please make a deposit before selecting an item.");
		} else {
			// get item selection from user
			System.out.println("Enter item to purchase: ");
			String purchaseChoice = "";
			boolean flag = false;
			while (!flag) {
				try {
					purchaseChoice = userInput.nextLine().toUpperCase();

					Map<String, InventoryItem> inventoryMap = vm.getMap();
					InventoryItem newPurchase = inventoryMap.get(purchaseChoice);
					if (newPurchase.getCurrentStock() <= 0) {
						System.out.println("Item out of stock, please make another selection");
						break;
					} else {
						// get item name from selection
						String newPurchaseStr = newPurchase.getName();
						// get item price from selection
						double purchasePrice = newPurchase.getPrice();
						// update purchase price after item is selected
						transaction.setCurrentBalance(transaction.getCurrentBalance() - purchasePrice);
						// update current stock after item is selected
						newPurchase.setCurrentStock(newPurchase.getCurrentStock() - 1);
						// dispense message displayed
						String dispenseMessage = newPurchase.dispense();
						// **.getIdentifier should be .getSlot
						String currentBalanceFormatted = "";
						currentBalanceFormatted = String.format("$%.2f", transaction.getCurrentBalance());
						String purchasePriceFormatted = "";
						purchasePriceFormatted = String.format("$%.2f", newPurchase.getPrice());
						// what the user sees when they select an item
						System.out.println(dispenseMessage + "\n" + newPurchaseStr + " Purchased for "
								+ purchasePriceFormatted + "\nCurrent Balance: " + currentBalanceFormatted);
						// audit log
						printAudit(dateTime() + " " + newPurchase.getName() + " " + newPurchase.getSlot() + " $"
								+ newPurchase.getPrice() + " " + currentBalanceFormatted + "\n");
						flag = true;
					}
				} catch (NullPointerException e) {
					System.out.println("Enter a valid item selection");
					flag = false;
				}

			}
		}
	}

	// deposits money into current balance, re-prompts if string entered instead of number

	public String feedMoney() {
		String output = "";
		boolean validEntry = false;
		System.out.println("\nPlease enter amount to deposit in whole number amounts: ");
		while (!validEntry) {

			try {
				String currentBalanceFormatted = "";
				int depositAmount = Integer.valueOf(userInput.nextLine());
				if (depositAmount >= 0) {
					transaction.setDepositAmount(depositAmount);
					transaction.setCurrentBalance(transaction.getCurrentBalance() + transaction.getDepositAmount());
					output += ("Successfully Deposited: $" + transaction.getDepositAmount() + ".00");
					output += String.format("\nCurrent Balance: $%.2f", transaction.getCurrentBalance());
					currentBalanceFormatted = String.format("$%.2f", transaction.getCurrentBalance());
					validEntry = true;
					printAudit(dateTime() + " FEED MONEY: $" + transaction.getDepositAmount() + ".00 "
							+ currentBalanceFormatted + "\n");
					validEntry = true;
				} else {
					System.out.println("Enter a positive number: ");
					validEntry = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Enter whole number: ");
				validEntry = false;
			}

		}
		return output;
	}

	public void run() {
		vm = new VendingMachine();
		transaction = new Transaction();
		vm.populateVendingMachine();
		createAudit();
		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(vm.getInventoryItems());

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while (true) {
					String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						System.out.println(feedMoney());
					} else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						selectProduct();
					} else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
	
						System.out.println("\nChange returned: ");
						int balanceInCents = transaction.getCurrentBalanceInCents();
						System.out.println(transaction.returnChange(balanceInCents));

						String beforeBalanceFormatted = "";
						beforeBalanceFormatted = String.format("$%.2f", transaction.getBalanceBeforeChangeGiven());
						String afterBalanceFormatted = "";
						afterBalanceFormatted = String.format("$%.2f", transaction.getBalanceAfterChangeGiven());
						printAudit(dateTime() + " " + "GIVE CHANGE: " + beforeBalanceFormatted + " "
								+ afterBalanceFormatted + "\n");
						transaction.resetCoins(0);
						break;

					}

				}
			}

			else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thank you!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
