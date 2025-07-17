package com.app.ecom.service;

import com.app.ecom.dto.CartRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public List<CartItem> getCart(String userId) {
    return cartRepository.findByUserId(Long.valueOf(userId));
  }

  public boolean addToCart(String userId, CartRequest cartRequest) {

    Optional<Product> productOpt = productRepository.findById(cartRequest.getProductId());

    if (productOpt.isEmpty()) {
      return false;
    }
    Product product = productOpt.get();
    if (product.getStockQuantity() < cartRequest.getQuantity()) {
      return false;
    }
    Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
    if (userOpt.isEmpty()) {
      return false;
    }
    User user = userOpt.get();
    CartItem existingCartItem = cartRepository.findByUserAndProduct(user, product);
    if (existingCartItem != null) {
      existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
      existingCartItem.setPrice(
          existingCartItem.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
      cartRepository.save(existingCartItem);
    } else {
      CartItem cartItem = new CartItem();
      cartItem.setUser(user);
      cartItem.setProduct(product);
      cartItem.setQuantity(cartRequest.getQuantity());
      cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartRequest.getQuantity())));
      cartRepository.save(cartItem);
    }
    return true;
  }

  public boolean deleteFromCart(String userId, Long productId) {
    Optional<Product> productOpt = productRepository.findById(productId);
    Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

    if(productOpt.isPresent() && userOpt.isPresent()){
      cartRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
      return true;
    }
    return false;
  }

  public void clearCart(String userId) {
    userRepository.findById(Long.valueOf(userId)).ifPresent(cartRepository::deleteByUser);
  }
}
