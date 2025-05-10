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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void addToCart(Long isbn, Integer quantity) {
        User user = getCurrentUser();

        Book book = bookRepository.findByIsbn(isbn)//Eklenecek kitabın varlığının kontrolü.
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Optional<CartItem> optionalItem = cartItemRepository.findByUserAndBook(user, book);
        //Aynı kitap,aynı user'ın sepetindeyse repodan alınır.
        CartItem cartItem;

        if (optionalItem.isPresent()) {//optionelItem'ın içinde aynı kitap varsa true döner.
            cartItem = optionalItem.get();//Yani aynı kitap varsa miktar arttırılır.
            cartItem.setQuantity(cartItem.getQuantity() + quantity); //
        } else {
            cartItem = CartItem.builder()
                    .user(user)
                    .book(book)
                    .quantity(quantity)
                    .build();
        }

        cartItemRepository.save(cartItem); // Sepeti güncelle.
    }


    public List<CartItemResponse> getCartItems() {
        User user = getCurrentUser();

        return cartItemRepository.findByUser(user).stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getId(),
                        cartItem.getBook(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());//toList()'te olabilir.
    }

    @Transactional
    public void removeFromCart(Long isbn) {
        User user = getCurrentUser();

        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem cartItem = cartItemRepository
                .findByUser(user)
                .stream()
                .filter(item -> item.getBook().getId().equals(book.getId()))
                .findFirst()
                .orElse(null);

        assert cartItem != null;
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        } else {
            cartItemRepository.delete(cartItem);
        }
    }

    @Transactional
    public void clearCart() {
        User user = getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
}