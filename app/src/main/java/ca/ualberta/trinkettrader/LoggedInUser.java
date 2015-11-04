package ca.ualberta.trinkettrader;

public class LoggedInUser extends User {

    private static LoggedInUser ourInstance = new LoggedInUser();

    public static LoggedInUser getInstance() {
        return ourInstance;
    }

    private LoggedInUser() {
    }
}
