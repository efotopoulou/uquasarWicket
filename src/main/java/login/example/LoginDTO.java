package login.example;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: eleni
 * Date: 10/11/13
 * Time: 3:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class LoginDTO implements Serializable {
    private String username;
    private String password;

    public LoginDTO() {

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
}