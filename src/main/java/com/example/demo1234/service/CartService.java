package com.example.demo1234.service;

import com.example.demo1234.dto.CartItemResponse;
import com.example.demo1234.model.Book;
import com.example.demo1234.model.CartItem;
import com.example.demo1234.model.User;
import com.example.demo1234.repository.BookRepository;
import com.example.demo1234.repository.CartItemRepository;
import com.example.demo1234.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addToCart(Long bookId, Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 🔥 Önce bu kullanıcı ve bu kitap için daha önce sepette ürün var mı kontrol ediyoruz
        List<CartItem> existingCartItems = cartItemRepository.findByUser(user)
                .stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .toList();

        if (!existingCartItems.isEmpty()) {
            // Varsa ➔ quantity artır
            CartItem cartItem = existingCartItems.get(0);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Yoksa ➔ yeni kayıt oluştur
            CartItem cartItem = CartItem.builder()
                    .user(user)
                    .book(book)
                    .quantity(quantity)
                    .build();
            cartItemRepository.save(cartItem);
        }
    }


    public List<CartItemResponse> getCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        // 🔥 CartItem nesnelerini CartItemResponse DTO'suna çeviriyoruz
        return cartItems.stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getBook().getTitle(),
                        cartItem.getBook().getPrice(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
}
