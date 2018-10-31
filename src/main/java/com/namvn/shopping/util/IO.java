package com.namvn.shopping.util;

import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.List;

public class IO {
    public List<String> cutWhiteSpaces(String str) {
        if(str!=null) {
            String[] splited = str.trim().split("\\s+");
            return Arrays.asList(splited);
        }
        else return null;
    }
}
