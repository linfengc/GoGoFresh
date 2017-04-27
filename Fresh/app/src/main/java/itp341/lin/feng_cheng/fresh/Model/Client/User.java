package itp341.lin.feng_cheng.fresh.Model.Client;

import java.util.ArrayList;

/**
 * Created by fredlin on 4/1/17.
 */

public class User {
    private String username;
    private String password;
    private boolean premiumUser;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isPremiumUser() {

        return premiumUser;
    }

    public void setPremiumUser(boolean premiumUser) {
        this.premiumUser = premiumUser;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, boolean premiumUser, String password) {

        this.username = username;
        this.premiumUser = premiumUser;
        this.password = password;
    }
}
