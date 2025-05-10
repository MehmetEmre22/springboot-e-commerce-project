package com.example.demo1234.service;

import com.example.demo1234.dto.OrderItemResponse;
import com.example.demo1234.dto.OrderResponse;
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

        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();//Sepetteki miktar stoktan azsa sipariş verilmez.
            if (book.getQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();//Sepetteki kitapları topla
            book.setQuantity(book.getQuantity() - cartItem.getQuantity());//Sipariş verilen miktarda stoktan düş.
            bookRepository.save(book);//DB'ye kaydedet.

            OrderItem orderItem = OrderItem.builder()//Tüm kitaplarla order item oluştur.
                    .book(book)
                    .quantity(cartItem.getQuantity())
                    .price(book.getPrice())
                    .build();

            orderItems.add(orderItem);//Oluşturulan OrderItemleri bir listede topla

            totalPrice += book.getPrice() * cartItem.getQuantity();
        }

        Order order = Order.builder()//Tüm bu işlemleri bir orderda topla.
                .user(user)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        for (OrderItem orderItem : orderItems) {//Orderitemleri order'a bağla.
            orderItem.setOrder(order);
        }

        orderRepository.save(order);//order ı kaydet.
        cartItemRepository.deleteAll(cartItems);//Cartitemlere gerek kalmadığı için sil.
    }

    public List<OrderResponse> getUserOrders() {//Kullanıcının orderları
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return mapOrdersToOrderResponses(orders);
    }

    public List<OrderResponse> getAllOrders() {//Admin için tüm orderlar
        List<Order> orders = orderRepository.findAll();
        return mapOrdersToOrderResponses(orders);
    }

    public List<OrderResponse> getApprovedOrders() {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.APPROVED)
                .collect(Collectors.toList());
        return mapOrdersToOrderResponses(orders);
    }

    public List<OrderResponse> getRejectedOrders() {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.REJECTED)
                .collect(Collectors.toList());
        return mapOrdersToOrderResponses(orders);
    }
    public List<OrderResponse> getPendingOrders() {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .collect(Collectors.toList());
        return mapOrdersToOrderResponses(orders);
    }

    @Transactional
    public void approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
    }

    @Transactional
    public void rejectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }

    private List<OrderResponse> mapOrdersToOrderResponses(List<Order> orders) {
        return orders.stream().map(order -> {//Orderları orderResponse çevirme
            List<OrderItemResponse> items = order
                    .getOrderItems()
                    .stream()
                    .map(orderItem ->
                    new OrderItemResponse(
                            orderItem.getBook().getTitle(),
                            orderItem.getQuantity(),
                            orderItem.getPrice()
                    )).toList();

            return new OrderResponse(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getCreatedAt(),
                    order.getStatus().name(),
                    order.getUser().getUsername(),
                    order.getUser().getEmail(),
                    items
            );
        }).toList();
    }

}
