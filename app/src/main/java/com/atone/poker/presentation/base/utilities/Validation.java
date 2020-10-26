package com.atone.poker.presentation.base.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean validateCard(String cardName) {
        String regex = "^[HSDC]([1-9]|1[0-3])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardName);
        return matcher.matches();
    }
}
