package com.microservices.order.service;

import com.microservices.order.model.*;
import com.microservices.order.dto.OrderItemDTO;
import com.microservices.order.dto.OrderResponse;
import com.microservices.order.repository.OrderRepository;
// import com.microservices.order.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final  CartService cartService;
    private final OrderRepository orderRepository;
//    // private final UserRepository userRepository;

    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

//      Optional<User>  userOptional = userRepository.findById(Long.valueOf(userId));
//        if (userOptional.isEmpty()) {
//            return Optional.empty();
//        }

//        String userId = userOptional.get();

        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
//        order.setUserId(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream().map(i -> new OrderItem(
                null,
                i.getProductId(),
                i.getQuantity(),
                i.getPrice(),
                order
        )).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {

        return  new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream().map(i -> new OrderItemDTO(
                        i.getId(),
//                        i.getProductId().getId(),
                        i.getQuantity(),
                        i.getPrice(),
                        i.getPrice().multiply(new BigDecimal(i.getQuantity()))
                )).toList(), order.getCreatedAt()
        );
    }

    public Optional<OrderResponse> getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::mapToOrderResponse);
    }
}
