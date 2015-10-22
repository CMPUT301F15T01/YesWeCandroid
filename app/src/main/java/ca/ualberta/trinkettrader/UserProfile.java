package ca.ualberta.trinkettrader;

/**
 * Created by dashley on 2015-10-21.
 */
public class UserProfile {

    private ContactInfo contactInfo;
    private String city;
    private String name;

    public UserProfile() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
