package de.BukkitFabo.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class MySQL {

	private String host = "";
	private String database = "";
	private String user = "";
	private String password = "";
	
	public MySQL(String host, String database, String user, String password) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
	}
	
	public Connection con;
	public Connection openConnection() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?user=" + this.user + "&password=" + this.password + "&?autoreconnect=true");
		this.con = con;
		return con;
			
	}
	
	public Connection getConnection(){
		
		return con;
		
	}
	
	public boolean hasConnection(){
		
		try{
			return (con != null) || con.isValid(1);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
       
	public void queryUpdate(String query){
		
		Connection conn = con;
		PreparedStatement st = null;
		try{
			st = (PreparedStatement)conn.prepareStatement(query);
			st.executeUpdate();
		}catch (SQLException e){
			System.err.println("Failed to send update " + query + ".");
		}finally{
			closeRessources(null, st);
		}
		
	}
	
	public static void closeRessources(ResultSet rs, PreparedStatement st){
		
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(st != null){
			try{
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void closeConnection(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			con = null;
		}
	}
	
	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	
	public Integer getToggleTP(String name){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT ToggleTP FROM `PartyPlugin` WHERE Player = ?");
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return 0;
			int ToggleTP = rs.getInt("ToggleTP");
			rs.close();
			stmt.close();
			return ToggleTP;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public Integer setToggleTP(String name, boolean ToggleTP){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `PartyPlugin` SET ToggleTP = ? WHERE Player = ?");
			stmt.setInt(1, (ToggleTP) ? 1 : 0);
			stmt.setString(2, name);
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return (ToggleTP) ? 1 : 0;
	}
}

