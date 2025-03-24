package com.solwyz.deliveryBoy.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.solwyz.deliveryBoy.Enum.Role;

@Entity
@Table(name = "delivery_boy")
public class DeliveryBoy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password; // Storing the password securely
	private String mpin; // MPIN (hashed)
	private String assignedArea;
	private boolean isOnline; // Online status
	
	
	
	private String address;
	
	private String mobileNumner;
	

	@Enumerated(EnumType.STRING)
	private Role role; // Role (either ADMIN or DELIVERY_BOY)

	@OneToMany(mappedBy = "deliveryBoy")
    @JsonManagedReference  // Helps manage serialization
    private List<Order> orders;
	
	private String resetToken;
	private Date resetTokenExpiryDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public String getAssignedArea() {
		return assignedArea;
	}

	public void setAssignedArea(String assignedArea) {
		this.assignedArea = assignedArea;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean online) {
		isOnline = online;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumner() {
		return mobileNumner;
	}

	public void setMobileNumner(String mobileNumner) {
		this.mobileNumner = mobileNumner;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Date getResetTokenExpiryDate() {
		return resetTokenExpiryDate;
	}

	public void setResetTokenExpiryDate(Date resetTokenExpiryDate) {
		this.resetTokenExpiryDate = resetTokenExpiryDate;
	}
	
	
}
