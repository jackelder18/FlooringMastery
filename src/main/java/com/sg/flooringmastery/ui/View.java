package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.models.Order;
import com.sg.flooringmastery.service.Response;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class View {
    protected ConsoleIO io;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final int AREA_MIN = 0;
    
    public View(ConsoleIO io){
        this.io = io;
    }

    public MainMenuOption  selectFromMainMenu(){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        printHeader("Main Menu");
        for (MainMenuOption mmo : MainMenuOption.values()) {
            io.print(String.format("%s. %s", mmo.getValue(), mmo.getName()));
            min = Math.min(mmo.getValue(), min);
            max = Math.max(mmo.getValue(), max);
        }
        int value = io.readInt(String.format("Select [%s-%s]:", min, max), min, max);
        return MainMenuOption.fromValue(value);
    }
    
    
    public LocalDate getDate() {
        LocalDate dateSelection = io.readDate("Please input order date [mm/dd/yyyy]: ");
        return dateSelection;
    }
    
    public int getOrderId(){
        return io.readInt("Please input order ID number: ");
    }
    
    public Order getOrderSelection(){
        Order orderSelection = new Order(getDate());
        orderSelection.setOrderId(getOrderId());
        return orderSelection;
    }
    
    public Order getNewOrder(){
        printHeader("Add New Order");
        Order order = new Order(io.readDate("Order Date [MM/dd/yyyy]: "));
        order.setCustomerName(io.readRequiredString("Customer Name: "));
        order.setState(io.readRequiredString("State: "));
        order.setProductType(io.readRequiredString("Product Type: "));
        BigDecimal area = new BigDecimal(Double.toString(io.readDouble("Area in Square Feet: ", AREA_MIN, Integer.MAX_VALUE)));
        order.setArea(area.setScale(2, RoundingMode.HALF_UP));
        return order;
    }
    
    public Order editOrder(Order order){
        displayOrderSummary(order);
        io.print("\nPress enter without new data to leave field unedited.");
        String name = io.readString("Edit Customer Name: "+order.getCustomerName());
        String state = io.readString("Edit State: "+order.getState());
        String productType = io.readString("Edit Product Type: "+order.getProductType());
        double area = io.readDoubleOptional("Edit Square Footage: "+order.getArea(), AREA_MIN, Integer.MAX_VALUE);
        
        if(!name.isBlank()){
            order.setCustomerName(name);
        }
        if(!state.isBlank()){
            order.setState(state);
        }
        if(!productType.isBlank()){
            order.setProductType(productType);
        }
        if(area >= AREA_MIN){
            order.setArea(new BigDecimal(area));
        }
        return order;
    }
    
    public boolean shouldCommitOrder(Response response) {
        displayOrderSummary(response.getOrder());
        
        if(io.getConfirmation("\nIs this correct? Please confirm to submit order. [y/n]")){
            return true;
        }else{
            io.print("* Input information discarded *");
            return false;
        }
    }
    
    public boolean shouldDeleteOrder(Order order){
        displayOrderSummary(order);
        
        if(io.getConfirmation("\nDelete this order? [y/n]")){
            return true;
        }else{
            io.print("* Order was not deleted *");
            return false;
        }
    }
    
    public void displayAddOrderResult(Order order){
        io.print("");
        if(order == null){
            displayWriteToFileError();
        } else {
            printHeader("Your order has been added successfully!");
            displayOrderSummary(order);
        }
        io.print("");
    }
    
    public void displayRemoveOrderResult(boolean removeSuccess){
        io.print("");
        if(removeSuccess){
            printHeader("Order removed successfully!");
        }else{
            displayWriteToFileError();
        }
        io.print("");
    }
    
    public void displayOrderSummary(Order order){
        if(order == null){
            displayNoOrderFound();
        }else{
            if(order.getOrderId() == 0){
                io.print("\nHere is your order:");
            }else{
                io.print("\nOrder Number "+order.getOrderId()+", "+order.getDate().format(DATE_FORMATTER));
            }
            io.print(String.format("%s, %s. %s square feet of %s flooring.", 
                    order.getCustomerName(), order.getState(),
                    order.getArea(), order.getProductType()));
            io.print(String.format("Material Cost: $%s. Labor Cost: $%s. Tax: $%s. Total Cost: $%s.",
                    order.getMaterialCost(), order.getLaborCost(), 
                    order.getTaxCost(), order.getTotalCost()));
        }
    }
    
    public void displayOrders(List<Order> orders){
        if(orders.size() <= 0){
            io.print("No orders found for this date.\n");
        }else{
            printHeader("Orders for "+orders.get(0).getDate().format(DATE_FORMATTER));
            for(Order o: orders){
                io.print(String.format("%s: %s, %s", 
                        o.getOrderId(), o.getCustomerName(), o.getState()));
                io.print(String.format("%s sq. ft. %s flooring. Total Cost: $%s\n",
                        o.getArea(), o.getProductType(), o.getTotalCost()));
            }
        }
    }
    
    public void displayNoOrderFound(){
        io.print("No order found.\n");
    }
    
    public void printErrors(List<String> errors){
        io.print("ERROR");
        for(String error: errors){
            io.print(error);
        }
    }
    
    public void displayEditOrderResult(boolean editSuccess){
        io.print("");
        if(editSuccess){
            printHeader("Order edited successfully!");
        }else{
            displayWriteToFileError();
        }
        io.print("");
    }
    
    public void displayWriteToFileError(){
        io.print("ERROR: Changes not saved to file successfully.");
    }
      
    public void printHeader(String message){
        io.print("==== "+message+" ====");
    }
    
    public void displayWelcome(){
        io.print("**** FLOORS ACROSS THE NATION ****");
        io.print("           Welcome!\n");
    }

    public void displayGoodbye() {
        io.print("Thank you! Please come again.");
    }
    
    public void displayDisplayOrdersBanner(){
        printHeader("Display Orders by Date");
    }
    
    public void displayEditOrderBanner(){
        printHeader("Edit Order");
    }
    
    public void displayRemoveOrderBanner(){
        printHeader("Remove Order");
    }
    
}
