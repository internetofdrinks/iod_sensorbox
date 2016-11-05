package internet.of.drinks.user;

/**
 * Created by Martin on 05.11.2016.
 */
public class User {
    private String firstname, lastname;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
