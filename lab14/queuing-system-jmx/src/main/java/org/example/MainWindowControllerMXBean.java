package org.example;

import org.example.cases.CaseCategory;

import java.util.List;

public interface MainWindowControllerMXBean {

    public List<CaseCategory> getCaseCategoryList();
    public void setCaseCategoryList(List<CaseCategory> observableList);

}
