package com.epam.jtc.spring.datalayer;

import com.epam.jtc.spring.datalayer.dto.AvailableTagsEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    
    private HibernateSessionFactoryUtil() {
    }
    
    public static SessionFactory getSessionFactory() {
        
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure(
                    "hibernate/hibernate.cfg.xml");
                configuration.addAnnotatedClass(AvailableTagsEntity.class);
                //configuration.addFile("hibernate/hibernate.cfg.xml");
                StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory =
                    configuration.buildSessionFactory(builder.build());
    
                /*Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
                Transaction tx1 = session.beginTransaction();
    
                AvailableTagsEntity tags = new AvailableTagsEntity();
                tags.setId(33);
                tags.setTag("new tag");
                session.save(tags);
  
                tx1.commit();
                session.close();*/
                
            } catch (Exception e) {
                System.err.println("Исключение! " + e);
            }
        }
        return sessionFactory;
    }
}