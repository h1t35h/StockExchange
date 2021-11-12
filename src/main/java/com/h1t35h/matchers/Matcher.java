package com.h1t35h.matchers;

import com.h1t35h.entities.CompletedOrder;
import com.h1t35h.entities.Order;

import java.util.List;

/**
 * Interface for Matchers. Assuming we may have multiple matchers in future. Which may allow more complex operations.
 * @author hitesy
 */
public interface Matcher {
    /**
     * Adds an order to the stock Matcher.
     * @param order order for Matcher.
     * @return list of Executed order
     */
    List<CompletedOrder> addOrder(Order order);
}
