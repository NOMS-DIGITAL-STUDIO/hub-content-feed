package org.moj.hub;

import com.google.gson.Gson;
import org.moj.hub.domain.MetadataRepository;
import org.moj.hub.ports.database.PostgresRepository;
import org.moj.hub.ports.http.GetMetadataRoute;

import java.net.URISyntaxException;
import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws URISyntaxException, SQLException {
        port(Integer.valueOf(System.getProperty("PORT")));

        get("/hello", (req, res) -> "Hello World");

        Gson gson = new Gson();
        MetadataRepository metadataRepository = new PostgresRepository();
        get("/articles/:id", new GetMetadataRoute(metadataRepository), gson::toJson);
    }
}
