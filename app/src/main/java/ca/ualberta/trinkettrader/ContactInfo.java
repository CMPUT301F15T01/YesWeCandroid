package ca.ualberta.trinkettrader;

public class ContactInfo {

    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private String postalCode;

    public Boolean getNeedToSave() {
        return this.needToSave;
    }

    private Boolean needToSave;

    public ContactInfo() {
        this.name = "";
        this.address = "";
        this.city = "";
        this.phoneNumber = "";
        this.postalCode = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.needToSave = Boolean.TRUE;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.needToSave = Boolean.TRUE;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.needToSave = Boolean.TRUE;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.needToSave = Boolean.TRUE;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.needToSave = Boolean.TRUE;
    }
}
