package uk.gov.justice.digital.noms.hub.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MetadataRepository extends MongoRepository<ContentItem, String> {

    ContentItem findTopByOrderByTitle();
    List<ContentItem> findOrderById();
    List<ContentItem> findDistinctByCategory();

}
