package org.webapp.gymfreaks.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.core.model.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_amount")
    private Float orderAmount;

    @Column(name = "order_shipped")
    private boolean orderShipped;

    @Column(name = "order_address")
    private String orderAddress;

    @ManyToOne
    @JoinColumn(name = "order_user_id", referencedColumnName = "user_id")
    private UserEntity orderUserId;

}
