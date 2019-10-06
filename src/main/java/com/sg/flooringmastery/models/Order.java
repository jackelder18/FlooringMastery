package com.sg.flooringmastery.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class Order {
    private final LocalDate date;
    private int orderId;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxCost;
    private BigDecimal totalCost;
    
    public Order(LocalDate date){
        this.date = date;
    }
    
    public BigDecimal scale(BigDecimal b){
        return b.setScale(2, RoundingMode.HALF_UP);
    }
    
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = scale(taxRate);
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setArea(BigDecimal area) {
        this.area = scale(area);
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = scale(costPerSquareFoot);
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = scale(laborCostPerSquareFoot);
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = scale(materialCost);
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = scale(laborCost);
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = scale(taxCost);
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = scale(totalCost);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
    
}
