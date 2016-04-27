package dao.mySql;

import dao.interfaces.PersonDao;
import model.Person;

import java.util.Optional;

public interface MySqlPersonDao extends PersonDao {

    @Override
    default Optional<Person> getPersonById(int id) {
        return executeQuery(
                "SELECT first_name, last_name, permission, dob, email, password, address, telephone" +
                        " FROM Person WHERE id = " + id,
                rs -> !rs.next() ? null :
                        new Person(id,
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getBoolean("permission"),
                                rs.getDate("dob").toLocalDate(),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("address"),
                                rs.getString("telephone"))).toOptional();
    }

    @Override
    default Optional<String> getPasswordByEmail(String email) {
        return executeQuery(
                "SELECT password FROM Person WHERE email = '" + email + "'",
                rs -> rs.next() ? rs.getString("password") : null
        ).toOptional();
    }

    @Override
    default boolean setPasswordByEmail(String email, String password) {
        return withStatement(statement ->
                1 <= statement.executeUpdate(
                        "UPDATE Person SET password = '" + password + "' WHERE email = '" + email + "'")
        ).toOptional().orElse(false);
    }

    @Override
    default Optional<Person> getPersonByEmail(String email) {
        return executeQuery(
                "SELECT id, first_name, last_name, permission, dob, password, address, telephone" +
                        " FROM Person WHERE email = '" + email + "'",
                rs -> !rs.next() ? null :
                        new Person(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getBoolean("permission"),
                                rs.getDate("dob").toLocalDate(),
                                email,
                                rs.getString("password"),
                                rs.getString("address"),
                                rs.getString("telephone"))
        ).toOptional();
    }
}
