package com.deliveryBoy.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>{

	Optional<RefreshToken> findByToken(String token);

	@Modifying
	int deleteByUser(User user);
	
}
