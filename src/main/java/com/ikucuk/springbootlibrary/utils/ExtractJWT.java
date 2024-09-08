package com.ikucuk.springbootlibrary.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token, String extraction){

        token.replace("Bearer ","");

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        String[] entries = payload.split(",");
        Map<String,String> map = new HashMap<String, String>();

        for(String entry : entries){
            String[] keyValue = entry.split(":");
            if(keyValue[0].equals(extraction)){
                int remove = 1;
                if(keyValue[1].endsWith("}")){
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0],keyValue[1]);
            }
        }
        if(map.containsKey(extraction)){
            return map.get(extraction);
        }
        return null;
    }
}
/*
String[] chunks = token.split("\\.");

Splits the JWT into its three parts (header, payload, signature) using the dot ('.') as a delimiter. The parts are then stored in the chunks array.

Base64.Decoder decoder = Base64.getUrlDecoder();

Creates a Base64 decoder instance for decoding the Base64-encoded payload.

String payload = new String(decoder.decode(chunks[1]));

Decodes the Base64-encoded payload and stores it in the payload variable.

String[] entries = payload.split(",");

Splits the payload into individual key-value pairs using a comma (',') as a delimiter and stores them in the entries array.

Map<String, String> map = new HashMap<String, String>();

Creates a HashMap to store the key-value pairs extracted from the payload.

for (String entry : entries) {
            String[] keyValue = entry.split(":");
- Iterates through each entry in the entries array and splits it into a key-value pair using a colon (':') as a delimiter.

            if (keyValue[0].equals(extraction)) {

                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }
- Checks if the key of the current entry (keyValue[0]) matches the specified extraction key.

- If there's a match, processes the value to remove unnecessary characters and puts the key-value pair into the map.

        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
Finally, checks if the map contains the specified extraction key and returns its corresponding value if found; otherwise, returns null.
* */
