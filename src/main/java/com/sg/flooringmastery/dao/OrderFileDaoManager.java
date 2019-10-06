package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class OrderFileDaoManager implements OrderDaoManager { 
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMddyyyy");
    private final String DATA_DIRECTORY = "flooring-data-v3/";
    

    @Override
    public List<Order> getOrdersByDate(LocalDate dateSelection) {
        return buildDao(dateSelection).getAll();
    }
    
    @Override
    public OrderDao buildDao(LocalDate dateSelection){
        String path = generatePath(dateSelection);
        return new OrderFileDao(path, dateSelection);
    }
    
    @Override
    public String generatePath(LocalDate dateSelection) {
        String formattedDate = dateSelection.format(DATE_FORMATTER);
        return DATA_DIRECTORY+"Orders_"+formattedDate+".txt";
    }
    
    @Override
    public Order add(Order order) {
        String path = generatePath(order.getDate());
        File tempFile = new File(path);
        boolean newFile = false;
        if(!tempFile.exists()){
            File dir = new File(path);
            newFile = true;
        }
        try{
            OrderDao orderDao = buildDao(order.getDate());
            if(newFile){
                orderDao.addHeader();
            }
            return orderDao.addOrder(order);
        }catch(StorageException ex){
            return null;
        }
    }
    
    @Override
    public boolean editOrder(Order orderToEdit){
        OrderDao orderDao = buildDao(orderToEdit.getDate());
        try{
            return orderDao.editOrder(orderToEdit);
        }catch(StorageException ex){
            return false;
        }
    }

    @Override
    public Order getOrder(Order requestedOrder) {
        OrderDao orderDao = buildDao(requestedOrder.getDate());
        return orderDao.getOrder(requestedOrder.getOrderId());
    }

    @Override
    public boolean removeOrder(Order orderToRemove) {
        OrderDao orderDao = buildDao(orderToRemove.getDate());
        try{
            return orderDao.removeOrder(orderToRemove);
        }catch(StorageException ex){
            return false;
        }
    }
}
