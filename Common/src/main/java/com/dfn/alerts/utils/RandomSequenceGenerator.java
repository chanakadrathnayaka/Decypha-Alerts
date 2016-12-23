package com.dfn.alerts.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal
 * Date: 9/24/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomSequenceGenerator {

    private static final char[] CHARSET_AZ_09 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final char[] CHARSET_HEX = "0123456789ABCDEF".toCharArray();
    private static final int SEQUENCE_LENGTH = 32;
    private static final int SEQUENCE_LENGTH_PASSWORD = 7;

    private RandomSequenceGenerator() {
    }

    /**
     * Method to generate random sequence using a given character sequence
     *
     * @param characterSet input character set
     * @param length       length of required sequence
     * @return random sequence
     */
    public static String randomString(char[] characterSet, int length) {
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    public static synchronized String generateRandomSequence(int length) {
        return randomString(CHARSET_AZ_09, length);
    }

    public static synchronized String generateRandomSequence() {
        return generateRandomSequence(SEQUENCE_LENGTH);
    }

    public static synchronized String generateHexRandomSequence(int length) {
        return randomString(CHARSET_HEX, length);
    }
    public static synchronized String generateRandomPassword() {
        return generateRandomSequence(SEQUENCE_LENGTH_PASSWORD);
    }
}
