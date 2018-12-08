package com.example.mypackage;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public class AuthorFilter implements Predicate {

    private String[] authors;
    private String colName = null;
    private int colNumber = -1;

    public AuthorFilter(String[] authorsArg, String colNameArg) {
        this.authors = authorsArg;
        this.colNumber = -1;
        this.colName = colNameArg;
    }

    public AuthorFilter(String[] authorsArg, int colNumberArg) {
        this.authors = authorsArg;
        this.colNumber = colNumberArg;
        this.colName = null;
    }

    public boolean evaluate( Object valueArg, String colNameArg) {

        if (colNameArg.equalsIgnoreCase(this.colName)) {
            for (int i = 0; i < this.authors.length; i++) {
                if (this.authors[i].equalsIgnoreCase((String)valueArg)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean evaluate(Object valueArg, int colNumberArg) {

        if (colNumberArg == this.colNumber) {
            for (int i = 0; i < this.authors.length; i++) {
                if (this.authors[i].equalsIgnoreCase((String)valueArg)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean evaluate(RowSet rs) {

        if (rs == null) return false;

        try {
            for (int i = 0; i < this.authors.length; i++) {

                String cityName = null;

                if (this.colNumber > 0) {
                    cityName = (String)rs.getObject(this.colNumber);
                } else if (this.colName != null) {
                    cityName = (String)rs.getObject(this.colName);
                } else {
                    return false;
                }

                if (cityName.equalsIgnoreCase(authors[i])) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }}
