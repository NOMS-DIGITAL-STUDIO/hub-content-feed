package uk.gov.justice.digital.noms.hub.ports.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.digital.noms.hub.domain.ContentItem;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;

import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * This class is a prototype/proof of concept for a spike and as such is not covered by automated tests.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class LaravelDataAdapter {

    private final MetadataRepository metadataRepository;

    public LaravelDataAdapter(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @RequestMapping(value = "/hub", method = RequestMethod.GET)
    public String homePage() throws Exception {
        return populateTemplate(Collections.emptyMap(), "home-page.vm");
    }

    @RequestMapping(value = "/hub/education", method = RequestMethod.GET)
    public String educationProviders() throws Exception {
        return populateTemplate(Collections.emptyMap(), "education-providers.vm");
    }

    @RequestMapping(value = "/hub/books", method = RequestMethod.GET)
    public String books() throws Exception {
        List<ContentItem> results = metadataRepository.findAll();
        List<ContentItem> items = results.stream()
                .filter(contentItem -> contentItem.getMetadata() != null && "book".equals(contentItem.getMetadata().get("contentType")))
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("items", items);

        return populateTemplate(data, "books.vm");
    }

    @RequestMapping(value = "/radio/landing", method = RequestMethod.GET)
    public String radioProviders() throws Exception {
        return populateTemplate(Collections.emptyMap(), "radio-providers.vm");
    }

    @RequestMapping(value = "pdf/course/{providerId}", method = RequestMethod.GET)
    public String findCourseCategoriesForProviderId(@PathVariable String providerId) throws Exception {
        List<ContentItem> results = metadataRepository.findAll();
        List<String> categories = results.stream()
                .filter(contentItem -> contentItem.getMetadata() != null && "prospectus".equals(contentItem.getMetadata().get("contentType")))
                .filter(contentItem -> contentItem.getMetadata() != null && providerId.equals(contentItem.getMetadata().get("provider")))
                .map(contentItem -> contentItem.getMetadata() != null ? contentItem.getMetadata().get("category") : null)
                .distinct()
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("providerId", providerId);
        data.put("categories", categories);

        return populateTemplate(data, "education-subjects.vm");
    }

    @RequestMapping(value = "pdf/course/pdfs/{id}", method = RequestMethod.GET)
    public String findCoursesForId(@PathVariable String id) throws Exception {
        String providerId = id.split(":")[0];
        String categoryId = id.split(":")[1];

        List<ContentItem> results = metadataRepository.findAll();
        List<ContentItem> items = results.stream()
                .filter(contentItem -> contentItem.getMetadata() != null && "prospectus".equals(contentItem.getMetadata().get("contentType")))
                .filter(contentItem -> contentItem.getMetadata() != null && providerId.equals(contentItem.getMetadata().get("provider")))
                .filter(contentItem -> contentItem.getMetadata() != null && categoryId.equals(contentItem.getMetadata().get("category")))
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("providerId", providerId);
        data.put("categoryId", categoryId);
        data.put("items", items);

        return populateTemplate(data, "education-course-list.vm");
    }

    @RequestMapping(value = "/api/radio/show/{id}", method = RequestMethod.GET)
    public String findRadioShowsForProviderId(@PathVariable String providerId) throws Exception {

        List<ContentItem> results = metadataRepository.findAll();
        List<ContentItem> items = results.stream()
                .filter(contentItem -> contentItem.getMetadata() != null && "radio".equals(contentItem.getMetadata().get("contentType")))
                .filter(contentItem -> contentItem.getMetadata() != null && providerId.equals(contentItem.getMetadata().get("provider")))
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("providerId", providerId);
        data.put("items", items);

        return populateTemplate(data, "radio-episode.vm");
    }

    private String populateTemplate(Map<String, Object> data, String name) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("data", data);
        StringWriter out = new StringWriter();
        VelocityEngine ve = velocityEngine();
        Template template = ve.getTemplate(name);
        template.merge(context, out);
        return out.toString();
    }

    private VelocityEngine velocityEngine() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        return ve;
    }
}