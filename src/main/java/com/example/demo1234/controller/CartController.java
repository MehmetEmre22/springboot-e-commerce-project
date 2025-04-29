package com.example.demo1234.controller;

import com.example.demo1234.dto.CartItemResponse;
import com.example.demo1234.model.CartItem;
import com.example.demo1234.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId, @RequestParam Integer quantity) {
        cartService.addToCart(bookId, quantity);
        return "Book added to cart successfully!";
    }

    @GetMapping("/get")
    public List<CartItemResponse>  getCartItems() {
        return cartService.getCartItems();
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "Item removed from cart successfully!";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared successfully!";
    }
}
