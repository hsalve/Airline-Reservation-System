package com.reservation.airline.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.reservation.airline.dbconnection.DBConnection;
@FacesValidator("usernameValidator")

public class UsernameValidator implements Validator  {
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		//String page = "login";
		System.out.println("Validator called");
		Connection con = new DBConnection().dbConnect();
		Statement st = null;
		if(con != null){
			try {
				st = con.createStatement();
				String query = "SELECT IF( EXISTS(SELECT USERNAME FROM USERDETAILS WHERE USERNAME='"
								+ value.toString() + "' ),1,0);";
				System.out.println(query);
				ResultSet rs = st.executeQuery(query);
				
				if(rs.next()){
					if(rs.getString(1).equalsIgnoreCase("1")){
						System.out.println("hello fom validator");
						FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username Validation failed", "Username is not Unique.");
						 throw new ValidatorException(fmsg);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		
		/*if (!matcher.matches()) {
			FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Validation failed", "Invalid Email.");
			 throw new ValidatorException(fmsg);
		}*/
	}
} 