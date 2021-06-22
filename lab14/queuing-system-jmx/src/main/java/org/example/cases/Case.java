package org.example.cases;

public class Case {

    private CaseCategory caseCategory;
    private String id;

    public Case(CaseCategory caseCategory, Integer id) {
        this.caseCategory = caseCategory;
        this.id = caseCategory.getSymbol() + id.toString();
    }

    public CaseCategory getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(CaseCategory caseCategory) {
        this.caseCategory = caseCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
