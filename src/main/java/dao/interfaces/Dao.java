package dao.interfaces;

import common.Exceptional;
import common.ExceptionalFunction;
import common.Private;

import java.sql.*;

@FunctionalInterface
interface Dao {

    Connection getConnection() throws SQLException;

    @Private
    default <T> Exceptional<T, SQLException> withConnection(
            ExceptionalFunction<Connection, T, SQLException> jdbcTemplate) {
        try (final Connection connection = getConnection()) {
            return jdbcTemplate.apply(connection);
        } catch (SQLException e) {
            return Exceptional.withException(e);
        }
    }

    @Private
    default <T> Exceptional<T, SQLException> withStatement(
            ExceptionalFunction<Statement, T, SQLException> jdbcTemplate) {
        return withConnection(connection -> {
            try (final Statement statement = connection.createStatement()) {
                return jdbcTemplate.get(statement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> withPreparedStatement(
            ExceptionalFunction<PreparedStatement, T, SQLException> jdbcTemplate, String sql) {
        return withConnection(connection -> {
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                return jdbcTemplate.get(statement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> withCallableStatement(
            ExceptionalFunction<CallableStatement, T, SQLException> jdbcTemplate, String call) {
        return withConnection(connection -> {
            try (final CallableStatement callableStatement = connection.prepareCall(call)) {
                return jdbcTemplate.get(callableStatement);
            }
        });
    }

    @Private
    default <T> Exceptional<T, SQLException> executeQuery(String sql,
                                                          ExceptionalFunction<ResultSet, T, SQLException> template) {
        return withStatement(statement -> {
            try (final ResultSet rs = statement.executeQuery(sql)) {
                return template.get(rs);
            }
        });
    }
}
