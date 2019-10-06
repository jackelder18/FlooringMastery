package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.models.Order;
import com.sg.flooringmastery.service.OrderLibrary;
import com.sg.flooringmastery.service.Response;
import com.sg.flooringmastery.ui.MainMenuOption;
import com.sg.flooringmastery.ui.View;
import java.time.LocalDate;
import java.util.List;

/**
 * @date July 1, 2019
 * @author Jack Elder
 */
public class Controller {
    private final View view;
    private final OrderLibrary orderLibrary;
    
    public Controller(View view, OrderLibrary orderLibrary){
        this.view = view;
        this.orderLibrary = orderLibrary;
    }
    
    public void run() {
        
        view.displayWelcome();
        MainMenuOption menuSelection;
        
        do{
            menuSelection = view.selectFromMainMenu();
            
            switch(menuSelection){
                case DISPLAY_ORDERS:
                    displayOrders();
                    break;
                case ADD_ORDER:
                    addOrder();
                    break;
                case EDIT_ORDER:
                    editOrder();
                    break;
                case REMOVE_ORDER:
                    removeOrder();
                    break;
            }                     
        }while(menuSelection != MainMenuOption.QUIT);
        
       view.displayGoodbye();
    }

    private void displayOrders() {
        view.displayDisplayOrdersBanner();
        LocalDate dateSelection = view.getDate();
        List<Order> orders = orderLibrary.getOrdersByDate(dateSelection);
        view.displayOrders(orders);
    }

    private void addOrder(){
        Order order = view.getNewOrder();
        Response response = orderLibrary.validateOrder(order);
        
        if(response.hasError()){
            view.printErrors(response.getErrors());
        }else{
            if(view.shouldCommitOrder(response)){
                order = orderLibrary.addOrder(order);
                view.displayAddOrderResult(order);
            }
        }
    }

    private void editOrder() {
        view.displayEditOrderBanner();
        Order requestedOrder;
        Order retrievedOrder = null;
        
        while (retrievedOrder == null) {

            requestedOrder = view.getOrderSelection();
            retrievedOrder = orderLibrary.retrieveOrder(requestedOrder);
            if (retrievedOrder == null) {
                view.displayNoOrderFound();
            } else {
                Order editedOrder = view.editOrder(retrievedOrder);
                Response response = orderLibrary.validateOrder(editedOrder);
                if (response.hasError()) {
                    view.printErrors(response.getErrors());
                } else {
                    if (view.shouldCommitOrder(response)) {
                        view.displayEditOrderResult(orderLibrary.editOrder(editedOrder));
                    }
                }
            }
        }
    }

    private void removeOrder() {
        view.displayRemoveOrderBanner();
        Order requestedOrder;
        Order retrievedOrder = null;

        while (retrievedOrder == null) {

            requestedOrder = view.getOrderSelection();
            retrievedOrder = orderLibrary.retrieveOrder(requestedOrder);
            if (retrievedOrder == null) {
                view.displayNoOrderFound();
            } else {
                if (view.shouldDeleteOrder(retrievedOrder)) {
                    view.displayRemoveOrderResult(orderLibrary.removeOrder(retrievedOrder));
                }
            }
        }
    }
}
