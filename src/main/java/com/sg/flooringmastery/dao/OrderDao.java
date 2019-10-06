package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public interface OrderDao {
    
    public List<Order> getAll();
    
    public Order addOrder(Order order) throws StorageException;
    
    public Order getOrder(int orderId);
    
    public boolean editOrder(Order order) throws StorageException;
    
    public boolean removeOrder(Order order) throws StorageException;
    
    public void addHeader() throws StorageException;
    
}
