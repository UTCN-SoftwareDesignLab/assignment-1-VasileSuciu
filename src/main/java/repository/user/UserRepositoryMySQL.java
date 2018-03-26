package repository.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        try {
            PreparedStatement selectUserStatement = connection.
                    prepareStatement("SELECT * FROM user ");
            ResultSet userResultSet = selectUserStatement.executeQuery();
            while (userResultSet.next()) {
                User user = new UserBuilder()
                        .setPassword(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("user_id")))
                        .build();
                users.add(user);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("user_id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException("Server communication error");
        }
    }

    @Override
    public User findById(Long id) {
        try {
            PreparedStatement selectUserStatement = connection.
                    prepareStatement("SELECT * FROM user WHERE `user_id` = ?");
            selectUserStatement.setLong(1,id);
            ResultSet userResultSet = selectUserStatement.executeQuery();
            if (userResultSet.next()){
                User user = new UserBuilder()
                        .setPassword(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("user_id")))
                        .build();
                return user;
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        try {
            PreparedStatement selectUserStatement = connection.
                    prepareStatement("SELECT * FROM user WHERE username = ?");
            selectUserStatement.setString(1,username);
            ResultSet userResultSet = selectUserStatement.executeQuery();
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setPassword(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("user_id")))
                        .build();
                return user;
            }
            else {
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try{
            List<Role> roles = rightsRolesRepository.findRolesForUser(user.getId());

            PreparedStatement updateUser = connection.prepareStatement(
                    "UPDATE user SET  username = ?, password = ? WHERE user_id = ?");
            updateUser.setString(1,user.getUsername());
            updateUser.setString(2,user.getPassword());
            updateUser.setLong(3,user.getId());
            updateUser.executeUpdate();

            List<Role> newRoles = new ArrayList<Role>(user.getRoles());
            newRoles.removeAll(roles);

            rightsRolesRepository.addRolesToUser(user, newRoles);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)");
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeUser(User user){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user WHERE user_id = "+user.getId();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
