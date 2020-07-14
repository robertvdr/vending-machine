package com.techelevator;

public abstract class InventoryItem {
	//data types
	private String slot;
	private String name;
	private double price;
	private String type;
	private int currentStock;
	
	public InventoryItem(String identifier, String name, double price, String type, int currentStock) {
		this.slot = identifier;
		this.name = name;
		this.price = price;
		this.type = type;
		this.currentStock = currentStock;
	}
	
	//getters and setters
	public String getSlot() {
		return slot;
	}
	public void setSlot(String identifier) {
		this.slot = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}
	
	public abstract String dispense();
	

}
