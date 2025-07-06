package com.app.ecom.model;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
public class Order {

  private  Long id;
  private User user;
  private BigDecimal totalAmount;
  private OrderStatus orderStatus = OrderStatus.PENDING;
  private List<OrderItem> items = new ArrayList<>();
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
