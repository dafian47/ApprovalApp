package com.xeranta.dev.approvalapp.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Helper {

    private static final String DATE_SERVER_FORMAT = "yyyy-MM-dd";
    private static final String DATE_LOCAL_FORMAT = "dd MMMM yyyy";

    public static String changeDateFormat(String createDate) {

        String date = "";
        SimpleDateFormat input = new SimpleDateFormat(DATE_SERVER_FORMAT);
        SimpleDateFormat output = new SimpleDateFormat(DATE_LOCAL_FORMAT);

        Date formatDate;

        try {

            formatDate = input.parse(createDate);
            date = output.format(formatDate);

        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return date;
    }
}
