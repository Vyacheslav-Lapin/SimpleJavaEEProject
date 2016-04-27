package dao.mySql;

import dao.interfaces.InstanceDao;
import model.Gun;
import model.Instance;

import javax.sql.DataSource;
import java.util.Optional;

@FunctionalInterface
public interface MySqlInstanceDao extends InstanceDao {

    @Override
    default Optional<Instance> getInstanceById(int id) {
        return executeQuery(
                "SELECT g.id, name, caliber " +
                        "FROM Gun g, Instance i " +
                        "WHERE model_id = g.id AND  i.id = " + id,
                rs -> !rs.next() ? null :
                        new Instance(id,
                                new Gun(rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getDouble("caliber")))
        ).toOptional();
    }

    static MySqlGunDao from(DataSource dataSource) {
        return dataSource::getConnection;
    }
}
