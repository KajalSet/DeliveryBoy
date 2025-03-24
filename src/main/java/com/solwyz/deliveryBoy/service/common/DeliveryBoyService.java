package com.solwyz.deliveryBoy.service.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.solwyz.deliveryBoy.Enum.Role;
import com.solwyz.deliveryBoy.Exceptions.AuthenticationException;
import com.solwyz.deliveryBoy.Exceptions.GenericException;
import com.solwyz.deliveryBoy.filters.JwtTokenProvider;
import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.pojo.request.AuthenticationRequest;
import com.solwyz.deliveryBoy.pojo.request.RefreshTokenRequest;
import com.solwyz.deliveryBoy.pojo.response.AuthenticationResponse;
import com.solwyz.deliveryBoy.repositories.common.DeliveryBoyRepository;


@Service
public class DeliveryBoyService {
	
	@Autowired
	private DeliveryBoyRepository deliveryBoyRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// Register new Delivery Boy (Admin)
	public DeliveryBoy registerDeliveryBoy(DeliveryBoy deliveryBoy) {
		// Register without MPIN
		deliveryBoy.setUsername(deliveryBoy.getUsername());
		deliveryBoy.setPassword(passwordEncoder.encode(deliveryBoy.getPassword())); // Hash the password
		deliveryBoy.setRole(Role.DELIVERY_BOY);
		deliveryBoy.setAssignedArea(deliveryBoy.getAssignedArea());
		deliveryBoy.setOnline(false); // Default status is offline
		return deliveryBoyRepository.save(deliveryBoy);
	}

	// Authenticate Delivery Boy (Login)
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(request.getUsername());

		if (deliveryBoy == null || !passwordEncoder.matches(request.getPassword(), deliveryBoy.getPassword())) {
			throw new AuthenticationException("Invalid credentials");
		}

		// Validate if MPIN is set
		if (deliveryBoy.getMpin() == null) {
			throw new AuthenticationException("Please set your MPIN.");
		}

		// Validate MPIN
		if (!passwordEncoder.matches(request.getMpin(), deliveryBoy.getMpin())) {
			throw new AuthenticationException("Invalid MPIN.");
		}

		String accessToken = jwtTokenProvider.generateAccessToken(deliveryBoy);
		String refreshToken = jwtTokenProvider.generateRefreshToken(deliveryBoy);

		return new AuthenticationResponse(accessToken, refreshToken);
	}

	// Refresh Token
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String refreshToken = refreshTokenRequest.getRefreshToken();

		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid refresh token.");
		}

		String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(username);

		if (deliveryBoy == null) {
			throw new RuntimeException("User not found");
		}

		String newAccessToken = jwtTokenProvider.generateAccessToken(deliveryBoy);
		String newRefreshToken = jwtTokenProvider.generateRefreshToken(deliveryBoy);

		return new AuthenticationResponse(newAccessToken, newRefreshToken);
	}

	// Set MPIN for the DeliveryBoy
	public String setMpin(String username, String mpin) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}

		if (mpin == null || mpin.isEmpty()) {
			throw new IllegalArgumentException("MPIN cannot be empty");
		}

		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(username);

		if (deliveryBoy == null) {
			throw new RuntimeException("User not found");
		}

		// Encode and set MPIN
		deliveryBoy.setMpin(passwordEncoder.encode(mpin));

		// Save to database
		try {
			deliveryBoyRepository.save(deliveryBoy);
			return "MPIN set successfully!";
		} catch (Exception e) {
			throw new RuntimeException("Error saving MPIN: " + e.getMessage());
		}
	}

	// Change Online/Offline Status
	public String changeStatus(String username, boolean status) {
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findByUsername(username);

		if (deliveryBoy == null) {
			throw new RuntimeException("User not found");
		}

		deliveryBoy.setOnline(status); // Set online/offline
		deliveryBoyRepository.save(deliveryBoy);
		return "Status changed successfully!";
	}

	// Get all Delivery Boys (Admin only)
	public List<DeliveryBoy> getAllDeliveryBoys() {
		return deliveryBoyRepository.findAll();
	}

	public DeliveryBoy findByUsername(String username) {
		return deliveryBoyRepository.findByUsername(username);
	}

	public DeliveryBoy getDeliveryBoyById(Long id) {
		return deliveryBoyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Delivery Boy not found with ID: " + id));
	}

	

}
