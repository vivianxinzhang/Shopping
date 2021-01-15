package onlineShop.dao;

import onlineShop.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteProduct(int productId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            // 搜索Product这张表 通过productId找到一个product
            Product product = session.get(Product.class, productId);
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Product getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            // 搜索Product这张表 通过productId找到一个product 返回该product
            return session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            // Criteria搜索条件：Product.class -> 对Product这张表进行搜索
            products = session.createCriteria(Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
