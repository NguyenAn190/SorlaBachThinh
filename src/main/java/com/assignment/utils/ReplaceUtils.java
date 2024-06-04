package com.assignment.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ReplaceUtils {
    public static Integer replacePrice(String priceString) {
        try {
            int price = Integer.parseInt(priceString.replaceAll("[^\\d]", ""));
            return Integer.parseInt(new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.getDefault())).format(price).replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(price);
    }

    public static String removeTwoZerosFromString(String totalPriceString) {
        try {
            int totalPrice = Integer.parseInt(totalPriceString);
            int modifiedPrice = totalPrice / 100;
            return String.valueOf(modifiedPrice);
        } catch (NumberFormatException e) {
            return "Invalid input";
        }
    }
}
