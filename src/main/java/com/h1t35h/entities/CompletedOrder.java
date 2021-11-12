package com.h1t35h.entities;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Represents completed orders.
 * @author hitesy
 */
@Value
@Builder
public class CompletedOrder {
    String buyOrderId;
    BigDecimal sellPrice;
    BigInteger quantity;
    String sellOrderId;

    @Override
    public String toString() {
        return  buyOrderId + " " + sellPrice + " " + quantity + " " + sellOrderId;
    }
}
