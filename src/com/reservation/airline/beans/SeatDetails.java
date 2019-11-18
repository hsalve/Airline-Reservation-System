package com.reservation.airline.beans;

public class SeatDetails {
	
	private boolean booked;
	private String seatType;
	private String bookedBy;
	private boolean isSameUser;
	private boolean isDisabled;
	
	
	public boolean isBooked() {
		return booked;
	}
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
	public String getBookedBy() {
		return bookedBy;
	}
	public void setBookedBy(String bookedBy) {
		this.bookedBy = bookedBy;
	}
	public boolean isSameUser() {
		return isSameUser;
	}
	public void setSameUser(boolean isSameUser) {
		this.isSameUser = isSameUser;
	}
	public boolean isDisabled() {
		return isDisabled;
	}
	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	
	
	
	

}
