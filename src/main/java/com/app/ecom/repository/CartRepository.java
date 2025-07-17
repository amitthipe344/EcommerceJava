package com.app.ecom.repository;

import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem,Long> {
  List<CartItem> findByUserId(Long userId);

  CartItem findByUserAndProduct(User user, Product product);

  void deleteByUserAndProduct(User user, Product product);

  void deleteByUser(User user);
}
