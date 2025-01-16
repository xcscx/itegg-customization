package com.itegg.customization.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DemoData  implements Serializable {

    private static final long serialVersionUID = 1234567895414L;

    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    @ExcelProperty("待用标题")
    private String title;
    @ExcelProperty("随机字符")
    private String randStr;

}
