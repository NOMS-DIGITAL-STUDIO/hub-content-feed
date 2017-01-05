package uk.gov.justice.digital.noms.hub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.noms.hub.domain.ContentItem;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;

import java.util.UUID;

/**
 * Main Controller for Hub Content Feed.
 *
 *
 * @author amjad.khan
 */
@RestController
@RequestMapping("/content-items")
@Slf4j
public class HubController {

    private final MetadataRepository metadataRepository;

    public HubController(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ContentItem findContentItemForUUID(@PathVariable UUID id) {
        log.info("Retrieve Content Item UUID {}", id);
        return metadataRepository.getContentItemForId(id);
    }


}