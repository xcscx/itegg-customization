package com.itegg.customization.controller;


import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;

import com.itegg.customization.exception.WatermarkHandler;
import com.itegg.customization.model.vo.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 水印工具类，正对easyExcel对数据表插入水印
 */
@Slf4j
@RestController
@RequestMapping("/watermark")
public class WatermarkController {

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("导出测试", "UTF-8");

        // 传递参数
        List<DemoData> data = data();
        List<String> strs = Arrays.asList("string", "randStr", "date");

        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class)
                .inMemory(true) // 注意，此项配置不能少
                .registerWriteHandler(new WatermarkHandler(getContent()))
                .sheet("模板")
                .includeColumnFieldNames(null)
                .doWrite(data);
    }

    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.57);
            data.setTitle("title");
            data.setRandStr("deidjei");
            list.add(data);
        }
        return list;
    }


    protected String getContent() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 创建日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 格式化日期并返回为字符串
        String formattedDate = currentDate.format(formatter);
        String lastMobile = "9919";
        Map<String, Object> param = new HashMap<>();
        param.put("NAME", "超级供应商");
        param.put("PHONE", lastMobile);
        param.put("TIME", formattedDate);

        return getContent("${NAME}-${PHONE}-${TIME}-中南", param);
    }

    /**
     * 填充内容
     * @param template 模板内容
     * @param param 构建参数
     * @return 填充内容
     */
    protected String getContent(String template, Map<String, Object> param) {
        StringBuilder result = new StringBuilder(template);
        // 使用正则表达式替换所有占位符
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");  // 匹配 ${key} 的模式

        // 只要有占位符，就继续匹配和替换
        while (true) {
            Matcher matcher = pattern.matcher(result);  // 每次都创建新的 Matcher
            if (!matcher.find()) {
                break;  // 如果没有找到匹配项，则退出循环
            }

            String key = matcher.group(1);  // 获取占位符中的 key
            if (param.containsKey(key)) {
                String value = param.get(key).toString();
                // 使用 StringBuilder 替换匹配到的内容
                result.replace(matcher.start(), matcher.end(), value);
            }
        }
        return result.toString();
    }
    public static void main(String[] args) {
        List<DemoData> data = data();
        for(DemoData d : data) {
            System.out.println(JSONUtil.toJsonStr(d));
        }
    }

}

