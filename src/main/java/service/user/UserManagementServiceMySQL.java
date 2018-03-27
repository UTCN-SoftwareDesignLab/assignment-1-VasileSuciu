package service.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import model.User;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagementServiceMySQL implements UserManagementService {
    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public UserManagementServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> updateUser(String username, String password, List<String> roles) {
        User user = userRepository.findByUsername(username);
        user.setRoles(roles.stream().map(rightsRolesRepository::findRoleByTitle).collect(Collectors.toList()));
        boolean passwordChanged = !user.getPassword().equals(password);
        if (passwordChanged){
            user.setPassword(password);
        }
        UserValidator userValidator = new UserValidator(user);
        boolean userValid;
        if (passwordChanged){
            userValid = userValidator.validate();
        }
        else {
            userValid = userValidator.validateExceptPassword();
        }
        Notification<Boolean> userNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
            return userNotification;
        }
        else {
            userNotification.setResult(userRepository.updateUser(user));
        }
        return userNotification;
    }

    @Override
    public User getUser(String user) {
        return userRepository.findByUsername(user);
    }

    @Override
    public boolean deleteUser(String user) {
        return userRepository.removeUser(userRepository.findByUsername(user));
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
