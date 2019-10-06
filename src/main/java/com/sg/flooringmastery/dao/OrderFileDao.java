package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class OrderFileDao extends FileDao<Order> implements OrderDao {
    LocalDate date;
    
    public OrderFileDao(String path, LocalDate date){
        super(path, 12, true);
        this.date = date;
    }

    @Override
    public List<Order> getAll() {
        try{
            return read(this::mapToOrder).stream()
                    .collect(Collectors.toList());
        }catch(StorageException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Order addOrder(Order order) throws StorageException {
        List<Order> currentOrders = getAll();
        if(currentOrders.size() <= 0){
            order.setOrderId(1);
        } else {
            Order highestIdOrder = getAll().stream()
                    .sorted((b, a) -> a.getOrderId() - b.getOrderId())
                    .findFirst()
                    .orElse(null);
            order.setOrderId(highestIdOrder.getOrderId()+1);
        }
        append(order, this::mapToString);
        return order;
    }
    
    @Override
    public void addHeader() throws StorageException{
        appendString(HEADER);
    }

    @Override
    public Order getOrder(int orderId) {
        return getAll().stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean editOrder(Order order) throws StorageException {
        List<Order> orders = getAll();
        
        int index = 0;
        for(; index< orders.size(); index++){
            if(orders.get(index).getOrderId() == order.getOrderId()){
                break;
            }
        }
        
        if(index < orders.size()){
            orders.set(index, order);
            write(orders, this::mapToString);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeOrder(Order order) throws StorageException {
        List<Order> orders = getAll();
        
        int index = 0;
        for(; index < orders.size(); index++){
            if(orders.get(index).getOrderId() == order.getOrderId()){
                break;
            }
        }
        
        if(index < orders.size()){
            orders.remove(index);
            write(orders, this::mapToString);
            return true;
        }
        return false;
    }
    
    private Order mapToOrder(String[] tokens){
        Order o = new Order(date);
        o.setOrderId(Integer.parseInt(tokens[0]));
        o.setCustomerName(tokens[1]);
        o.setState(tokens[2]);
        o.setTaxRate(new BigDecimal(tokens[3]));
        o.setProductType(tokens[4]);
        o.setArea(new BigDecimal(tokens[5]));
        o.setCostPerSquareFoot(new BigDecimal(tokens[6]));
        o.setLaborCostPerSquareFoot(new BigDecimal(tokens[7]));
        o.setMaterialCost(new BigDecimal(tokens[8]));
        o.setLaborCost(new BigDecimal(tokens[9]));
        o.setTaxCost(new BigDecimal(tokens[10]));
        o.setTotalCost(new BigDecimal(tokens[11]));
        return o;
    }
    
    private String mapToString(Order o){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                o.getOrderId(),
                o.getCustomerName(),
                o.getState(),
                o.getTaxRate(),
                o.getProductType(),
                o.getArea(),
                o.getCostPerSquareFoot(),
                o.getLaborCostPerSquareFoot(),
                o.getMaterialCost(),
                o.getLaborCost(),
                o.getTaxCost(),
                o.getTotalCost());
    }

}
