package com.example.lowpricepharmacy.services;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Service for url manipulation
 *
 * @version 1.0
 */
@Service
@Log
public class URLService {
    private HashMap<String, String> keyMap; // key-url map
    private HashMap<String, String> valueMap;// url-key map to quickly check
    // whether an url is
    // already entered in our system
    private String domain; // Use this attribute to generate urls for a custom
    // domain name defaults to http://localhost:8080
    private char myChars[]; // This array is used for character to number
    // mapping
    private Random myRand; // Random object used to generate random integers
    private int keyLength; // the key length in URL defaults to 8
    private String protocolName;

    /**
     * Constructor without params
     */
    URLService() {
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
        myRand = new Random();
        keyLength = 21;
        myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j = 0;
            if (i < 10) {
                j = i + 48;
            } else if (i > 9 && i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            myChars[i] = (char) j;
        }

        domain = "http://localhost:8080";
    }


    /**
     * Constructor with params
     *
     * @param length    key length
     * @param newDomain new domain
     */
    URLService(int length, String newDomain) {
        this();
        this.keyLength = length;
        if (!newDomain.isEmpty()) {
            newDomain = sanitizeURL(newDomain);
            domain = newDomain;
        }
    }

    /**
     * Shortens url
     *
     * @param longURL original url
     * @return new short url
     */
    public String shortenURL(String longURL) {
        String shortURL = "";
        longURL = sanitizeURL(longURL);
        if (valueMap.containsKey(longURL)) {
            shortURL = domain + "/" + valueMap.get(longURL);
        } else {
            shortURL = domain + "/" + getKey(longURL);
        }
        log.info("Short URL:" + shortURL);

        return shortURL;
    }

    /**
     * This method should take care various issues with a valid url
     * e.g. www.google.com,www.google.com/, http://www.google.com,
     * http://www.google.com/
     * all the above URL should point to same shortened URL
     * There could be several other cases like these.``
     *
     * @param url
     * @return
     */
    String sanitizeURL(String url) {
        if (url.startsWith("http://")) {
            setProtocolName("http://");
            url = url.substring(7);
        }

        if (url.startsWith("https://")) {
            setProtocolName("https://");
            url = url.substring(8);
        }

        if (url.charAt(url.length() - 1) == '/') {
            url = url.substring(0, url.length() - 1);
        }

        return url;
    }

    /**
     * Get Key method
     */
    private String getKey(String longURL) {
        String key;
        key = generateKey();
        keyMap.put(key, protocolName + longURL);
        valueMap.put(protocolName + longURL, key);

        return key;
    }

    /**
     * GenerateKey
     *
     * @return key
     */
    private String generateKey() {
        StringBuilder key = new StringBuilder();
        boolean flag = true;
        while (flag) {
            key = new StringBuilder();
            for (int i = 0; i <= keyLength; i++) {
                key.append(myChars[myRand.nextInt(62)]);
            }

            if (!keyMap.containsKey(key.toString())) {
                flag = false;
            }
        }

        return key.toString();
    }

    public String searchLongUrlInMap(String shortUrl) {
        for (Map.Entry<String, String> entry : keyMap.entrySet()) {
            if (shortUrl.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return "";
    }


    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

}
