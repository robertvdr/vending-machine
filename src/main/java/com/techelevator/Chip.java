package com.techelevator;

public class Chip extends InventoryItem {

	public Chip(String slot, String name, double price, String type, int currentStock) {
		super(slot, name, price, type, currentStock);
	}
		
	@Override
	public String dispense() {
		String message = "Crunch Crunch, Yum!";
		return message;
	}
}
