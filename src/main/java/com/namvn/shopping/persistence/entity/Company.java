package com.namvn.shopping.persistence.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "company_id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyId;

    private String name;
    private String address;
    private String tel;
    private String fax;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "companies_products",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
    private Collection<Product> products;

    public Company() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
