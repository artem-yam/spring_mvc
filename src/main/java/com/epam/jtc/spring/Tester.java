package com.epam.jtc.spring;

import com.epam.jtc.spring.datalayer.BookDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tester {
    
    public static void main(String[] args) {
        ApplicationContext ctx =
            new ClassPathXmlApplicationContext("spring-context.xml");
        
      /*  SessionFactory sessionFactory =
            HibernateSessionFactoryUtil.getSessionFactory();*/
        
        //DAOFactory daoFactory = DAOFactory.getInstance(DataSourceType.ORACLE);
        BookDAO dao = (BookDAO) ctx.getBean("dao");
        dao.getAllEmployees();
    }
    
}
