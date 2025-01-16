package com.itegg.customization.model.vo;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

@Data
public class Watermark {

    /**
     * 获取默认水印 - "xxx 时间 xxx"
     *
     * @return 默认水印
     */
    public static String getDefaultWatermark() {
        String str1 = "";
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        return String.format("%s %s %s", str1, date, "");
    }

    public Watermark(String content) {
        this.content = content;
        init();
    }

    public Watermark(String content, String color, Font font, double angle) {
        this.content = content;
        this.color = color;
        this.font = font;
        this.angle = angle;
        init();
    }

    /**
     * 根据水印内容长度自适应水印图片大小，简单的三角函数
     */
    private void init() {
        FontMetrics fontMetrics = new JLabel().getFontMetrics(this.font);
        int stringWidth = fontMetrics.stringWidth(this.content);
        int charWidth = fontMetrics.charWidth('A');
        this.width = (int)Math.abs(stringWidth * Math.cos(Math.toRadians(this.angle))) + 2 * charWidth;
        this.height = (int)Math.abs(stringWidth * Math.sin(Math.toRadians(this.angle))) + 2 * charWidth;
        this.yAxis = this.height;
        this.xAxis = charWidth;
    }

    /**
     * 水印内容
     */
    private String content;

    /**
     * 画笔颜色
     */
    private String color = "#CCCCCC";

    /**
     * 字体样式
     */
    private Font font = new Font("Microsoft YaHei", Font.BOLD, 25);

    /**
     * 水印宽度
     */
    private int width;

    /**
     * 水印高度
     */
    private int height;

    /**
     * 倾斜角度，非弧度制
     */
    private double angle = 25;

    /**
     * 字体的y轴位置
     */
    private int yAxis;

    /**
     * 字体的X轴位置
     */
    private int xAxis;
}
