package edu.cscc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates a new orders table in the PizzaStore database and inserts new orders into that table.
 * @author Mutasem Alhariri 02-18-2020
 *
 */
public class PizzaStore {
	private final String user;
	private final String pass;
	private final String port;
	private final String host;
	private final String database;
	
	private String connectionURL = null;
	
	
	private static final String prefix = "malhariri";
	private static String ordersTableName = prefix+"_Orders";
	
	// DDL to delete orders table
	private static final String DELETE_DDL =
			"DROP TABLE IF EXISTS "+ordersTableName+";";
	
	// DDL to create orders table
	private static final String CREATE_DDL = 
		"CREATE TABLE "+ordersTableName+"(" + 
				"id INT NOT NULL IDENTITY PRIMARY KEY," +
				"quantity INT NOT NULL, "+
				"orderDate VARCHAR(40) NOT NULL," +
				"size VARCHAR(10) NOT NULL,"+ 
				"type VARCHAR(10) NOT NULL,"+ 
				"toppings VARCHAR(10) NOT NULL);";
	
	private final static String INSERTSQL = "INSERT INTO "+ordersTableName+"(quantity,orderDate,size,type,toppings) VALUES(?,?,?,?,?);";
	
	public PizzaStore(String user, String pass, String port, String host, String database) {
		super();
		this.user = user;
		this.pass = pass;
		this.port = port;
		this.host = host;
		this.database = database;
		this.connectionURL = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + database + ";user=" + user
				+ ";password=" + pass + ";";
	}

	public boolean createOrdersTable() {
		boolean result = true;
		try (Connection conn = DriverManager.getConnection(connectionURL);
				Statement stmt = conn.createStatement();) {
			
			stmt.execute(DELETE_DDL);
			
            stmt.execute(CREATE_DDL);

		} catch (SQLException e) {
			System.out.println(connectionURL);
			System.out.println(e.getMessage());
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean placeOrder(PizzaOrder order) {
		int numrows = 0;
		PreparedStatement pstmt = null;
		
		try(Connection conn = DriverManager.getConnection(connectionURL);){
			
			pstmt = conn.prepareStatement(INSERTSQL);
			
			pstmt.setInt(1,order.getQuantity());
			pstmt.setString(2,order.getOrderDate());
			pstmt.setString(3,order.getSize().toString());
			pstmt.setString(4,order.getType().toString());
			pstmt.setString(5,order.getTopping().toString());
			
			numrows = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println(connectionURL);
			System.out.println(e.getMessage());
			e.printStackTrace();
			numrows = 0;
		}
		return (numrows == 1);
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public String getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public String getDatabase() {
		return database;
	}
}
