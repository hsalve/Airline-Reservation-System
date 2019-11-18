package com.reservation.airline.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;



import com.reservation.airline.dbconnection.DBConnection;

@ManagedBean(name="user")
@SessionScoped
public class User {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private int seatsReserved;
	private boolean maxSeatReserved;
	private boolean AlreadyReserved;
	private String status;
	private Map<String,SeatDetails> seatMap;
	private List<String> keyList;
	DataSource ds;
	private String seats;
	private String isloggedin = "false";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getIsloggedin() {
		return isloggedin;
	}
	public void setIsloggedin(String isloggedin) {
		this.isloggedin = isloggedin;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSeatsReserved() {
		return seatsReserved;
	}
	public void setSeatsReserved(int seatsReserved) {
		this.seatsReserved = seatsReserved;
	}
	
	public boolean isMaxSeatReserved() {
		return maxSeatReserved;
	}
	public void setMaxSeatReserved(boolean maxSeatReserved) {
		this.maxSeatReserved = maxSeatReserved;
	}
	public boolean isAlreadyReserved() {
		return AlreadyReserved;
	}
	public void setAlreadyReserved(boolean alreadyReserved) {
		AlreadyReserved = alreadyReserved;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public Map<String, SeatDetails> getSeatMap() {
		return seatMap;
	}
	public void setSeatMap(Map<String, SeatDetails> seatMap) {
		this.seatMap = seatMap;
	}
	
	
	public List<String> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}
	
	public String getSeats() {
		return seats;
	}
	public void setSeats(String seats) {
		this.seats = seats;
	}
	public String login(){
		
		String page = "login";
		System.out.println("Login called");
		Connection con = new DBConnection().dbConnect();
		Statement st = null;
		if(con != null){
			try {
				st = con.createStatement();				
				String query = "select * from userdetails " +
								"where username = '" + userName + "'" + " AND " + "Password = '" + password + "';";
				System.out.println(query);
				ResultSet rs = st.executeQuery(query);
				
				System.out.println("Resultset ===> "+rs);
				
				if(rs.next()){
					//if(rs.getString(1).equalsIgnoreCase("1")){
						userName = rs.getString(1);
						firstName = rs.getString(2);
						password = rs.getString(6);
						lastName = rs.getString(3);
						email = rs.getString(4);
						status = rs.getString(7);
						System.out.println(rs.getString(8));
						if (rs.getString(9).equals("Admin")){
							if(rs.getString(8).equals("false")){
								page = "Admin";}
								
								else{
									page="AlreadyLoggedIn";
								}
							
						
						}
						else if(rs.getString(8).equals("false")){
						page = "home";}
						
						else{
							page="AlreadyLoggedIn";
						}
						
						
						System.out.println("status="+status);
						System.out.println("page="+page);
						isloggedin="true";
						String query1="update userdetails set loggedin='true'where username = '" + userName +"'";
						st.execute(query1);
					//}
				}else{
					return "UserNotFound";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		createSeatMap();
		return page;
	}
	
	public void add() throws SQLException{
		
		
		System.out.println("add called");
		//new DBConnection().dbConnect();

		 int i = 0;
	       
        PreparedStatement ps = null;
        Connection con = null;
        try {
            
	        con =  new DBConnection().dbConnect();
	        if (con != null) {
	            String sql = "INSERT INTO userdetails(username, FirstName, Password, LastName, Emailid, Status) VALUES(?,?,?,?,?,?)";
	            ps = con.prepareStatement(sql);
	            ps.setString(1, userName);
	            ps.setString(2, firstName);
	            ps.setString(3, password);
	            ps.setString(4, lastName);
	            ps.setString(5, email);
	            ps.setString(6, status);
	            
	            i = ps.executeUpdate();
	            System.out.println("Data Added Successfully");
        }
            
        } catch (Exception e) {
            System.out.println(e);}
         finally {
            try {
                con.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	private void createSeatMap(){
		seatMap = new LinkedHashMap<String,SeatDetails>();
		
		Connection con = new DBConnection().dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String query = "select * from seatdetails;";
		System.out.println("creating seatMap");
		
		if(con!=null){
			try {
				st = con.createStatement();
				rs = st.executeQuery(query);
				List<String> seatList = new ArrayList<String>();
				while(rs.next()){
					
					SeatDetails seatDetails = new SeatDetails();
					seatDetails.setBooked(rs.getBoolean(2));
					seatDetails.setSeatType(rs.getString(3));
					seatDetails.setBookedBy(rs.getString(4));
					
					/* enabling disabling seats*/
					if(status.equalsIgnoreCase("Standard") && seatDetails.getSeatType().equalsIgnoreCase("Luxury")){
						seatDetails.setDisabled(true);
					}
					if(!userName.equalsIgnoreCase(seatDetails.getBookedBy())&& seatDetails.getBookedBy()!=null){
						seatDetails.setDisabled(true);
					}
					
					/*checking for number of seats reserved*/
					if(userName.equalsIgnoreCase(seatDetails.getBookedBy())){
						seatsReserved ++;
						seatLimitCheck();
					}
					seatMap.put(rs.getString(1), seatDetails);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}else{
			System.out.println("Connection null");
		}
		
		keyList = new ArrayList<String>(seatMap.keySet());
		
	}
	
	public String book(){

		System.out.println("Inside book");
		System.out.println("Seats Reserved: "+seatsReserved);
		System.out.println("maxSeatReserved: "+maxSeatReserved);
		
		for (Map.Entry<String, SeatDetails> entry : seatMap.entrySet()) {
		   //System.out.println(entry.getKey() + "----> " + entry.getValue().isBooked());
			if(entry.getValue().isBooked() && entry.getValue().getBookedBy()==null){
				if(!maxSeatReserved){
					addSeat(entry);
				}
				//continue;
			}
			
			if(!entry.getValue().isBooked() && userName.equalsIgnoreCase(entry.getValue().getBookedBy())){
				removeSeat(entry);
			}
		}

		
		return "home";
		
	}
	
	private void addSeat(Map.Entry<String, SeatDetails> entry){
		Connection con = null;
		Statement st = null;
		con = new DBConnection().dbConnect();
		try {
			st = con.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String query1 = "select bookedby from seatdetails"+ " WHERE seatname = '" + entry.getKey() + "';";;
		System.out.println(query1);		
		try {
			ResultSet rs = st.executeQuery(query1);
			if(rs.next()){
				
				if(rs.getString(1) == null){
				  
					System.out.println(rs.getString(1));
					String query = "UPDATE seatdetails " +
							"SET isBooked= " + entry.getValue().isBooked() + "," + "bookedBy ='" + userName + "' " + 
							"WHERE seatname = '" + entry.getKey() + "';";
					
					
					System.out.println("query: "+query1);
					seatAlreadyReserved();
					
					int records = 0;
					
					try {
						
						records = st.executeUpdate(query);
						
						if(records > 0){
							entry.getValue().setBookedBy(userName);
							seatsReserved ++;
							seatLimitCheck();
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
				}
				
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		
	}
	
	private void removeSeat(Map.Entry<String, SeatDetails> entry){
		String query = "UPDATE seatdetails " +
				"SET isBooked= " + entry.getValue().isBooked() + "," + "bookedBy = " + null + " " + 
				"WHERE seatname = '" + entry.getKey() + "';";
		
		System.out.println("query: "+query);
		
		Connection con = null;
		Statement st = null;
		int records = 0;
		
		try {
			con = new DBConnection().dbConnect();
			st = con.createStatement();
			records = st.executeUpdate(query);
			
			if(records > 0){
				entry.getValue().setBookedBy(null);
				seatsReserved --;
				seatLimitCheck();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void seatLimitCheck(){
		if(seatsReserved >= 4){
			maxSeatReserved = true;
		}else{
			maxSeatReserved = false;
		}
		
		System.out.println("maxSeatReserved: "+maxSeatReserved);
	}
	private void seatAlreadyReserved()
	{
		AlreadyReserved=true;
	}

	
	public String viewUserDetails(){
		List<String> seatList = new ArrayList<String>();
		for (Map.Entry<String, SeatDetails> entry : seatMap.entrySet()) {
			  if(userName.equalsIgnoreCase(entry.getValue().getBookedBy()))
				  seatList.add(entry.getKey());
		}
		
		seats="";
		if(seatList.size() == 0){
			seats = "No reservations made";
		}else{
			for(int i = 0;i<seatList.size();i++){
				if(i != seatList.size() - 1 )
					seats = seats + seatList.get(i) + ",";
				else
					seats = seats + seatList.get(i);
			}
			
		}
		
		return "UserDetails";
		
	}
	
	public String logout(){
		Connection con = new DBConnection().dbConnect();
		Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String query1="update userdetails set loggedin='false'where username = '" + userName +"'";
		try {
			st.execute(query1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
	}
	
	
	

}
