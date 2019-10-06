package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Product;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public interface ProductDao {
    
    public List<Product> getAll();
    
    public Product getProduct(String productType);
    
}
