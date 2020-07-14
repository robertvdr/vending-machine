package com.techelevator;

public class Candy extends InventoryItem {

	public Candy(String slot, String name, double price, String type, int currentStock) {
		super(slot, name, price, type, currentStock);
	}

	@Override
	public String dispense() {
		String message = "Munch Munch, Yum!";
		return message;
	}

}
