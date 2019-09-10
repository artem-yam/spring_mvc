package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.dao.BookDAO;
import com.epam.jtc.spring.datalayer.oracle.dao.OracleBookDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Tester {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx;
        // ctx =   new ClassPathXmlApplicationContext("spring.xml");

        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.epam.jtc.spring.datalayer");
        ctx.refresh();


        //DAOFactory daoFactory = DAOFactory.getInstance(DataSourceType.ORACLE);

        BookDAO dao;
        dao = ctx.getBean(OracleBookDAO.class);

        //dao = daoFactory.getBookDAO();

        dao.getAllBooks();

        ctx.close();
    }

}
