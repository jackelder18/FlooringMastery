package com.sg.flooringmastery.service;

import com.sg.flooringmastery.models.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * @date July 3, 2019
 * @author Jack Elder
 * @source Corbin March
 */
public class Response {
    private final ArrayList<String> errorMessages = new ArrayList<>();
    private Order order;

    public boolean hasError() {
        return errorMessages.size() > 0;
    }

    public void addError(String message) {
        errorMessages.add(message);
    }

    public List<String> getErrors() {
        return new ArrayList<>(errorMessages);
    }

    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order){
        this.order = order;
    }
    
}
