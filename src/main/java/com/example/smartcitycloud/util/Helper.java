package com.example.smartcitycloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Helper {
    private static final Pattern PATTERN_DATA = Pattern.compile("ab.*?!%");

    /**
     * 获取有效数据
     *
     * @param content
     * @return
     */
    public static List<String> matchedVars(String content) {
        List<String> data = new ArrayList<>();
        Matcher matcher = PATTERN_DATA.matcher(content);
        while (matcher.find()) {
            data.add(matcher.group().replace("ab", "").replace("!%", ""));
        }
        return data;
    }

    public static Long str2Timestamp(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return str2DateTime(str).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime str2DateTime(String str) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) {
        String content = "xxxxxab123,1,15,2019-04-12!%,,,,,,ab123,2,17,2019-04-12!%ssssab123,2,17,";
        List<String> list = matchedVars(content);
        log.info("list#{}", list);
    }
}
