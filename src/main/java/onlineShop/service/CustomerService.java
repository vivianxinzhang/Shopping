package onlineShop.service;

import onlineShop.dao.CustomerDao;
import onlineShop.entity.Cart;
import onlineShop.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// 用在 "Business Service Facade" 上的 @Component
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;

    public void addCustomer(Customer customer) {
        // 激活用户注册
        customer.getUser().setEnabled(true);
        // setEnabled(false)  注销用户啊 -> 用户无法再登陆 但没有真的删除用户数据

        Cart cart = new Cart();
        customer.setCart(cart);

        customerDao.addCustomer(customer);
    }

    public Customer getCustoemrByUserName(String userName) {
        return customerDao.getCustomerByUserName(userName);
    }
}
