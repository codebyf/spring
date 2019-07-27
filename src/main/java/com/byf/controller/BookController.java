package com.byf.controller;

import com.byf.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    private void print(){
        System.out.println(bookService);
    }
}
