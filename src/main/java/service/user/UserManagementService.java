package service.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserManagementService {

    Notification<Boolean> updateUser(User user, Boolean passwordChanged);

    void deleteUser(String user);

    List<User> getAllUsers();
}
