package com.techelevator;

public class Gum extends InventoryItem {

	public Gum(String slot, String name, double price, String type, int currentStock) {
		super(slot, name, price, type, currentStock);
	}
	@Override
	public String dispense() {
		String message = "Chew Chew, Yum!";
		return message;
	}

}
