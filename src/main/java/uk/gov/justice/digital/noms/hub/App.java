package uk.gov.justice.digital.noms.hub;

import com.google.gson.Gson;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;
import uk.gov.justice.digital.noms.hub.ports.database.PostgresRepository;
import uk.gov.justice.digital.noms.hub.ports.http.GetMetadataRoute;
import spark.Spark;

import java.net.URISyntaxException;
import java.sql.SQLException;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws URISyntaxException, SQLException {
        port(Integer.valueOf(System.getProperty("PORT")));

        get("/hello", (req, res) -> "Hello World");

        Gson gson = new Gson();
        MetadataRepository metadataRepository = new PostgresRepository();
        Spark.get("/articles/:id", new GetMetadataRoute(metadataRepository), gson::toJson);
    }
}
