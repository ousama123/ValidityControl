package com.company;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilRegNmrValidityCheck implements IValidityCheck{
    public Logger LOGGER = Logger.getLogger(BilRegNmrValidityCheck.class.getName());
    @Override
    public boolean check(String number) {
        // Mönster för ett giltigt bilregistreringsnummer
        String pattern = "^[A-Z]{3}[0-9]{2}[A-Z0-9]{1}$";
        // Kompilera mönstret
        Pattern p = Pattern.compile(pattern);
        // Sök efter mönstret i indatat
        Matcher m = p.matcher(number);
        // Kontrollera om det finns en matchning
        if (m.find()){
            System.out.println("The Reg number: " + number + " is correct reg number!");
            return true;
        }
        LOGGER.warning("The Reg number: " + number + " is incorrect reg number!");
        return false;
    }
}
