package com.resoft.winner.response.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class WinnerConverter  extends AbstractBeanField<Object, Object>{
	
	@Override
    protected Object convert(String winner) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if(winner != null && winner.equalsIgnoreCase("yes")) {
        	return true;
        }
        return false;
  }

}
