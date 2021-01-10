package onlineShop.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Authorities table has two cols emailId and authorities
@Entity
// @Entity: Used with model class to specify that it is an entity and mapped to a table in the database.
@Table(name = "authorities")
// @Table: Used with entity class to define the corresponding table name in the database.
// name = "authorities" 如果没有这个name的属性, hibernate将假定这是一个非POJO的实体映射
public class Authorities implements Serializable {
    // SerialVersionUID is used to ensure that during deserialization
    // the same class (that was used during serialize process) is loaded.
    private static final long serialVersionUID = 8734140534986494039L;

    @Id
    // indicating the member field below is the primary key of current entity
    private String emailId;

    private String authorities;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}