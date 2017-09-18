package application.services;

import application.db.UserDB;

import application.models.User;
import application.utils.requests.SignupRequest;
import org.springframework.stereotype.Service;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class AccountService {
    private static UserDB db;
    private final PasswordEncoder encoder;

    public AccountService() {
        db = new UserDB();
        encoder = new BCryptPasswordEncoder();
    }

    public long addUser(SignupRequest user) {
        final String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return db.addUser(user);
    }

    public void changeUserData(User user) {
        db.changeUserData(user);
    }

    public User getUser(long id) {
        return db.getUser(id);
    }

    @Nullable
    public Long getId(String login) {
        return db.getId(login);
    }

    public boolean checkSignup(String login, String email) {
        return !(db.hasLogin(login) || db.hasEmail(email));
    }

    public boolean checkSignin(long id, String password) {
        return encoder.matches(password, db.getUser(id).getPassword());
    }

}
