package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.DAOFactory;
import com.epam.jtc.spring.datalayer.DataSourceType;
import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.dao.NotificationDAO;
import com.epam.jtc.spring.datalayer.dto.Book;
import com.epam.jtc.spring.datalayer.dto.NotificationTypes;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
        NotificationDAO dao =
                DAOFactory.getInstance(DataSourceType.ORACLE)
                        .getNotificationDAO();

        List<Book> result = new ArrayList<>();

        //int newBookId = dao.addBook("title", "author", null);
        dao.addNotification(0, "qqq","", NotificationTypes.SEARCH);

        System.out.println(result);
    }

}
