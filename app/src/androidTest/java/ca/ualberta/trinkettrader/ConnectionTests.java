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

import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

public class ConnectionTests extends ActivityInstrumentationTestCase2 {
    public ConnectionTests() {
        super(LoginActivity.class);
    }

    // turn wifi off, check
    // turn wifi on, check
    public void testHasConnection() {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        // viv; http://stackoverflow.com/questions/3930990/android-how-to-enable-disable-wifi-or-internet-connection-programmatically; 2015-11-18
        WifiManager wifiManager = (WifiManager) loginActivity.getSystemService(LoginActivity.WIFI_SERVICE);
        wifiManager.setWifiEnabled(Boolean.FALSE); // turn off
        //assertTrue(InternetConnection.getInstance().internetConnectionAvailable(loginActivity)==Boolean.FALSE);
        // TODO disable wifi
        wifiManager.setWifiEnabled(Boolean.TRUE); // turn on
        assertTrue(InternetConnection.getInstance().internetConnectionAvailable(loginActivity));

        // finish activity
        loginActivity.finish();
    }
}
