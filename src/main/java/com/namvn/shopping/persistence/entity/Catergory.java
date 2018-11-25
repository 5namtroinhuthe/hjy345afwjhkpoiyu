package com.namvn.shopping.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "catergory")
public class Catergory {
    @Id
    @Column(name = "catergory_id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catergoryId;
    @Column(unique = true, nullable = false)
    private String name;
    private String image;
    private int status;
    @OneToMany(mappedBy = "catergory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Product> products;

    public Catergory() {
    }

    public Long getCatergoryId() {
        return catergoryId;
    }

    public void setCatergoryId(Long catergoryId) {
        this.catergoryId = catergoryId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
