package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class ProductFileDao extends FileDao <Product> implements ProductDao {
    
    public ProductFileDao(String path){
        super(path, 3, true);
    }

    @Override
    public List<Product> getAll() {
        try {
            return read(this::mapToProduct).stream()
                    .collect(Collectors.toList());
        } catch (StorageException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Product getProduct(String productType) {
        return getAll().stream()
                .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                .findFirst()
                .orElse(null);
    }
    
    private Product mapToProduct(String[] tokens){
        String productType = tokens[0];
        BigDecimal costPerSquareFoot = new BigDecimal(tokens[1]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(tokens[2]);
        return new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }
    
}
