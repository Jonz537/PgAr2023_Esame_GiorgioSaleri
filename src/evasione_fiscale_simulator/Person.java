package evasione_fiscale_simulator;

import utils_bs.Sex;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Person {

    private String name;
    private String surname;
    private Calendar birthdate; //gaySpawned
    private Sex sex;
    private String birthCity;
    private TaxIdCode taxIdCode;
    private Calendar expireDate;

    public Person() {
    }

    public Person(ArrayList<String> personData) {
        this.name = personData.get(0);
        this.surname = personData.get(1);
        this.sex = Sex.valueOf(personData.get(2));
        this.birthCity = personData.get(3);

        String[] date = personData.get(4).split("-");
        this.birthdate = new GregorianCalendar(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]) - 1,
                Integer.parseInt(date[2]));
        this.taxIdCode = new TaxIdCode(surname, name, birthdate, sex, birthCity);
    }

    public Person(String name, String surname, Calendar birthdate, Sex sex, String birthCity) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.sex = sex;
        this.birthCity = birthCity;
        this.taxIdCode = new TaxIdCode(surname, name, birthdate, sex, birthCity);
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public Sex getSex() {
        return sex;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public TaxIdCode getTaxIdCode() {
        return taxIdCode;
    }

    public String getStringDate() {
        return birthdate.get(Calendar.YEAR)+ "-" + String.format("%02d", (birthdate.get(Calendar.MONTH) + 1)) + "-" + birthdate.get(Calendar.DAY_OF_MONTH);
    }

    public boolean validTaxId() {
        return taxIdCode.isValid();
    }

    public Calendar getExpireDate() {
        return expireDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthdate(String birthdate) {
        String[] date = birthdate.split("-");
        this.birthdate = new GregorianCalendar(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]) - 1,
                Integer.parseInt(date[2]));
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public void setTaxIdCode(TaxIdCode taxIdCode) {
        this.taxIdCode = taxIdCode;
    }

    public void setExpireDate(String expireDate) {
        String[] date = expireDate.split("-");
        this.expireDate = new GregorianCalendar(Integer.parseInt(date[0]),
                Integer.parseInt(date[1]) - 1,
                Integer.parseInt(date[2]));
    }

    @Override
    public String toString() {
        return "main.Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthdate.get(Calendar.WEEK_OF_YEAR) + "/" + birthdate.get(Calendar.MONTH) +
                "/" + birthdate.get(Calendar.YEAR) +
                ", sex=" + sex +
                ", city='" + birthCity + '\'' +
                ", taxIdCode='" + taxIdCode + '\'' +
                '}';
    }
}
