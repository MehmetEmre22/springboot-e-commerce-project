package com.example.demo1234.controller;

import com.example.demo1234.dto.DtoBook;
import com.example.demo1234.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/get-all-book")
    public List<DtoBook> getAll() {
        return bookService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/get-all-book")
    public List<DtoBook> adminGetAll() {
        return bookService.adminGetAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save-book")
    public DtoBook save(@RequestBody DtoBook dtoBook){
        return  bookService.save(dtoBook);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update-book/{isbn}")
    public DtoBook updateBookByIsbn(@PathVariable Long isbn, @RequestBody DtoBook dtoBook) {
        return bookService.updateByIsbn(isbn, dtoBook);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/delete-book/{isbn}")
    public void deleteBookByIsbn(@PathVariable Long isbn) {
        bookService.deleteByIsbn(isbn);
    }
}