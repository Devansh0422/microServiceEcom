package com.microservices.order.service;

import com.microservices.order.model.CartItem;
// import com.microservices.order.model.Product;  // Product belongs to product-service microservice
// import com.microservices.order.model.User;      // User belongs to user-service microservice
import com.microservices.order.dto.CartItemRequest;
import com.microservices.order.repository.CartItemRepository;
// import com.microservices.order.repository.ProductRepository;  // Not available in this microservice
// import com.microservices.order.repository.UserRepository;     // Not available in this microservice
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    // Hardcoded values for user/product details (replace with actual microservice calls when integrated)
    private static final String HARDCODED_USER_ID = "TEMP-USER-ID";
    private static final String HARDCODED_PRODUCT_ID = "TEMP-PRODUCT-ID";
    private static final BigDecimal HARDCODED_PRODUCT_PRICE = new BigDecimal("99.99");
    private static final int HARDCODED_STOCK_QUANTITY = 100;

    // private final ProductRepository productRepository;  // Product microservice repository — not in this service
    // private final UserRepository userRepository;        // User microservice repository — not in this service
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        // TODO: Replace with actual product-service REST call
        // Optional<Product> productopt = productRepository.findById(request.getProductid());
        // if (productopt.isEmpty()) { return false; }
        String productId = String.valueOf(request.getProductid());

        // TODO: Replace with actual product-service REST call for stock check
        // if (product.getStockQuantity() < request.getQuantity()) { return false; }
        if (HARDCODED_STOCK_QUANTITY < request.getQuantity()) {
            return false;
        }

        // TODO: Replace with actual user-service REST call
        // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        // if (userOpt.isEmpty()) { return false; }

        // TODO: Replace with actual product-service REST call for price
        // BigDecimal productPrice = product.getPrice();
        BigDecimal productPrice = HARDCODED_PRODUCT_PRICE;

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(productPrice.multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(productPrice.multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {

        // TODO: Replace with actual product-service and user-service REST calls
        // Optional<Product> productOpt = productRepository.findById(productId);
        // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        // if (productOpt.isPresent() && userOpt.isPresent()) { ... }

        String productIdStr = String.valueOf(productId);
        cartItemRepository.deleteByUserIdAndProductId(userId, productIdStr);
        return true;
    }

    public List<CartItem> getCart(String userId) {
        // TODO: Replace with actual user-service REST call to validate user
        // return userRepository.findById(Long.valueOf(userId)).map(cartItemRepository::findByUser).orElseGet(List::of);
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        // TODO: Replace with actual user-service REST call to validate user
        // userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
        cartItemRepository.deleteByUserId(userId);
    }
}

