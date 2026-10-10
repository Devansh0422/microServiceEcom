package com.microservices.order.repository;

import com.microservices.order.model.CartItem;
// import com.microservices.order.model.Product;  // Product belongs to product-service microservice
// import com.microservices.order.model.User;      // User belongs to user-service microservice
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Microservice-compatible methods (uses plain String IDs instead of entity references)
    CartItem findByUserIdAndProductId(String userId, String productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);

    void deleteByUserIdAndProductId(String userId, String productId);

    // TODO: Restore these when User/Product entities are available (e.g. via shared-lib or same service)
    // CartItem findByUserAndProduct(User user, Product product);
    // void deleteByUserAndProduct(User user, Product product);
    // List<CartItem> findByUser(User user);
    // void deleteByUser(User user);
}
