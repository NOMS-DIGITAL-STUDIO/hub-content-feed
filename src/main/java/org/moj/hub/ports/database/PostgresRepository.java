package org.moj.hub.ports.database;

import org.moj.hub.domain.Article;
import org.moj.hub.domain.MetadataRepository;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.UUID;

public class PostgresRepository implements MetadataRepository {

    private final Connection connection;

    public PostgresRepository() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        connection = DriverManager.getConnection(dbUrl);
    }

    @Override
    public Article getArticleForId(UUID uuid) {

        Statement stmt;
        try {
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT title FROM hub_metadata WHERE uuid = '" + uuid.toString() + "'");
            if (resultSet.next()) {
                return new Article(resultSet.getString(1));
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
