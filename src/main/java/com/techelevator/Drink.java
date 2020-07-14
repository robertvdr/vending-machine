package com.techelevator;

public class Drink extends InventoryItem {

	public Drink(String slot, String name, double price, String type, int currentStock) {
		super(slot, name, price, type, currentStock);
	}
	@Override
	public String dispense() {
		String message = "Glug Glug, Yum!";
		return message;
	}

}
