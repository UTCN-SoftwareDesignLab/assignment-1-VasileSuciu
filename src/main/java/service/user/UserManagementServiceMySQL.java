package service.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.User;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.List;

public class UserManagementServiceMySQL implements UserManagementService {
    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public UserManagementServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> updateUser(User user, Boolean passwordChanged) {
        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
            return userNotification;
        }
        if (passwordChanged){
            user.setPassword(encodePassword(user.getPassword()));
        }
        userNotification.setResult(userRepository.updateUser(user));
        return userNotification;
    }

    @Override
    public void deleteUser(String user) {
        userRepository.removeUser(userRepository.findByUsername(user));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
