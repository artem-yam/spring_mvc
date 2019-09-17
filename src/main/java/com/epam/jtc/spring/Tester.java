package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dto.Book;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
        BookDAO dao =
                DAOFactory.getInstance(DataSourceType.ORACLE)
                        .getBookDAO();

        List<Book> result = new ArrayList<>();

        System.out.println("Получаем книги");

        //int newBookId = dao.addBook("title", "author", null);
        result = dao.getAllBooks();

        System.out.println(result);
    }

}
