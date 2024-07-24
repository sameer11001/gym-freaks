package org.webapp.gymfreaks.order.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.webapp.gymfreaks.auth.model.UserEntity;

@Data
public class OrderViewDto {
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
