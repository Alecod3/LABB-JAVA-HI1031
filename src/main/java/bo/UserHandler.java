package bo;

import java.sql.SQLException;
import db.UserDB;
import ui.UserInfo;

public class UserHandler {

    private final UserDB userDB = new UserDB();

    public UserInfo login(String username, String password) {
        if (username == null || username.trim().isEmpty() || username.trim().length() > 50
                || password == null || password.isEmpty() || password.length() > 1024) {
            return null;
        }
        try {
            User user = userDB.findByCredentials(username.trim(), password);
            if (user == null) {
                return null;
            }
            return new UserInfo(user.getId(), user.getUsername());
        } catch (SQLException e) {
            throw new IllegalStateException("Kunde inte kontrollera inloggningen.", e);
        }
    }
}
