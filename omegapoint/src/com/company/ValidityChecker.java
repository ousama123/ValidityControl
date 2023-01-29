package com.company;

import java.util.ArrayList;
import java.util.List;

class ValidityChecker {
    private final List<IValidityCheck> checks;

    public ValidityChecker(List<IValidityCheck> checks) {
        this.checks = checks;
    }
    
    public void runChecks(String data) {
        for (IValidityCheck check : checks) {
            try {
                check.check(data);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
}
