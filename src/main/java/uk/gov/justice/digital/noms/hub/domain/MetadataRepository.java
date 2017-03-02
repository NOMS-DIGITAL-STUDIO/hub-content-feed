package uk.gov.justice.digital.noms.hub.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collections;
import java.util.List;

public interface MetadataRepository extends MongoRepository<ContentItem, String> {

    ContentItem findTopByOrderByTitle();
    List<ContentItem> findOrderById();
    List<ContentItem> findDistinctByCategory();

    @Query(value = "{ 'metadata.published' : 'true' }")
    List<ContentItem> findAllPublished();

}
