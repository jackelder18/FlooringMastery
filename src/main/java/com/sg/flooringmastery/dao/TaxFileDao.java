package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.models.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date July 2, 2019
 * @author Jack Elder
 */
public class TaxFileDao extends FileDao<Tax> implements TaxDao {
    
    public TaxFileDao(String path){
        super(path, 2, true);
    }

    @Override
    public List<Tax> getAll() {
        try{
            return read(this::mapToTax).stream()
                    .collect(Collectors.toList());
        }catch(StorageException ex){
            return new ArrayList<>();
        }
    }

    @Override
    public Tax getTaxByState(String state) {
        return getAll().stream()
                .filter(t -> t.getState().equalsIgnoreCase(state))
                .findFirst()
                .orElse(null);
    }
    
    private Tax mapToTax(String[] tokens){
        String state = tokens[0];
        BigDecimal taxRate = new BigDecimal(tokens[1]);
        return new Tax(state, taxRate);
    }
    
}
