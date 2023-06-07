package evasione_fiscale_simulator;

import main.Main;
import utils_bs.Sex;
import world_stuff.TaxWorld;

import java.util.*;

public class TaxIdCode {

    private String code;

    private final static HashMap<Character, Integer> monthMap = new HashMap<>() {{
        put('B', 28);
        put('D', 30);
        put('H', 30);
        put('P', 30);
        put('S', 30);
    }};
    private final static HashMap<Character, Integer> oddCharacters = new HashMap<>() {{
        put('0', 1);
        put('1', 0);
        put('2', 5);
        put('3', 7);
        put('4', 9);
        put('5', 13);
        put('6', 15);
        put('7', 17);
        put('8', 19);
        put('9', 21);
        put('A', 1);
        put('B', 0);
        put('C', 5);
        put('D', 7);
        put('E', 9);
        put('F', 13);
        put('G', 15);
        put('H', 17);
        put('I', 19);
        put('J', 21);
        put('K', 2);
        put('L', 4);
        put('M', 18);
        put('N', 20);
        put('O', 11);
        put('P', 3);
        put('Q', 6);
        put('R', 8);
        put('S', 12);
        put('T', 14);
        put('U', 16);
        put('V', 10);
        put('W', 22);
        put('X', 25);
        put('Y', 24);
        put('Z', 23);
    }};
    private final static ArrayList<Character> monthMapReverse = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'));

    public TaxIdCode(String code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Generate Tax Id Code
     * @param name string
     * @param surname string
     * @param date calendar date with birthday
     * @param sex Enum class
     * @param city name of birthplace
     */
    public TaxIdCode(String surname, String name, Calendar date, Sex sex, String city) {
        StringBuilder generatedCode = new StringBuilder();
        // Adding name and surname chars
        generatedCode.append(surnameChar(surname));
        generatedCode.append(nameChar(name));

        // Adding birth year and month
        generatedCode.append(String.format("%02d", date.get(Calendar.YEAR) % 100));
        generatedCode.append(monthMapReverse.get(date.get(Calendar.MONTH)));

        // Adding birthday and sex
        if (sex.equals(Sex.M)) {
            generatedCode.append(date.get(Calendar.DAY_OF_MONTH));
        } else {
            generatedCode.append(date.get(Calendar.DAY_OF_MONTH) + 40);
        }
        // Adding cities code
        if (TaxWorld.getCitiesByName(city) == null) {
            return;
        }
        generatedCode.append(TaxWorld.getCitiesByName(city));
        generatedCode.append(checkChar(generatedCode.toString()));
        this.code = generatedCode.toString();
    }



    private String vowel(StringBuilder characters, String name) {
        // Adding vowels and returning if length > 3
        for (int i = 0; i < name.length(); i++) {
            if (name.substring(i, i + 1).matches("[AEIOU]")) {
                characters.append(name.charAt(i));
            }
            if (characters.length() > 2) {
                return characters.toString();
            }
        }

        // Adding "X"s if initial string isn't long enough
        while(characters.length() < 3) {
            characters.append("X");
        }

        return characters.toString();
    }

    /**
     * Give the first 3 characters for the tax ID Code
     * @param surname string with either name or surname
     * @return The generation of the name/surname of taxIdCode
     */
    private String surnameChar(String surname) {
        StringBuilder characters = new StringBuilder();
        surname = surname.toUpperCase();

        // Adding consonants and returning if length > 3
        for (int i = 0; i < surname.length(); i++) {
            if (surname.substring(i, i + 1).matches("[A-Z&&[^AEIOU]]")) {
                characters.append(surname.charAt(i));
            }
            if (characters.length() > 2) {
                return characters.toString();
            }
        }

        return vowel(characters, surname);
    }

    private String nameChar(String name) {
        StringBuilder characters = new StringBuilder();
        name = name.toUpperCase();

        // Adding consonants and returning if length > 3
        for (int i = 0; i < name.length(); i++) {
            if (name.substring(i, i + 1).matches("[A-Z&&[^AEIOU]]")) {
                characters.append(name.charAt(i));
            }
            if (characters.length() > 3) {
                return characters.charAt(0) + characters.substring(2,4);
            }
        }

        if (characters.length() > 2) {
            return characters.toString();
        }

        return vowel(characters, name);
    }


    /**
     * Generate final check character
     * @param generatedCode first 15 chars in Tax id code
     * @return check character
     */
    public char checkChar(String generatedCode) {
        int sum = 0;

        for (int i = 0; i < generatedCode.length(); i++) {
            // Check if digit is at and even (not index, normal people start counting from one)
            if (i % 2 == 1) {
                sum += ((generatedCode.substring(i, i + 1).matches("[0-9]+"))?
                        Character.getNumericValue(generatedCode.charAt(i)) :
                        ((int)generatedCode.charAt(i)) % 65);
            } else {
                sum += oddCharacters.get(generatedCode.charAt(i));
            }
        }

        return (char)(65 + (sum % 26));
    }

    /**
     * Check function for validity of a Tax ID Code
     * @return true if the Tax ID code is valid
     */
    public boolean isValid() {

        // Check characters in right positions and month characters
        if (!(code.substring(0, 6).matches("[A-Z]+") && code.substring(8, 9).matches("[ABCDEHLMPRST]") &&
                code.substring(11, 12).matches("[A-Z]+") && code.substring(15, 16).matches("[A-Z]+"))) {
            return false;
        }
        // Check digits in right positions
        if (!(code.substring(6, 8).matches("[0-9]+") && code.substring(9, 11).matches("[0-9]+") &&
                code.substring(12, 15).matches("[0-9]+"))) {
            return false;
        }

        // Check date
        int birthDay = Integer.parseInt(code.substring(9, 11)) % 40;
        if (birthDay < 1 || birthDay > monthMap.getOrDefault(code.charAt(8), 31)) {
           return false;
        }

        // Check last characters
        if (!(code.charAt(15) == checkChar(code.substring(0, 15)))) {
            return false;
        }
        // Return if valid
        return (isValidName(code.substring(0, 3)) || isValidName(code.substring(3, 6)));
    }

    private boolean isValidName(String threeChar) {

        boolean consonantFinished = false, vowelFinished = false;
        for (int i = 0; i < 3; i++) {
            // check if consonant are finished
            if ((!consonantFinished && threeChar.substring(i, i + 1).matches("[AEIOU]"))) {
                consonantFinished = true;
            }
            // check if vowels are finished
            if ((!vowelFinished && consonantFinished && threeChar.charAt(i) == 'X')) {
                vowelFinished = true;
            }

            // if consonant are finished and if we find another consonant (exp: x) the code is invalid
            if (consonantFinished && threeChar.substring(i, i + 1).matches("[A-Z&&[^AEIOUX]]")) {
                return false;
            }

            // if vowel are finished, and we find another vowel the code is invalid
            if (vowelFinished && threeChar.substring(i, i + 1).matches("[AEIOU]")) {
                return false;
            }
        }
        return true;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxIdCode taxIdCode = (TaxIdCode) o;
        return Objects.equals(code, taxIdCode.code);
    }

    @Override
    public String toString() {
        return "main.TaxIdCode{" +
                "code='" + code + '\'' +
                '}';
    }
}
