package com.uow.sose.cuisine.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer_order")
public class Order {

    @Id
    @GeneratedValue
    private int order_id;

    private double total_amount;
    private String status;
    private String promo_code;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
