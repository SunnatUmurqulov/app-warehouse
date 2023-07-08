package uz.pdp.appwarehouse.utill;

import java.util.UUID;

public class GenerateNumber {
    public static String generateUniqueNumber(){
        return  UUID.randomUUID().toString();
    }
}
