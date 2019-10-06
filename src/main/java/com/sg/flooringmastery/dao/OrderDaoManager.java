package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;
import java.time.LocalDate;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public interface OrderDaoManager {
    
    public String generatePath(LocalDate dateSelection);
    
    public OrderDao buildDao(LocalDate dateSelection);
    
    public List<Order> getOrdersByDate(LocalDate dateSelection);
    
    public Order add(Order order);
    
    public boolean editOrder(Order orderToEdit);
    
    public boolean removeOrder(Order orderToRemove);
    
    public Order getOrder(Order requestedOrder);
    
}
