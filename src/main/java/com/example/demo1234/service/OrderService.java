package com.example.demo1234.service;

import com.example.demo1234.enums.OrderStatus;
import com.example.demo1234.model.*;
import com.example.demo1234.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.function.ServerResponse.status;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void placeOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();

            // Stok kontrolü
            if (book.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }

            // Stok düşürme
            book.setQuantity(book.getQuantity() - cartItem.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = OrderItem.builder()
                    .book(book)
                    .quantity(cartItem.getQuantity())
                    .price(book.getPrice())
                    .build();

            orderItems.add(orderItem);

            totalPrice += book.getPrice() * cartItem.getQuantity();
        }

        Order order = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }

        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems); // Sipariş verildikten sonra sepet temizlenir
    }

    public List<Order> getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUser(user);
    }

    public List<OrderResponse> getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user).stream()
                .filter(order -> order.getStatus() == OrderStatus.APPROVED) // 🔥 sadece onaylı siparişler
                .collect(Collectors.toList());

        return orders.stream().map(order -> {
            List<OrderItemResponse> items = order.getOrderItems().stream().map(orderItem ->
                    new OrderItemResponse(
                            orderItem.getBook().getTitle(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    )).collect(Collectors.toList());

            return new OrderResponse(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getCreatedAt(),
                    items
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public void rejectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }

}
