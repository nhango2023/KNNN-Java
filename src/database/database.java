package database;

import java.sql.*;
import javax.swing.*;

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
	public ResultSet Excute(String sql) {
		ResultSet result = null;
		try {
			CallableStatement callableStatement = conn.prepareCall(sql);
	        result = callableStatement.executeQuery();
	        return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
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