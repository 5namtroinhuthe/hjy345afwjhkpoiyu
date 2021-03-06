package com.namvn.shopping.persistence.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String productId;
    @NotNull
    private String productName;
    @NotNull
    private String detail;
    private String color;
    private String size;

    private String manufacturer;
    private String material;
    private String madeIn;
    private String province;
    @NotNull
    private String image;
    @NotNull
    private float priceInput;
    @NotNull
    private float prices;
    private float priceNew;
    @NotNull
    private int quantity;
    @NotNull
    private Date date;
    private int status;
    @Transient
    private MultipartFile productImage;
    @ManyToOne
    @JoinColumn(name = "catergory_id")
    private Catergory catergory;
    @ManyToMany(mappedBy = "products")
    private Collection<Company> companies;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    public Product() {
    }

    public Product(String productId, String name, float prices, float priceNew) {
        this.productId = productId;
        this.productName = name;
        this.prices = prices;
        this.priceNew = priceNew;
    }

    public float getPriceInput() {
        return priceInput;
    }

    public void setPriceInput(float priceInput) {
        this.priceInput = priceInput;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Collection<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Collection<Company> companies) {
        this.companies = companies;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Catergory getCatergory() {
        return catergory;
    }

    public void setCatergory(Catergory catergory) {
        this.catergory = catergory;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getMadeIn() {
        return madeIn;
    }

    public void setMadeIn(String madeIn) {
        this.madeIn = madeIn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public float getPrices() {
        return prices;
    }

    public void setPrices(float prices) {
        this.prices = prices;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(float priceNew) {
        this.priceNew = priceNew;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }
}
