package ca.ualberta.trinkettrader;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class LoggedInUser extends User {

    private static String FILENAME = "profile.sav";
    private static LoggedInUser ourInstance = new LoggedInUser();

    public static LoggedInUser getInstance() {
        return ourInstance;
    }

    private LoggedInUser() {
    }
    public void saveInFile(Context c) {
        /**
         * saves LoggedInUser Data to file
         */
        if(this.getNeedToSave()){
            return;
        }
        try {
            FileOutputStream fos = c.openFileOutput(FILENAME, 0);
            BufferedWriter out =  new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(ourInstance, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        needToSave = Boolean.FALSE;
    }

    public void loadFromFile(Context c) {
        /**
         * Loads LoggedInUser data from file
         */
        try {

            FileInputStream fis = c.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/dcom/google/gson/Gson.html, 2015-09-23
            Type loggedInUserType = new TypeToken<LoggedInUser>(){}.getType();
            Gson gson = new Gson();
            ourInstance = gson.fromJson(in, loggedInUserType);

        } catch (FileNotFoundException e) {
            //TODO: Assumption: LoggedInUser instantiated upon login, no file == no previous data
            //TODO: Therefore do nothing
        }
        this.setNeedToSave(Boolean.FALSE);
    }
}
