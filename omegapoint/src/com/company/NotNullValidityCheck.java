package com.company;

import java.util.logging.Logger;

public class NotNullValidityCheck implements IValidityCheck {
    private static final Logger LOGGER = Logger.getLogger(NotNullValidityCheck.class.getName());
    @Override
    public boolean check(String data) {
        if (data == null || data.isEmpty()) {
            //LOGGER.warning("Data is null!!");
            throw new IllegalArgumentException("Data is null!!");
            //return false;
        }
        return true;
    }
}
