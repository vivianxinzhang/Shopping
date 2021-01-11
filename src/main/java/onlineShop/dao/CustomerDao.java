package onlineShop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Authorities;
import onlineShop.entity.Customer;
import onlineShop.entity.User;

// Data access object(DAO): 与数据库相关的代码逻辑
@Repository
// @Repository本质上也是@component  但是@Repository用在跟数据库相关的object上
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;
    // hibernate提供的接口  用来连接数据库 我们在ApplicationConfig中定义了SessionFactory

    // customer由 controller传过来
    // 这个method被controller调用
    public void addCustomer(Customer customer) {
        // 多进行并发情况下 Authorities如果交给Spring来管理 应该是prototype
        // Authorities不涉及业务逻辑 这里用完就可以GC  不需要交给Spring来管理
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;

        try {
            session = sessionFactory.openSession();
            // 把要做的事情 放在一起执行  如果出现问题 就会回滚 防止dirty data/ data inconsistent
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            // 在transaction中同时去update authorities和customer
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出错的话 返回操作之前的状态 不要更新
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // userName < --- > email
    // sessionFactory是Spring提供的 然后通过Spring创建
    public Customer getCustomerByUserName(String userName) {
        User user = null;
        // GET操作不需要rollback  所以Session可以定义在block里面
        // try-with-resource这种写法 里面的session会自动 close
        try (Session session = sessionFactory.openSession()) {
            // Criteria搜索条件：User.class -> 对User这张表进行搜索
            Criteria criteria = session.createCriteria(User.class);
            // Restrictions对应SQL语句
            user = (User) criteria.add(Restrictions.eq("emailId", userName)).uniqueResult();
            // => select * from user where emailId = 'username';
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null)
            return user.getCustomer();
        return null;
    }
}