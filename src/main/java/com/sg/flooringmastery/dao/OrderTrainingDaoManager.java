package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;

/**
 * @date July 7, 2019
 * @author JacK Elder
 */
public class OrderTrainingDaoManager extends OrderFileDaoManager {

    @Override
    public Order add(Order order) {
        return null;
    }
    
    @Override
    public boolean editOrder(Order orderToEdit){
        return false;
    }
    
    @Override
    public boolean removeOrder(Order orderToRemove) {
        return false;
    }
}
