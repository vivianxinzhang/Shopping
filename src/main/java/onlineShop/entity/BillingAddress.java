package onlineShop.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "billingAddress")
public class BillingAddress implements Serializable {

    private static final long serialVersionUID = 1028098616457762743L;

    @Id
    // indicating the member field below is the primary key of current entity
    @GeneratedValue(strategy = GenerationType.AUTO)
    // 每增加一行，值自动加 1，不需要自己手动设置
    private int id;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    // 通过 billingAddress 找到 customer
    // customer 中有一个field 是 billingAddress
    @OneToOne(mappedBy = "billingAddress")
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}