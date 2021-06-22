package org.example.cases;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import java.beans.ConstructorProperties;

public class CaseCategory implements CompositeDataView {
    private String name;
    private Integer priority;
    private String symbol;

    @ConstructorProperties({"name", "priority", "letter"})
    public CaseCategory(String name, Integer priority, String symbol) {
        this.name = name;
        this.priority = priority;
        this.symbol = symbol;
    }

    public static CaseCategory from(CompositeData cd) {
        return new CaseCategory((String) cd.get("name"),
                (Integer) cd.get("priority"), (String) cd.get("symbol"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name + ", " + priority.toString() + ", " + symbol;
    }

    @Override
    public CompositeData toCompositeData(CompositeType ct) {
        try {
            CompositeData cd = new CompositeDataSupport(ct, new String[]{"name", "priority", "symbol"},
                    new Object[]{name, priority, symbol});
            assert ct.isValue(cd);
            return cd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
