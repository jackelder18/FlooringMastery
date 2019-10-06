package com.sg.flooringmastery.service;

import com.sg.flooringmastery.models.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @date July 8, 2019
 * @author Jack Elder
 */
public class OrderLibraryTest {
    
    
    ApplicationContext ctx = new ClassPathXmlApplicationContext("test-context.xml");
    OrderLibrary orderLibrary = ctx.getBean("orderLibrary", OrderLibrary.class);
    private static final LocalDate FILE_DATE = LocalDate.of(2019, 4, 22);

    @Test
    public void testValidateOrderValid() {
        Order order = new Order(FILE_DATE);
        order.setCustomerName("Test Business");
        order.setProductType("Ceramic Tile");
        order.setState("NE");
        order.setArea(BigDecimal.TEN);
        
        Response response = orderLibrary.validateOrder(order);
        assertFalse(response.hasError());
    }
    
    @Test
    public void testValidateOrderOneError() {
        Order order = new Order(FILE_DATE);
        order.setCustomerName("Test Business");
        order.setProductType("Ceramic Tile");
        order.setState("WA");
        order.setArea(BigDecimal.TEN);
        
        Response response = orderLibrary.validateOrder(order);
        assertEquals(response.getErrors().size(), 1);
    }
    
    @Test
    public void testValidateOrderMultipleErrors() {
        Order order = new Order(FILE_DATE);
        order.setCustomerName(" ");
        order.setProductType("Just the best floor ever");
        order.setState("WA");
        order.setArea(BigDecimal.TEN);

        Response response = orderLibrary.validateOrder(order);
        assertEquals(response.getErrors().size(), 3);
    }

    @Test
    public void testIsNullOrWhiteSpaceNull() {
        String nullString = null;
        assertTrue(orderLibrary.isNullOrWhiteSpace(nullString));
    }
    
    @Test
    public void testIsNullOrWhiteSpaceWhiteSpace(){
        String whiteSpaceString = "    ";
        assertTrue(orderLibrary.isNullOrWhiteSpace(whiteSpaceString));
    }
    
    @Test
    public void testIsNullOrWhiteSpaceText(){
        String validString = "Flooring Company";
        assertFalse(orderLibrary.isNullOrWhiteSpace(validString));
    }

    @Test
    public void testIsUnsupportedStateUnsupported() {
        String unsupported = "WA";
        assertTrue(orderLibrary.isUnsupportedState(unsupported));
    }
    
    @Test
    public void testIsUnsupportedStateSupported() {
        String supported = "MN";
        assertFalse(orderLibrary.isUnsupportedState(supported));
    }

    @Test
    public void testIsUnsupportedProductTypeUnsupported() {
        String unsupported = "super soft, nice flooring";
        assertTrue(orderLibrary.isUnsupportedProductType(unsupported));
    }
    
    @Test
    public void testIsUnsupportedProductTypeSupported() {
        String supported = "Slate";
        assertFalse(orderLibrary.isUnsupportedProductType(supported));
    }
    
    @Test
    public void testIsUnsupportedProductTypeSupportedCaseInsensitive() {
        String supported = "sLAtE";
        assertFalse(orderLibrary.isUnsupportedProductType(supported));
    }

    @Test
    public void testCalculateCostsReadingValues() {
        Order order = new Order(FILE_DATE);
        order.setCustomerName("Test Business");
        order.setProductType("Ceramic Tile");
        order.setState("NE");
        order.setArea(BigDecimal.TEN);
        
        order = orderLibrary.calculateCosts(order);
        
        assertTrue(order.getTaxRate().equals(new BigDecimal("2.50")));
        assertTrue(order.getCostPerSquareFoot().equals(new BigDecimal("3.00")));
        assertTrue(order.getLaborCostPerSquareFoot().equals(new BigDecimal("4.25")));
    }
    
    @Test
    public void testCalculateCostsCalculations() {
        Order order = new Order(FILE_DATE);
        order.setCustomerName("Test Business");
        order.setProductType("Ceramic Tile");
        order.setState("NE");
        order.setArea(BigDecimal.TEN);
        
        order = orderLibrary.calculateCosts(order);

        assertTrue(order.getTaxCost().equals(new BigDecimal("1.81")));
        assertTrue(order.getTotalCost().equals(new BigDecimal("74.31")));
    }
    
}
