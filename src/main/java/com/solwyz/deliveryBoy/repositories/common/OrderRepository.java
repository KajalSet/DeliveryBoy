package com.solwyz.deliveryBoy.repositories.common;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solwyz.deliveryBoy.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByDeliveryBoyIdAndStatus(Long deliveryBoyId, String status);

	List<Order> findByStatus(String status);

	List<Order> findByOrderDateBetween(Date startDate, Date endDate);

	List<Order> findByDeliveryBoyId(Long deliveryBoyId);

	List<Order> findByDeliveryBoyIdOrderByOrderDateDesc(Long deliveryBoyId);

	List<Order> findByDeliveryBoyIdAndStatusAndOrderDate(Long deliveryBoyId, String string, LocalDate today);

	List<Order> findByDeliveryBoyIdAndStatusInOrderByOrderDateDesc(Long deliveryBoyId, List<String> statuses);

	List<Order> findByStatusOrderByOrderDateDesc(String status);


	//List<Order> findByDeliveryBoyIdOrderByOrderDateDesc(Long deliveryBoyId);

//    @Query("SELECT o FROM Order o WHERE o.deliveryBoy.id = :deliveryBoyId AND o.status = :status AND DATE(o.createdAt) = :today")
//    List<Order> findByDeliveryBoyIdAndStatusAndDate(@Param("deliveryBoyId") Long deliveryBoyId, 
//                                                    @Param("status") String status, 
//                                                    @Param("today") LocalDate today);
}
	
	


