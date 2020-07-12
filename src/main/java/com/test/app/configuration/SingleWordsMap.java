package com.test.app.configuration;

import java.util.HashMap;
import java.util.Map;

public class SingleWordsMap {

    private static Map<String,Long> wordMap = new HashMap<>();

    public static Map<String, Long> getWordMap() {
        return wordMap;
    }


}
