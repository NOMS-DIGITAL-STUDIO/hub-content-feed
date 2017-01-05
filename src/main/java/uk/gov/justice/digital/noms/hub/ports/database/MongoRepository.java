package uk.gov.justice.digital.noms.hub.ports.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import uk.gov.justice.digital.noms.hub.domain.ContentItem;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class MongoRepository implements MetadataRepository {

    @Value("${hub.content.feed.datastore.url}")
    private String databaseURL;

    @Override
    public ContentItem getContentItemForId(UUID uuid) {
        Assert.notNull(databaseURL, "DatabaseURL is null");
        return ContentItem.builder().title("CANNED TITLE").build();
    }
}
