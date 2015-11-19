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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A singleton class.
 * When asked, this class will let the asking activity know if an internet connection is available.
 */
public class InternetConnection {
    private static InternetConnection ourInstance = new InternetConnection();
    /**
     * Public Constructor
     */
    private InternetConnection(){
    }

    /**
     * Returns instance of InternetConnection class.
     * @return InternetConnection Single instance of InternetConnection class
     */
    public static InternetConnection  getInstance(){ return ourInstance; }

    /**
     * Method used by classes to determine if there is an internet connection available.
     * When calling this method, pass in current activity.
     * @param context Current activity
     * @return Boolean True is internet connection is available, false if unavailable
     */
    public Boolean internetConnectionAvailable(Context context){
        // Android Developers, Accessed on 2015-11-18, http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
