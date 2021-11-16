package com.h1t35h.store;

import com.h1t35h.entities.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface for allowing storage and retrieval of Orders.
 * @author hitesy
 */
public interface OrderStore {

    /**
     * Returns a particular Order
     * @param transactionId transactionId
     * @param stock stock for the order
     * @return order for the stock.
     */
    Optional<Order> getOrder(String transactionId, String stock);

    /**
     * Stores an order returns true if successful.
     * @param order order
     * @return status of order call
     */
    Boolean storeOrder(Order order);

    /**
     * Allows searching for Orders
     * @param transactionId transaction of order
     * @param stock stock for the order
     * @param orderDate order Dates
     * @return
     */
    List<Order> searchOrders(String transactionId, String stock, LocalDateTime orderDate);
}
