package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<IValidityCheck> checks = new ArrayList<>();
        checks.add(new NotNullValidityCheck());
        checks.add(new PersonalNumberValidityCheck());
        checks.add(new BilRegNmrValidityCheck());

        ValidityChecker validityChecker = new ValidityChecker(checks);
        validityChecker.runChecks("ABC12A");
    }
}
