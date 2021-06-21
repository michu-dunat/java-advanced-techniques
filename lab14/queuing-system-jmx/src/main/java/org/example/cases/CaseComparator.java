package org.example.cases;

import java.util.Comparator;

public class CaseComparator implements Comparator<Case> {

    @Override
    public int compare(Case o1, Case o2) {
        return o2.getCaseCategory().getPriority() - o1.getCaseCategory().getPriority();
    }
}
