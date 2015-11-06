// Copyright 2015 Andrea McIntosh, Dylan Ashley, Anju Eappen, Jenna Hatchard, Kirsten Svidal, Raghav Vamaraju
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

    /**
     * Returns name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets address.
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets city.
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets phone number.
     * @return String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.needToSave = Boolean.TRUE;
    }

    /**
     * Gets postal code.
     * @return String
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.needToSave = Boolean.TRUE;
    }
}
