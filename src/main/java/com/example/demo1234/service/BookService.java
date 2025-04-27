package com.example.demo1234.service;

import com.example.demo1234.dto.DtoBook;
import com.example.demo1234.dto.DtoBookIU;
import com.example.demo1234.model.Book;

import com.example.demo1234.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public DtoBook save(DtoBookIU dtoBookIU) {
        DtoBook response = new DtoBook();
        Book product = new Book();
        BeanUtils.copyProperties(dtoBookIU,product);

        Book dbProduct = bookRepository.save(product);
        BeanUtils.copyProperties(dbProduct,response);

        return response;
    }
    public List<DtoBook> getAll(){
        return bookRepository.findAll()
                .stream()
                .map(product ->{
                    DtoBook dto =new DtoBook();
                 BeanUtils.copyProperties(product,dto);
                 return dto;
                } ).toList();
    }
}
