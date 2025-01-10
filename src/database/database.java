package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class database {
	public Connection conn = null;

//Phuong thuc thuc hien ket noi CSDL 
	public void connect() throws SQLException {
		try {
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/qlhoatdong"; // MySQL connection URL
	        Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = java.sql.DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Ket noi CSDL that bai", "Thong bao", 1);
		}
	}

//Phuong thuc dung de truy van CSDL 
	public ResultSet Excute(String sql) throws SQLException {
	    ResultSet result = null;
	    CallableStatement callableStatement = null;
	    try {
	        callableStatement = conn.prepareCall(sql);
	        result = callableStatement.executeQuery();
	    } catch (SQLException e) {
	        throw e; // Re-throw the exception to propagate it
	    }
	    return result;
	}
	
	public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}