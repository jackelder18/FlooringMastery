package com.sg.flooringmastery.models;

import java.math.BigDecimal;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class Tax {
    private final String state;
    private final BigDecimal taxRate;
    
    public Tax(String state, BigDecimal taxRate){
        this.state = state;
        this.taxRate = taxRate;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
