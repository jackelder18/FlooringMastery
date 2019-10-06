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
 * @date July 2, 2019
 * @author Jack Elder
 */
public class OrderFileDaoTest {

    private static final String SEED_FILE = "test-data/Seed-Orders_04022019.txt";
    private static final String DATA_FILE = "test-data/Test-Orders_04022019.txt";
    private static final LocalDate FILE_DATE = LocalDate.of(2019, 4, 22);
    
    private final OrderFileDao orderDao = new OrderFileDao(DATA_FILE, FILE_DATE);
    
    @BeforeAll
    public static void setUp() throws IOException{
        Path source = Paths.get(SEED_FILE);
        Path destination = Paths.get(DATA_FILE);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void testGetAll() {
        List<Order> orders = orderDao.getAll();
        assertTrue(orders.size() > 0);
    }

    @Test
    public void testAddOrder() throws StorageException {
        int initialDataSize = orderDao.getAll().size();
        Order newOrder = new Order(FILE_DATE);
        newOrder.setCustomerName("Test Person");
        newOrder.setState("Minnesota");
        newOrder.setTaxRate(new BigDecimal("5.00"));
        newOrder.setProductType("Slate");
        newOrder.setArea(new BigDecimal("40"));
        newOrder.setCostPerSquareFoot(BigDecimal.ZERO);
        newOrder.setLaborCostPerSquareFoot(BigDecimal.ZERO);
        newOrder.setMaterialCost(BigDecimal.ZERO);
        newOrder.setLaborCost(BigDecimal.ZERO);
        newOrder.setTaxCost(BigDecimal.ZERO);
        newOrder.setTotalCost(BigDecimal.ZERO);
        orderDao.addOrder(newOrder);
        assertEquals(orderDao.getAll().size(), initialDataSize+1);
    }

    @Test
    public void testGetOrder() {
        Order order = orderDao.getOrder(5);
        assertNotNull(order);
        assertNotNull(order.getProductType());
        assertNotNull(order.getMaterialCost());
    }
    
    @Test
    public void testGetOrderNonexistent(){
        Order order = orderDao.getOrder(300);
        assertNull(order);
    }

    @Test
    public void testEditOrder() throws StorageException {
        Order edits = new Order(FILE_DATE);
        Order original = orderDao.getOrder(8);
        edits.setOrderId(8);
        edits.setCustomerName("Matt Damon's Wisconsin Country Store");
        edits.setState("WI");
        edits.setProductType(original.getProductType()); //unedited product type and area
        edits.setArea(original.getArea());
        edits.setTaxRate(new BigDecimal("5.00"));
        edits.setCostPerSquareFoot(BigDecimal.ZERO);
        edits.setLaborCostPerSquareFoot(BigDecimal.ZERO);
        edits.setMaterialCost(BigDecimal.ZERO);
        edits.setLaborCost(BigDecimal.ZERO);
        edits.setTaxCost(BigDecimal.ZERO);
        edits.setTotalCost(BigDecimal.ZERO);
        orderDao.editOrder(edits);
        Order result = orderDao.getOrder(8);
        assertTrue(result.getCustomerName().equals("Matt Damon's Wisconsin Country Store"));
        assertTrue(result.getState().equals("WI"));
        assertTrue(result.getProductType().equals(original.getProductType()));
        assertTrue(result.getArea().compareTo(original.getArea()) == 0);
    }

    @Test
    public void testRemoveOrder() throws StorageException {
        int initialDataSize = orderDao.getAll().size();
        Order orderToRemove = orderDao.getOrder(3);
        boolean isRemoved = orderDao.removeOrder(orderToRemove);
        assertTrue(isRemoved);
        assertEquals(orderDao.getAll().size(), initialDataSize-1);
    }
    
    @Test
    public void testRemoveOrderTwo() throws StorageException{
        Order orderToRemove = orderDao.getOrder(10);
        orderDao.removeOrder(orderToRemove);
        assertNull(orderDao.getOrder(10));
    }
    
}
