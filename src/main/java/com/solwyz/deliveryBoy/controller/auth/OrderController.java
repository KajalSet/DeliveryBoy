package com.solwyz.deliveryBoy.controller.auth;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solwyz.deliveryBoy.models.Order;
import com.solwyz.deliveryBoy.pojo.response.ApiResponse;
import com.solwyz.deliveryBoy.service.common.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// Create Order (Admin or Customer)
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
		Order createdOrder = orderService.createOrder(order);
		ApiResponse<Order> response = new ApiResponse<>("success", createdOrder);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/pending")
	public ResponseEntity<ApiResponse<List<Order>>> getPendingOrders() {
		List<Order> pendingOrders = orderService.getPendingOrders();
		ApiResponse<List<Order>> response = new ApiResponse<>("success", pendingOrders);
		return ResponseEntity.ok(response);
	}


	// Accept Order (Delivery Boy)
	@PostMapping("/{orderId}/accept/{deliveryBoyId}")
	public ResponseEntity<ApiResponse<Order>> acceptOrder(@PathVariable Long orderId,
			@PathVariable Long deliveryBoyId) {
		Order acceptedOrder = orderService.acceptOrder(orderId, deliveryBoyId);
		ApiResponse<Order> response = new ApiResponse<>("success", acceptedOrder);
		return ResponseEntity.ok(response);
	}
	
	//list of accepted orders
	@GetMapping("/todaysOrder/{deliveryBoyId}")
    public ResponseEntity<ApiResponse<List<Order>>> getAcceptedOrdersByDeliveryBoy(@PathVariable Long deliveryBoyId) {
        List<Order> acceptedOrders = orderService.getAcceptedOrdersByDeliveryBoy(deliveryBoyId);
        ApiResponse<List<Order>> response = new ApiResponse<>("success", acceptedOrders);
        return ResponseEntity.ok(response);
    }
		
	

	// Reject Order (Delivery Boy)
	@PostMapping("/reject/{deliveryBoyId}/{orderId}")
	public ResponseEntity<ApiResponse<Order>> rejectOrder(
	    @PathVariable Long orderId, 
	    @PathVariable Long deliveryBoyId, 
	    @RequestBody String reason) {

	    Order rejectedOrder = orderService.rejectOrder(orderId, deliveryBoyId, reason);
	    ApiResponse<Order> response = new ApiResponse<>("success", rejectedOrder);
	    return ResponseEntity.ok(response);
	}

	
	   //get all orders rejected by delivery boy
		@GetMapping("/rejected")
		public ResponseEntity<ApiResponse<List<Order>>> getRejectedOrders() {
			List<Order> rejectedOrders=orderService.getRejectedOrdersByDeliveryBoy();
			ApiResponse<List<Order>>response=new ApiResponse<>("success",rejectedOrders);
			return ResponseEntity.ok(response);
		}
	
	// Get Orders Assigned to Delivery Boy
	@GetMapping("/assigned/{deliveryBoyId}")
	public ResponseEntity<ApiResponse<List<Order>>> getAssignedOrders(@PathVariable Long deliveryBoyId,
			@RequestParam String status) {
		List<Order> orders = orderService.getOrdersByDeliveryBoy(deliveryBoyId, status);
		ApiResponse<List<Order>> response = new ApiResponse<>("success", orders);
		return ResponseEntity.ok(response);
	}

	// Get Pending Orders (Delivery Boys can see this)
	

	
	// Get All Orders by Delivery Boy ID
	@GetMapping("/all/{deliveryBoyId}")
	public ResponseEntity<ApiResponse<Map<String, List<Order>>>> getAllOrdersByDeliveryBoy(
	        @PathVariable Long deliveryBoyId) {
	    
	    Map<String, List<Order>> groupedOrders = orderService.getOrdersGroupedByStatus(deliveryBoyId);
	    
	    ApiResponse<Map<String, List<Order>>> response = new ApiResponse<>("success", groupedOrders);
	    return ResponseEntity.ok(response);
	}



		// Get orders by date range (filter by day, week, month, year)
		@GetMapping("/filter")
		public ResponseEntity<ApiResponse<List<Order>>> getOrdersByDateRange(@RequestParam String startDate,
				@RequestParam String endDate) {
			// Parse the startDate and endDate to Date objects, implement parsing logic as
			// needed
			Date start = new Date(startDate); // Use proper date parsing
			Date end = new Date(endDate); // Use proper date parsing
			List<Order> orders = orderService.getOrdersByDateRange(start, end);
			ApiResponse<List<Order>> response = new ApiResponse<>("success", orders);
			return ResponseEntity.ok(response);
		}

	// Get all cancelled orders for a delivery boy
//	@GetMapping("/{deliveryBoyId}/rejected")
//	public ResponseEntity<ApiResponse<List<Order>>> getCancelledOrders(@PathVariable Long deliveryBoyId) {
//		List<Order> cancelledOrders = orderService.getCancelledOrdersByDeliveryBoy(deliveryBoyId);
//		ApiResponse<List<Order>> response = new ApiResponse<>("success", cancelledOrders);
//		return ResponseEntity.ok(response);
//	}
	

	// Cancel Order (Delivery Boy)
//	@PostMapping("/{orderId}/cancel")
//	public ResponseEntity<ApiResponse<Order>> cancelOrder(@PathVariable Long orderId) {
//		Order cancelledOrder = orderService.cancelOrder(orderId);
//		ApiResponse<Order> response = new ApiResponse<>("success", cancelledOrder);
//		return ResponseEntity.ok(response);
//	}
	

	
}