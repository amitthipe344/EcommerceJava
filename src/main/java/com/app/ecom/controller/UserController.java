package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUser() {
    return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

    return userService.fetchUser(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
    userService.addUser(userRequest);
    return ResponseEntity.ok("User added successfully");
  }

  @PutMapping("{id}")
  public ResponseEntity<String> updateUser(@PathVariable Long id,
      @RequestBody UserRequest userRequest) {
    boolean updated = userService.updateUser(id, userRequest);
    if (updated) {
      return ResponseEntity.ok("User Updated Successfully");
    }
    return ResponseEntity.notFound().build();
  }
}
