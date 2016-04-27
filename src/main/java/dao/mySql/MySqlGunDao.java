package dao.mySql;

import dao.interfaces.GunDao;
import model.Gun;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@FunctionalInterface
public interface MySqlGunDao extends GunDao {

    @Override
    default Optional<Gun> getGunById(int id) {
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next() ? new Gun(id, rs.getString("name"), rs.getDouble("caliber")) : null
        ).toOptional();
    }

    @Override
    default Collection<Gun> getAllGuns() {
        return executeQuery(
                "SELECT id, name, caliber FROM Gun",
                rs -> {
                    Collection<Gun> guns = new HashSet<>();
                    while (rs.next())
                        guns.add(new Gun(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("caliber")));
                    return guns;
                }).toOptional().orElseGet(Collections::emptySet);
    }

    static MySqlGunDao from(DataSource dataSource) {
        return dataSource::getConnection;
    }
}
