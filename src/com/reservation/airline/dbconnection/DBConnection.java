package com.reservation.airline.dbconnection;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DBConnection {
	
	public Connection dbConnect(){
		System.out.println("inside connect");
		Connection con = null;
		try {
			//Context ctx = new InitialContext();
			DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/AirlineDB");
			con = ds.getConnection();
			System.out.println("ds: "+ds);
			System.out.println("Con: "+con);
			if(con != null){
				System.out.println("Connection done!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}

}
