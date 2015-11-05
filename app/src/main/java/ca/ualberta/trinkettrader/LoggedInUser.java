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
import java.util.ArrayList;
import java.util.HashMap;

public class LoggedInUser extends User {

    private static String FILENAME = "profile.sav";
    private static LoggedInUser ourInstance = new LoggedInUser();
    private boolean needToSave;

    public static LoggedInUser getInstance() {
        return ourInstance;
    }

    private LoggedInUser() {
    }
    public void saveInFile(Context c) {
        /**
         * saves LoggedInUser Data to file
         */
        if(!needToSave){
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
            //https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type dataCollectionType = new TypeToken<HashMap<String, ArrayList<Long>>>() {}.getType();
            Gson gson = new Gson();
            ourInstance = gson.fromJson(in, dataCollectionType);

        } catch (FileNotFoundException e) {
            //TODO: Assumption: LoggedInUser instantiated upon login, no file == no previous data
            //TODO: Therefore do nothing
        }
    }
}
