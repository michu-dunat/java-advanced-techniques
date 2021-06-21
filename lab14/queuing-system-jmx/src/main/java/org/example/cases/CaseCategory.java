package org.example.cases;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeDataView;
import javax.management.openmbean.CompositeType;
import java.beans.ConstructorProperties;

public class CaseCategory implements CompositeDataView {
    private String name;
    private Integer priority;
    private char letter;

    @ConstructorProperties({ "name", "priority", "letter"})
    public CaseCategory(String name, Integer priority, char letter) {
        this.name = name;
        this.priority = priority;
        this.letter = letter;
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

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return name + ", " + priority.toString();
    }

    public static CaseCategory from(CompositeData cd) {
        return new CaseCategory((String) cd.get("name"),
                (Integer) cd.get("priority"), (char) cd.get("letter"));
    }

    @Override
    public CompositeData toCompositeData(CompositeType ct) {
        try {
            CompositeData cd = new CompositeDataSupport(ct, new String[] { "name", "priority", "letter" },
                    new Object[] { name, priority, letter });
            assert ct.isValue(cd);
            return cd;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
