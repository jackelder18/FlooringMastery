package com.sg.flooringmastery.models;

import java.math.BigDecimal;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class Product {
    private final String productType;
    private final BigDecimal costPerSquareFoot;
    private final BigDecimal laborCostPerSquareFoot;
    
    public Product(String type, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot){
        this.productType = type;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }
    
}
