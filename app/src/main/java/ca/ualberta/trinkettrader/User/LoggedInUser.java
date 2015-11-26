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

package ca.ualberta.trinkettrader.User;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.trinkettrader.ElasticStorable;

public class LoggedInUser extends User {
    /**
     * A singleton class that stores the current instance of the User class that has logged into the
     * current instance of the application.
     */

    private static String FILENAME = "profile.sav";
    private static LoggedInUser ourInstance = new LoggedInUser();

    private LoggedInUser() {
    }

    /**
     * Pass in the Context to cache the any changes to the current user's data. This method check's
     * whether there are any changes to LoggedInUser that need to be saved before doing so.
     *
     * @param c
     */
    // joshua2ua; https://github.com/joshua2ua/lonelyTwitter; 2015-11-26
    public void saveInFile(Context c) {
        if (this.getNeedToSave()) {
            return;
        }
        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(ourInstance, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        needToSave = Boolean.FALSE;
    }

    /**
     * Pass in the context to load the cached data of the user of the current application instance
     * into the LoggedInUser singleton class.
     *
     * @param c
     */
    // joshua2ua; https://github.com/joshua2ua/lonelyTwitter; 2015-11-26
    public void loadFromFile(Context c) {
        try {

            FileInputStream fis = c.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/dcom/google/gson/Gson.html; 2015-09-23
            Type loggedInUserType = new TypeToken<LoggedInUser>() {
            }.getType();
            Gson gson = new Gson();
            ourInstance = gson.fromJson(in, loggedInUserType);

        } catch (FileNotFoundException e) {
            //TODO: Assumption: LoggedInUser instantiated upon login, no file == no previous data
            //TODO: Therefore do nothing
        }
        this.setNeedToSave(Boolean.FALSE);
    }

    public void loadFromNetwork(String email) throws NoSuchFieldException {

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        ArrayList<NameValuePair> userData = new ArrayList<>();
        NameValuePair n = new BasicNameValuePair("email", email);
        userData.add(n);

        ArrayList<ElasticStorable> foundUsers = null;
        try {
            foundUsers = LoggedInUser.getInstance().searchOnNetwork(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (foundUsers.size() == 0) {
            ourInstance.getProfile().setEmail(email);
            ourInstance.saveToNetwork();
        } else if (foundUsers.size() == 1) {
            ourInstance = (LoggedInUser) foundUsers.get(0);
        } else {
            throw new NoSuchFieldException();
        }

    }

    /**
     * Returns the current (and single) instance of logged in user.
     * To be called when any changes are made to user's profile, i.e. adding/deleting a friend or
     * making a trade.
     *
     * @return LoggedInUser
     */
    public static LoggedInUser getInstance() {
        return ourInstance;
    }
}
