package com.solwyz.deliveryBoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = { @Server(url = "/solwyz/", description = "Default Server URL") })
@SpringBootApplication
public class DeliveryBoyApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryBoyApplication.class, args);
	}

	@Bean(name = "neocorpBCryptPasswordEncoder")
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return new ProviderManager(List.of(authProvider));
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin123"))
//				.roles("ADMIN").build();
//		UserDetails deliveryBoy = User.builder().username("deliveryboy")
//				.password(passwordEncoder().encode("delivery123")).roles("DELIVERY_BOY").build();
//		return new InMemoryUserDetailsManager(admin, deliveryBoy);
//	}
}
