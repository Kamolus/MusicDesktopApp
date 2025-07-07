package model;

import java.time.LocalDate;

public class MusicianScout extends Employee implements IScout {

    private String phoneNumber;
    public MusicianScout(String name, String email, LocalDate hireDate, double salary, String phoneNumber) {
        super(name, email, hireDate, salary);
        try{
            setPhoneNumber(phoneNumber);
        }catch(Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            this.phoneNumber = phoneNumber;
        }
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}