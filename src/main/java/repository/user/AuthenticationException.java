package repository.user;

public class AuthenticationException extends Exception {

    public AuthenticationException(){super();}

    public AuthenticationException(String message){
        super(message);
    }

}
