package org.webapp.gymfreaks.order.repository;

import org.springframework.stereotype.Repository;
import org.webapp.gymfreaks.core.repository.BaseRepository;
import org.webapp.gymfreaks.order.model.Order;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

}
