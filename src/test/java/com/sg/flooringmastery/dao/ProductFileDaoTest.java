package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class ProductFileDaoTest {
    
    ProductDao productDao = new ProductFileDao("test-data/Test-Products.txt");

    @Test
    public void testGetAll() {
        List<Product> products = productDao.getAll();
        assertTrue(products.size() > 0);
    }

    @Test
    public void testGetProduct() {
        Product cork = productDao.getProduct("Cork");
        assertTrue(cork.getProductType().equals("Cork"));
        assertNotNull(cork.getCostPerSquareFoot());
        assertNotNull(cork.getLaborCostPerSquareFoot());
    }
    
    @Test
    public void testGetProductCaseInsensitive() {
        Product cork = productDao.getProduct("cOrK");
        assertTrue(cork.getProductType().equals("Cork"));
        assertNotNull(cork.getCostPerSquareFoot());
        assertNotNull(cork.getLaborCostPerSquareFoot());
    }
    
    @Test
    
    public void testGetProductNull(){
        Product nullProduct = productDao.getProduct("IS THIS A PRODUCT?");
        assertNull(nullProduct);
    }
    
}
