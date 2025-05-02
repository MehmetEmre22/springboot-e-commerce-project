package com.example.demo1234.service;

import com.example.demo1234.dto.DtoBook;

import com.example.demo1234.model.Book;

import com.example.demo1234.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public DtoBook save(DtoBook dtoBook) {
        DtoBook response = new DtoBook();
        Book product = new Book();
        BeanUtils.copyProperties(dtoBook,product);//dtodaki aynı isimli alanları product'a kopyalar
        Book dbProduct = bookRepository.save(product);//id otomatik set edildiği için dbBook'a save'i yani id ile geri dönüyoruz
        BeanUtils.copyProperties(dbProduct,response);

        return response;
    }
    public List<DtoBook> getAll(){
        return bookRepository.findAll()
                .stream()
                .map(book ->{
                    DtoBook dto =new DtoBook();
                    BeanUtils.copyProperties(book,dto);
                    return dto;
                } ).toList();
    }
    @Transactional
    public DtoBook updateByIsbn(Long isbn, DtoBook dtoBook) {
        Book existingBook = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (dtoBook.getTitle() != null) existingBook.setTitle(dtoBook.getTitle());
        if (dtoBook.getPrice() != null) existingBook.setPrice(dtoBook.getPrice());
        if (dtoBook.getAuthor() != null) existingBook.setAuthor(dtoBook.getAuthor());
        if (dtoBook.getCategory() != null) existingBook.setCategory(dtoBook.getCategory());
        if (dtoBook.getDescription() != null) existingBook.setDescription(dtoBook.getDescription());
        if (dtoBook.getImage() != null) existingBook.setImage(dtoBook.getImage());
        if (dtoBook.getPublisher() != null) existingBook.setPublisher(dtoBook.getPublisher());
        if (dtoBook.getQuantity() != null) existingBook.setQuantity(dtoBook.getQuantity());
        if (dtoBook.getIsbn() != null) existingBook.setIsbn(dtoBook.getIsbn());

        Book updated = bookRepository.save(existingBook);

        DtoBook response = new DtoBook();
        BeanUtils.copyProperties(updated, response);
        return response;
    }
    @Transactional
    public void deleteByIsbn(Long isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));

        bookRepository.delete(book); // veya: bookRepository.deleteByIsbn(isbn);
    }

}
