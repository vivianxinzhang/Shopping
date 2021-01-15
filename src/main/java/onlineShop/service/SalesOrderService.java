package onlineShop.service;

import onlineShop.dao.SalesOrderDao;
import onlineShop.entity.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderService {
    @Autowired
    private SalesOrderDao salesOrderDao;

    public void addSalesOrder(SalesOrder salesOrder) {
        salesOrderDao.addSalesOrder(salesOrder);
    }
}
