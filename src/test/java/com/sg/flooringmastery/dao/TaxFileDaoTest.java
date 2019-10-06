package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Tax;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class TaxFileDaoTest {
    
    TaxDao taxDao = new TaxFileDao("test-data/Test-Taxes.txt");

    @Test
    public void testGetAll() {
        List<Tax> taxes = taxDao.getAll();
        assertTrue(taxes.size() > 0);
    }
    
    @Test
    public void testGetTaxByState() {
        Tax mnTax = taxDao.getTaxByState("MN");
        assertTrue(mnTax.getState().equals("MN"));
        assertNotNull(mnTax.getTaxRate());
    }
    
    @Test
    public void testGetTaxByStateCaseInsensitive(){
        Tax mnTax = taxDao.getTaxByState("mN");
        assertTrue(mnTax.getState().equals("MN"));
        assertNotNull(mnTax.getTaxRate());
    }
    
    @Test
    public void testGetTaxByStateNull(){
        Tax nullTax = taxDao.getTaxByState("Is mayonnaise an instrument?");
        assertNull(nullTax);
    }
    
}
