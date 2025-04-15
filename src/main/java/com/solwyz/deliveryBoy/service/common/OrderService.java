package com.solwyz.deliveryBoy.service.common;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.solwyz.deliveryBoy.controller.auth.Orders;
import com.solwyz.deliveryBoy.models.DeliveryBoy;
import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.repositories.common.DeliveryBoyRepository;
import com.solwyz.deliveryBoy.repositories.common.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DeliveryBoyRepository deliveryBoyRepository;

	// Create Order
	public Order createOrder(Order order) {
		order.setStatus("PENDING");
		return orderRepository.save(order);
	}

	// Accept Order
	public Order acceptOrder(Long orderId, Long deliveryBoyId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getStatus().equals("PENDING")) {
			throw new RuntimeException("Order already processed");
		}

		DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId)
				.orElseThrow(() -> new RuntimeException("Delivery Boy not found"));

		order.setStatus("ACCEPTED");
		order.setDeliveryBoy(deliveryBoy);
		return orderRepository.save(order);
	}

	// Reject Order
	public Order rejectOrder(Long orderId, Long deliveryBoyId, String reason) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if (!order.getStatus().equals("PENDING")) {
			throw new RuntimeException("Order already processed");
		}

		// Fetch the delivery boy
		DeliveryBoy deliveryBoy = deliveryBoyRepository.findById(deliveryBoyId)
				.orElseThrow(() -> new RuntimeException("Delivery Boy not found"));

		// Set rejected status and assign the delivery boy
		order.setStatus("REJECTED");
		order.setDeliveryBoy(deliveryBoy);

		return orderRepository.save(order);
	}

	// Get Orders Assigned to a Delivery Boy
	public List<Order> getOrdersByDeliveryBoy(Long deliveryBoyId, String status) {
		return orderRepository.findByDeliveryBoyIdAndStatus(deliveryBoyId, status);
	}

	// Get All Pending Orders
//	public List<Order> getPendingOrders() {
//		return orderRepository.findByStatus("PENDING");
//	}
	public List<Order> getPendingOrders() {
	    return orderRepository.findByStatusOrderByOrderDateDesc("PENDING");
	}

	// Get orders by date range (day/week/month/year)
	public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {

		return orderRepository.findByOrderDateBetween(startDate, endDate);
	}

//	public List<Order> getRejectedOrdersByDeliveryBoy() {
//
//		return orderRepository.findByStatus("REJECTED");
//	}
	public List<Order> getRejectedOrdersByDeliveryBoy() {
	    return orderRepository.findByStatusOrderByOrderDateDesc("REJECTED");
	}


	public List<Order> getAcceptedOrdersByDeliveryBoy(Long deliveryBoyId) {
		LocalDate today = LocalDate.now(); // Get today's date
		return orderRepository.findByDeliveryBoyIdAndStatusAndOrderDate(deliveryBoyId, "ACCEPTED", today);
	}
	
	// Get All Orders for a Delivery Boy
		public List<Order> getOrdersByDeliveryBoy(Long deliveryBoyId) {
			return orderRepository.findByDeliveryBoyIdOrderByOrderDateDesc(deliveryBoyId);
		}

		
//	public Map<String, List<Order>> getOrdersGroupedByStatus(Long deliveryBoyId) {
//		List<Order> orders = orderRepository.findByDeliveryBoyIdOrderByOrderDateDesc(deliveryBoyId);
//		return orders.stream().collect(Collectors.groupingBy(Order::getStatus));
//	}

	

//	public Order cancelOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//            .orElseThrow(() -> new RuntimeException("Order not found"));
//        
//        order.setStatus("CANCELLED");
//        return orderRepository.save(order);
//    }

//	public List<Order> getCancelledOrdersByDeliveryBoy(Long deliveryBoyId) {
//		return orderRepository.findByDeliveryBoyIdAndStatus(deliveryBoyId, "REJECTED");
//	}
//
//	
}
