package com.ljn.server.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author: ljn
 * @Date: 2022/02/26/12:23
 * @Description:日期转换
 */
@Component
public class DateConverter implements Converter<String,LocalDate> {


    @Override
    public LocalDate convert(String source){
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
