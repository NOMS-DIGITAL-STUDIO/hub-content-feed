package uk.gov.justice.digital.noms.hub.ports.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.noms.hub.domain.ContentItem;
import uk.gov.justice.digital.noms.hub.ports.http.response.ContentItemList;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;

import java.util.Arrays;
import java.util.List;

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
    public ContentItem findContentItemForID(@PathVariable String id) {
        log.info("Retrieve Content Item ID {}", id);
        return metadataRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ContentItemList findAllContentItems() {
        log.info("Retrieve All Content Items");
        List<ContentItem> list = metadataRepository.findAll();
        if(list.size() > 0) {
            return new ContentItemList(Arrays.asList(list.get(list.size() - 1)));
        }
        return new ContentItemList();
    }

}