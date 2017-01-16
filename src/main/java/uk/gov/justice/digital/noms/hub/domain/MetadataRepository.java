package uk.gov.justice.digital.noms.hub.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetadataRepository extends MongoRepository<ContentItem, String> {

    ContentItem findTopByOrderByTitle();


}
