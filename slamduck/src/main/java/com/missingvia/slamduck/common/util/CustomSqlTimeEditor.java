package com.missingvia.slamduck.common.util;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomSqlTimeEditor extends CustomDateEditor{

	public CustomSqlTimeEditor(DateFormat dateFormat, boolean allowEmpty)
	  {
	    super(dateFormat, allowEmpty);
	  }

	  public Object getValue()
	  {
	    return new Time(((Date)super.getValue()).getTime());
	  }
}
