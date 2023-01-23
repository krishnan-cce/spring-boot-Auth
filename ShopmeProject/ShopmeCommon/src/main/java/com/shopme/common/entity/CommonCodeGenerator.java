package com.shopme.common.entity;

public class CommonCodeGenerator {
	
    public static String generateUserCode(String name, String Code, Long id) {
        name = name == null ? "" : name;
        String firstThreeLetters = name.substring(0, Math.min(3, name.length())).toUpperCase();
        System.out.println("Generated Code : " + firstThreeLetters + Code + id);
        return firstThreeLetters + Code + id;
    }

}
