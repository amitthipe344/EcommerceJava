package com.app.ecom.controller;

import com.app.ecom.dto.CartRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @GetMapping
  public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId) {
    return ResponseEntity.ok(cartService.getCart(userId));
  }

  @PostMapping
  public ResponseEntity<String> addToCart(
      @RequestHeader("X-User-ID") String userId,
      @RequestBody CartRequest request) {
    if (!cartService.addToCart(userId, request)) {
      return ResponseEntity.badRequest().body("Not able to complete the request");
    }
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping("items/{productId}")
  public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,
      @PathVariable Long productId) {
    boolean deleted = cartService.deleteFromCart(userId, productId);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
