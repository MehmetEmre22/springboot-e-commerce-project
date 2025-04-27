package com.example.demo1234.controller;

import com.example.demo1234.dto.DtoBook;
import com.example.demo1234.dto.DtoBookIU;
import com.example.demo1234.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/get-all-product")
    public List<DtoBook> getAll() {
        return bookService.getAll();
    }
    @PostMapping("/save-product")
    public DtoBook save(@RequestBody DtoBookIU dtoBookIU){
        return  bookService.save(dtoBookIU);
    }
}