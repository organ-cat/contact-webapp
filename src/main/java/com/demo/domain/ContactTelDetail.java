package com.demo.domain;

import javax.persistence.*;
import java.io.Serializable;

// 一对多示例
@Entity
@Table(name = "contact_tel_detail")
public class ContactTelDetail implements Serializable {
    private Long id;
    private int version;
    private String telType;
    private String telNumber;
    private Contact contact;

    public ContactTelDetail() {
    }

    public ContactTelDetail(String telType, String telNumber) {
        this.telType = telType;
        this.telNumber = telNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "TEL_TYPE")
    public String getTelType() {
        return this.telType;
    }
    public void setTelType(String telType) {
        this.telType = telType;
    }

    @Column(name = "TEL_NUMBER")
    public String getTelNumber() {
        return this.telNumber;
    }
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @ManyToOne // 多对一,关联"一方"
    @JoinColumn(name = "CONTACT_ID") // 指定外键列名
    public Contact getContact() {
        return this.contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String toString() {
        return "Contact Tel Detail - Id: " + id + ", Contact id: "
                + getContact().getId() + ", Type: "
                + telType + ", Number: " + telNumber;
    }
}
