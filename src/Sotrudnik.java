

/**
 *
 * @author a.belovol
 */

import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Sotrudnik implements Comparable {
    // ID сотрудника.
    private int personID;
    // Имя сотрудника
    private String personName;
    // Фамилия сотрудника
    private String personSurName;
    // Отчество сотрудника
    private String personPatronymicName;
    
    // поле ПОЛ
    private char sex;
    
    // поле ИД Отдела
    private int otdelID;
    
        // поле ДАТА РОЖДЕНИЯ
    private Date dateOfBirth;
    
    /* Лишнее ... */
    // поле ГОД ОБУЧЕНИЯ
    private int educationYear;
    // get/set для ГОД ОБУЧЕНИЯ
    public int getEducationYear() {
        return educationYear;
    }
        public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }
    
    // get/set для ДАТА РОЖДЕНИЯ
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    // get/set для ИД Отдела
    public int getOtdelID() {
        return otdelID;
    }
    public void setOtdelID(int otdelID) {
        this.otdelID = otdelID;
    }
    
    // get/set для ИД сотрудника
    public int getPersonID() {
        return personID;
    }
    public void setPersonID(int personID) {
        this.personID = personID;
    }
    
    // get/set для ИМЯ
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    
    // get/set для ОТЧЕСТВО
    public String getPersonPatronymicName() {
        return personPatronymicName;
    }
    public void setPersonPatronymicName(String personPatronymicName) {
        this.personPatronymicName = personPatronymicName;
    }
    
    // get/set для ФАМИЛИЯ
    public String getPersonSurName() {
        return personSurName;
    }
    public void setPersonSurName(String personSurName) {
        this.personSurName = personSurName;
    }
    
    // get/set для ПОЛ
    public char getSex() {
        return sex;
    }
    public void setSex(char sex) {
        this.sex = sex;
    }
    
    
    
    // DateFormat - класс для преобразования даты
    // в строку в определеннном формате.
    // Подробнее смотрите документацию по этому методу
    public String toString() {
        return personName + " " + personSurName + " " + personPatronymicName + ", "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth)
                + ", ИД Отдела=" + otdelID + " Год:" + educationYear;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
}
