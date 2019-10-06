package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDaoManager;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.models.Order;
import com.sg.flooringmastery.models.Product;
import com.sg.flooringmastery.models.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class OrderLibrary {
    private final OrderDaoManager orderDaoManager;
    private final ProductDao productDao;
    private final TaxDao taxDao;
    
    public OrderLibrary(OrderDaoManager orderDao, ProductDao productDao, TaxDao taxDao){
        this.orderDaoManager = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }
    
    public List<Order> getOrdersByDate(LocalDate dateSelection){
       return orderDaoManager.getOrdersByDate(dateSelection);
    }
    
    public Response validateOrder(Order order){
        
        Response response = new Response();
        
        if(isNullOrWhiteSpace(order.getCustomerName())){
            response.addError("Customer name is required.");
        }
        
        if(isUnsupportedState(order.getState())){
            response.addError("Sorry, we do not operate in "+order.getState()+" yet.");
        }
        
        if(isUnsupportedProductType(order.getProductType())){
            response.addError("Sorry, we do not carry "+order.getProductType()+" yet.");
        }
        
        if(isNullOrWhiteSpace(order.getArea().toString())){
            response.addError("An area value is required.");
        }
        
        if(response.hasError()){
            return response;
        }
        
        order = calculateCosts(order);
        
        response.setOrder(order);
        
        return response;
        
    }
    
    public Order addOrder(Order order) {
        return orderDaoManager.add(order);
    }
    
    public boolean editOrder(Order order){
        return orderDaoManager.editOrder(order);
    }
    
    public boolean removeOrder(Order order){
        return orderDaoManager.removeOrder(order);
    }
    
    public Order retrieveOrder(Order requestedOrder){
        return orderDaoManager.getOrder(requestedOrder);
    }
    
    public boolean isNullOrWhiteSpace(String value) {
        return value == null || value.trim().length() == 0;
    }
    
    public boolean isUnsupportedState(String state){
        return taxDao.getTaxByState(state) == null;
    }
    
    public boolean isUnsupportedProductType(String productType){
        return productDao.getProduct(productType) == null;
    }
    
    public Order calculateCosts(Order order){
        Product product = productDao.getProduct(order.getProductType());
        Tax tax = taxDao.getTaxByState(order.getState());
        BigDecimal taxMultiplier = tax.getTaxRate().scaleByPowerOfTen(-2);
        
        order.setTaxRate(tax.getTaxRate());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()));
        order.setTaxCost((order.getMaterialCost().add(order.getLaborCost())).multiply(taxMultiplier));
        order.setTotalCost(order.getMaterialCost().add(order.getLaborCost()).add(order.getTaxCost()));
        
        return order;
    }
    

}
