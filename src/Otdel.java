public class Otdel {
    
    // поле ИД отдел
    private int otdelID;
    // поле ИМЯ ГРУППЫ
    private String nameOtdel;
    // поле Boss
    private String boss;
    // поле СПЕЦИАЛЬНОСТЬ
    private String speciality;  
    
    // get/set для Boss
    public String getBoss() {
        return boss;
    }
    public void setBoss(String boss) {
        this.boss = boss;
    }

    // get/set для ИД отдела
    public int getOtdelID() {
        return otdelID;
    }
    public void setOtdelID(int OtdelID) {
        this.otdelID = otdelID;
    }
    
        // get/set для ИМЯ отдела
    public String getNameOtdel() {
        return nameOtdel;
    }
    public void setNameOtdel(String nameOtdel) {
        this.nameOtdel = nameOtdel;
    }

    // get/set для СПЕЦИАЛЬНОСТЬ
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String toString() {
        return nameOtdel;
    }
}
