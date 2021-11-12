package com.h1t35h.entities;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data entity for order details.
 *
 * @author hitesy
 */
@Value
@Builder
public class Order {
    String orderId;
    LocalDateTime orderTime;
    String stock;
    OrderType orderType;
    // Not accounting for currency changes
    @NonNull
    BigDecimal price;
    @With
    BigInteger quantity;

    /**
     * Returns an Order entity based on a passed down orderString input.
     * <b>Note:</b>
     * Date is based on 1st january 2021 time coming in from the input.
     *
     * @param orderString input order string of the format <br> <b>order-id time stock (buy/sell) price qty<b/>
     * @return internal Order entity
     */
    public static Order getOrderFromString(String orderString) {
        String[] orderParams = orderString.split("\\s+");
        List<Integer> hoursMinutes =
                Arrays.stream(orderParams[1].split(":")).map(Integer::valueOf).collect(Collectors.toList());
        LocalDateTime orderTime = LocalDateTime.of(2021, 1, 1, hoursMinutes.get(0), hoursMinutes.get(1));
        return Order.builder()
                .orderId(orderParams[0])
                .orderTime(orderTime)
                .stock(orderParams[2])
                .orderType(OrderType.valueOf(orderParams[3]))
                .price(new BigDecimal(orderParams[4]))
                .quantity(new BigInteger(orderParams[5]))
                .build();
    }
}
