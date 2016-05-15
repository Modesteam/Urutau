package com.modesteam.urutau.dao.impl;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class DaoHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(DaoHelper.class);
    
    /**
     * This is a validation of parameter value
     * 
     * @return true 
     */
    public boolean isValidParameter(Object value) {
        boolean validParameter = false;

        if (value instanceof Integer || value instanceof String || value instanceof Long) {
            validParameter = true;
        }

        return validParameter;
    }
    
    /**
     * Generate String to pass in {@link EntityManager#createQuery(String)}
     * 
     * @param field Object key of search
     * @return String with command SQL
     */
    public String getSelectQuery(final Class<?> entity, final Object field) {
        String sql = "SELECT table FROM "+ entity.getName() +" table"
                + " WHERE table." + field + "=:value";
        
        logger.info(sql);
        
        return sql;
    }
}
