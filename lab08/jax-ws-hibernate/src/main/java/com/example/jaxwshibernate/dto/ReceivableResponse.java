package com.example.jaxwshibernate.dto;

import com.example.jaxwshibernate.models.AccruedReceivable;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(name = "ReceivableResponse")
public class ReceivableResponse {

    private ArrayList<AccruedReceivable> receivables;

    public ArrayList<AccruedReceivable> getReceivables() {
        return receivables;
    }

    public void setReceivables(ArrayList<AccruedReceivable> receivables) {
        this.receivables = receivables;
    }
}
