package com.exe.inventorymsystemserver.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "transaction_type")
    private String transactionType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "part_id")
    private Parts part;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total")
    private double total;

    @Column(name = "price")
    private double price;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Transaction(Long transactionId, String transactionType, Parts part, String name, int quantity, double total, double price, String createdBy, LocalDateTime createdDate) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.part = part;
        this.name = name;
        this.quantity = quantity;
        this.total = total;
        this.price = price;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    public Transaction() {

    }
}
