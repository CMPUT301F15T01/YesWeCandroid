package ca.ualberta.trinkettrader;

/**
 * Created by dashley on 2015-10-21.
 */
public class ContactInfo {

    private String address;
    private String phoneNumber;
    private String postalCode;

    public ContactInfo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
