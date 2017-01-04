package uk.gov.justice.digital.noms.hub.ports.http;

import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

public class GetMetadataRoute implements Route {

    private MetadataRepository metadataRepository;

    public GetMetadataRoute(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.type("application/json");
        String id = request.params(":id");
        UUID uuid = UUID.fromString(id);
        return metadataRepository.getArticleForId(uuid);
    }

}
