package com.missingvia.slamduck.common.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class CustomTimestampEditor extends CustomDateEditor{
	public CustomTimestampEditor(DateFormat dateFormat, boolean allowEmpty)
	  {
	    super(dateFormat, allowEmpty);
	  }

	  public Object getValue()
	  {
	    if (super.getValue() == null) {
	      return null;
	    }
	    return new Timestamp(((Date)super.getValue()).getTime());
	  }
}
