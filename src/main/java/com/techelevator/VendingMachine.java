package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.Scanner;

public class VendingMachine {

	private Map<String, InventoryItem> inventoryMap = new LinkedHashMap<String, InventoryItem>();
	private static final int INITIAL_INVENTORY = 5;

	// read in file for vending machine inventory, initialize each item into their
	// appropriate subclasses
	// populate a map with slot(A1, A2, etc.) as key and inventoryItem as value
	// we can reach each inventory item with their slot
	public void populateVendingMachine() {
		File inputFile = new File("VendingMachine.txt");

		String itemString = "";
		String[] inventoryArray = null;
		

		
		try (Scanner dataInput = new Scanner(inputFile)) {
			while (dataInput.hasNext()) {
				itemString = dataInput.nextLine();
				inventoryArray = itemString.split("\\|");

				switch (inventoryArray[3]) {

				case "Chip":
					Chip chip = new Chip(inventoryArray[0], inventoryArray[1], Double.parseDouble(inventoryArray[2]),
							inventoryArray[3], INITIAL_INVENTORY);
					inventoryMap.put(inventoryArray[0], chip);
					break;

				case "Candy":
					Candy candy = new Candy(inventoryArray[0], inventoryArray[1], Double.parseDouble(inventoryArray[2]),
							inventoryArray[3], INITIAL_INVENTORY);
					inventoryMap.put(inventoryArray[0], candy);
					break;

				case "Drink":
					Drink drink = new Drink(inventoryArray[0], inventoryArray[1], Double.parseDouble(inventoryArray[2]),
							inventoryArray[3], INITIAL_INVENTORY);
					inventoryMap.put(inventoryArray[0], drink);
					break;

				case "Gum":
					Gum gum = new Gum(inventoryArray[0], inventoryArray[1], Double.parseDouble(inventoryArray[2]),
							inventoryArray[3], INITIAL_INVENTORY);
					inventoryMap.put(inventoryArray[0], gum);
					break;
					
				default:
					//Do we need this to print at the CLI instead of here?
					System.out.println("Invalid inventory type");
				}
			}
		} catch (FileNotFoundException e) {
			//Do we need this to print from the CLI instead of here?
			System.out.println("Could not find file.");
		}

	}

	public Map<String, InventoryItem> getMap() {
		return inventoryMap;
	}

	public String getInventoryItems() {
//		System.out.println();
		String header = ("\nSLOT  ITEM                 PRICE\n");
		String output = "";
		for (Map.Entry<String, InventoryItem> entry : inventoryMap.entrySet()) {
			String k = entry.getKey();
			String v = entry.getValue().getName();
			String p = "$" + Double.toString(entry.getValue().getPrice());
			if (p.length() < 5) {
				p = p + "0";
			}
			if (entry.getValue().getCurrentStock() > 0) {
				output += String.format("%-5s %-19s %6s\n", k, v, p);

			} else {
				output += String.format("%-5s %-19s %6s **SOLD OUT**\n", k, v, p);
			}
		}
		return header + output;
	}

	
	
}
