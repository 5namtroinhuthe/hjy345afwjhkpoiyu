package com.namvn.shopping.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    @Id
    @Column(name = "bill_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long billId;
    private String nameBill;
    private String nameUser;
    private String address;
    private String email;
    private String tel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "bill",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserOrder userOrder;
    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getNameBill() {
        return nameBill;
    }

    public void setNameBill(String nameBill) {
        this.nameBill = nameBill;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
