package com.inventorys;

import com.inventory.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class App {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        Session session = factory.openSession();

        try {
            session.beginTransaction();

            // Insert data if table is empty
            Long count = session.createQuery(
                    "select count(p) from Product p", Long.class
            ).getSingleResult();

            if (count == 0) {
                session.persist(new Product("Dell XPS", "Laptop", 1500, 5));
                session.persist(new Product("MacBook Air", "Laptop", 1200, 8));
                session.persist(new Product("HP Pavilion", "Laptop", 900, 3));
                session.persist(new Product("Logitech Mouse", "Accessory", 50, 20));
                session.persist(new Product("Keyboard", "Accessory", 70, 15));
                session.persist(new Product("Monitor", "Electronics", 200, 10));
            }

            print("Price Ascending",
                    session.createQuery("from Product p order by p.price asc", Product.class).list());

            print("Price Descending",
                    session.createQuery("from Product p order by p.price desc", Product.class).list());

            print("Quantity High First",
                    session.createQuery("from Product p order by p.quantity desc", Product.class).list());

            Query<Product> q = session.createQuery("from Product", Product.class);
            q.setFirstResult(0);
            q.setMaxResults(3);
            print("First 3 Products", q.list());

            q.setFirstResult(3);
            print("Next 3 Products", q.list());

            System.out.println("\nTotal Products: " +
                    session.createQuery("select count(p) from Product p", Long.class).getSingleResult());

            System.out.println("Products with Quantity > 0: " +
                    session.createQuery(
                            "select count(p) from Product p where p.quantity > 0",
                            Long.class).getSingleResult());

            Object[] minMax = session.createQuery(
                    "select min(p.price), max(p.price) from Product p", Object[].class
            ).getSingleResult();

            System.out.println("Min Price: " + minMax[0]);
            System.out.println("Max Price: " + minMax[1]);

            List<Object[]> group = session.createQuery(
                    "select p.description, count(p) from Product p group by p.description",
                    Object[].class).list();

            System.out.println("\nGrouped by Description:");
            for (Object[] row : group) {
                System.out.println(row[0] + " -> " + row[1]);
            }

            print("Price Between 100 and 1000",
                    session.createQuery(
                            "from Product p where p.price between 100 and 1000",
                            Product.class).list());

            print("Names Starting with M",
                    session.createQuery(
                            "from Product p where p.name like 'M%'",
                            Product.class).list());

            print("Names Ending with r",
                    session.createQuery(
                            "from Product p where p.name like '%r'",
                            Product.class).list());

            print("Names Containing 'top'",
                    session.createQuery(
                            "from Product p where p.name like '%top%'",
                            Product.class).list());

            print("Name Length = 7",
                    session.createQuery(
                            "from Product p where length(p.name) = 7",
                            Product.class).list());

            session.getTransaction().commit();

        } finally {
            session.close();
            factory.close();
        }
    }

    private static void print(String title, List<Product> list) {
        System.out.println("\n--- " + title + " ---");
        for (Product p : list) {
            System.out.println(p);
        }
    }
}