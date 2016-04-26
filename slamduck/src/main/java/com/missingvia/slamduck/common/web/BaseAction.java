package com.missingvia.slamduck.common.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.missingvia.slamduck.common.util.CustomSqlDateEditor;
import com.missingvia.slamduck.common.util.CustomSqlTimeEditor;
import com.missingvia.slamduck.common.util.CustomTimestampEditor;
import com.missingvia.slamduck.common.util.DateTimePattern;

/**
 * 控制器基类。
 *
 */
public class BaseAction {
	/**
     * 日志操作句柄
     */
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimePattern.DATE.getPattern());

        SimpleDateFormat timeFormat = new SimpleDateFormat(DateTimePattern.HMS.getPattern());

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());

        binder.registerCustomEditor(Integer.class, null,
                new CustomNumberEditor(Integer.class, new DecimalFormat(), true));

        binder.registerCustomEditor(Long.class, null,
                new CustomNumberEditor(Long.class, new DecimalFormat(), true));

        binder.registerCustomEditor(Double.class, null,
                new CustomNumberEditor(Double.class, new DecimalFormat(), true));

        binder.registerCustomEditor(Float.class,
                new CustomNumberEditor(Float.class, new DecimalFormat(), true));

        binder.registerCustomEditor(BigDecimal.class,
                new CustomNumberEditor(BigDecimal.class, new DecimalFormat(), true));

        binder.registerCustomEditor(java.util.Date.class, null,
                new CustomDateEditor(dateTimeFormat, true));

        binder.registerCustomEditor(java.sql.Date.class, null,
                new CustomSqlDateEditor(dateFormat, true));

        binder.registerCustomEditor(java.sql.Timestamp.class, null,
                new CustomTimestampEditor(dateTimeFormat, true));

        binder.registerCustomEditor(java.sql.Time.class, null,
                new CustomSqlTimeEditor(timeFormat, true));

        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }




    /**
     * 打印FormResponse格式的JSON数据。
     *
     * @param response
     * @param responseInfo
     * @throws IOException
     */
    protected void printResponseInfoJson(HttpServletResponse response, CommonResponse responseInfo)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        printJson(response,mapper.writeValueAsString(responseInfo));
    }


    /**
     * 输出一段文本，内容类型为：text/html;charset=utf-8。
     *
     * @param response
     * @param text
     * @throws java.io.IOException
     */
    protected void printText(HttpServletResponse response, String text) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(text);
    }

    /**
     * 输出JSON，内容类型为：application/json;charset=utf-8。
     *
     * @param response
     * @param text
     * @throws java.io.IOException
     */
    protected void printJson(HttpServletResponse response, String text) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(text);
    }
}
