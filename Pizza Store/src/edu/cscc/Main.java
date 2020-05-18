package edu.cscc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementations of the PizzaStore class to create a new orders table.
 * @author Mutasem Alhariri 02-18-2020
 *
 */

public class Main {

	// Database credentials
	static final String USER = "userName";
	static final String PASS = "password";
	static final String PORT = "1433";
	static final String HOST = "databaseName.hostName.com";
	static final String DATABASE = "PizzaStore";

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	static String datestr = LocalDateTime.now().format(formatter);
	static final PizzaOrder[] orders = {
			new PizzaOrder(1, datestr, PizzaSize.MEDIUM, PizzaType.REGULAR, PizzaTopping.CHEESE),
			new PizzaOrder(1, datestr, PizzaSize.LARGE, PizzaType.DEEPDISH, PizzaTopping.PEPPERONI),
			new PizzaOrder(1, datestr, PizzaSize.SMALL, PizzaType.NOCRUST, PizzaTopping.VEGGIE),
			new PizzaOrder(1, datestr, PizzaSize.MEDIUM, PizzaType.THIN, PizzaTopping.PEPPERONI),
			new PizzaOrder(5, datestr, PizzaSize.LARGE, PizzaType.REGULAR, PizzaTopping.CARNIVORE),
			new PizzaOrder(1, datestr, PizzaSize.MEDIUM, PizzaType.NOCRUST, PizzaTopping.VEGGIE), };

	public static void main(String[] args) {
		PizzaStore store = new PizzaStore(USER, PASS, PORT, HOST, DATABASE);
		if (!store.createOrdersTable()) {
			System.out.println("Cannot create orders table");
			System.exit(-1);
		}

		for (PizzaOrder order : orders) {
			if (!store.placeOrder(order)) {
				System.out.println("Cannot create order");
			}
		}
		System.out.println("Done");
	}
}
