package com.missingvia.slamduck.common.util;

import java.text.DateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomSqlDateEditor extends CustomDateEditor{
	public CustomSqlDateEditor(DateFormat dateFormat, boolean allowEmpty)
	  {
	    super(dateFormat, allowEmpty);
	  }

	  public Object getValue()
	  {
	    return new java.sql.Date(((java.util.Date)super.getValue()).getTime());
	  }
}
