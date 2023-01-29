package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalNumberValidityCheck implements IValidityCheck {
    private static final Logger LOGGER = Logger.getLogger(PersonalNumberValidityCheck.class.getName());

    @Override
    public boolean check(String personalId) {

        //System.out.println("Before reformatting: " + personalId);

        personalId = reformat_pn(personalId); //reformating the personal number to get a one with size of 10 numbers!
        if (personalId.length() != 10) {
            LOGGER.warning("The personal number: " + personalId + " is in a wrong format!");
            return false;
        }

        //System.out.println("After reformatting: " + personalId);

        // Regex för att matcha ett giltigt personnummer i Sverige
        // i formaten "YYMMDD-XXXX", "YYYYMMDD-XXXX", "YYMMDDXXXX", "YYYYMMDDXXXX", "YYYMMDD-XXXX", "YYYMMDDXXXX"
        if (!isValidFormat(personalId)) {
            return false;
        }

        //special cases
        int month = Integer.parseInt(personalId.substring(2, 4));
        int day = Integer.parseInt(personalId.substring(4, 6));

        if (!isValidMonthOrDay(month, day)) {
            return false;
        }
        
        // Kontrollera luhn-algoritmen för att validera kontrollsiffran
        List<Integer> check_control_digits = controlLuhnAlgorithm(personalId);
        int controlDigit = check_control_digits.get(0);
        int checkDigit = check_control_digits.get(1);

        if (controlDigit == checkDigit) {
            System.out.println("Valid Personal Number.");
            return true;
        } else {
            LOGGER.warning("This personal number: " + personalId + " did not pass the control algorithm therefore it is invalid personal number! ");
            return false;
        }
    }

    private List<Integer> controlLuhnAlgorithm(String personalId) {
        List<Integer> check_control_digits = new ArrayList<>();
        int[] digits = personalId.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .toArray();

        int sum = 0;
        for (int i = 0; i < digits.length - 1; i++) {
            int digit = digits[i];
            digit *= 2 - (i % 2);
            if (digit > 9) {
                digit -= 9;
            }
            sum += digit;
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        int checkDigit = digits[digits.length - 1];
        check_control_digits.add(controlDigit);
        check_control_digits.add(checkDigit);
        return check_control_digits;
    }

    private String reformat_pn(String personalId) {

        personalId = personalId.replaceAll("-", "");
        if (personalId.length() == 12) {
            return personalId.substring(2);
        }
        if (personalId.length() == 11) {
            return personalId.substring(1);
        }
        return personalId;
    }

    private boolean isValidFormat(String personalId) {
        String regex = "^(\\d{10}|\\d{12}|\\d{6}-\\d{4}|\\d{8}-\\d{4}|\\d{8} \\d{4}|\\d{6} \\d{4})";

        // Kompilera mönstret
        Pattern pattern = Pattern.compile(regex);
        // Sök efter mönstret i indatat
        Matcher match = pattern.matcher(personalId);

        if (!match.matches()) {
            System.out.println("");
            LOGGER.warning("This personal number: " + personalId + " did not match the regex format! ");
            return false;
        }
        return true;
    }

    private boolean isValidMonthOrDay(int month, int day) {
        if (month < 1 || month > 12) {
            LOGGER.warning("Wrong month!");
            return false;
        }
        if (day < 1 || day > 31) {
            LOGGER.warning("Wrong day!");
            return false;
        }
        return true;
    }
}
