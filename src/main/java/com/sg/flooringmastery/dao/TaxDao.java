package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Tax;
import java.util.List;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public interface TaxDao {
    
    public List<Tax> getAll();
    
    public Tax getTaxByState(String state);
    
}
