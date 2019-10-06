package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 * @date July 3, 2019
 * @author Jack Elder
 */
public class OrderFileDaoManagerTest {
    
    private static final String SEED_FILE = "test-data/Seed-Orders_04022019.txt";
    private static final String DATA_FILE = "test-data/Test-Orders_04022019.txt";
    private static final LocalDate FILE_DATE = LocalDate.of(2019, 4, 22);
    private OrderDaoManager orderDaoManager = new OrderFileDaoManager();
    
    @BeforeAll
    public static void setUp() throws IOException{
        Path source = Paths.get(SEED_FILE);
        Path destination = Paths.get(DATA_FILE);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void testGetOrdersByDate() {
        List<Order> ordersByDate = orderDaoManager.getOrdersByDate(FILE_DATE);
        assertTrue(ordersByDate.size() > 0);
    }

    @Test
    public void testGeneratePath() {
        LocalDate date = LocalDate.of(2019, 4, 2);
        String expectedPath = "flooring-data-v3/Orders_04022019.txt";
        System.out.println(orderDaoManager.generatePath(date));
        assertTrue(orderDaoManager.generatePath(date).equals(expectedPath));
    }
    
    @Test
    public void testGetOrder(){
        List<Order> ordersByDate = orderDaoManager.getOrdersByDate(FILE_DATE);
        Order order = ordersByDate.get(2);
        Order retrievedOrder = orderDaoManager.getOrder(order);
        assertTrue(order.getDate().equals(retrievedOrder.getDate()));
        assertTrue(order.getCustomerName().equals(retrievedOrder.getCustomerName()));
        assertTrue(order.getState().equals(retrievedOrder.getState()));
        assertTrue(order.getTotalCost().equals(retrievedOrder.getTotalCost()));
    }
    
    @Test
    public void testEditOrder(){
        Order edits = new Order(FILE_DATE);
        int initialSize = orderDaoManager.getOrdersByDate(FILE_DATE).size();
        edits.setOrderId(8);
        edits.setCustomerName("Matt Damon's Wisconsin Country Store");
        edits.setState("WI");
        edits.setProductType("Glass Tile");
        edits.setArea(BigDecimal.TEN);
        edits.setTaxRate(new BigDecimal("5.00"));
        edits.setCostPerSquareFoot(BigDecimal.ZERO);
        edits.setLaborCostPerSquareFoot(BigDecimal.ZERO);
        edits.setMaterialCost(BigDecimal.ZERO);
        edits.setLaborCost(BigDecimal.ZERO);
        edits.setTaxCost(BigDecimal.ZERO);
        edits.setTotalCost(BigDecimal.ZERO);
        
        orderDaoManager.editOrder(edits);
        assertEquals(orderDaoManager.getOrdersByDate(FILE_DATE).size(), initialSize);
    }
    
}
