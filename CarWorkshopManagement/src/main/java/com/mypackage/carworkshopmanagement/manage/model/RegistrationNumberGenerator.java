package com.mypackage.carworkshopmanagement.manage.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationNumberGenerator {

    private static int counter = 0;

    public static String generateUniqueRegistrationNumber() {
        counter++;
        if(counter>99){
            counter=0;
        }
        long timestamp = System.currentTimeMillis();
        String formattedDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(timestamp));
        return String.format("%s%02d", formattedDate, counter); // Combining formatted date with a 4-digit counter
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            String uniqueRegNumber = generateUniqueRegistrationNumber();
//            System.out.println("Unique Registration Number: " + uniqueRegNumber);
//        }
//    }
}
