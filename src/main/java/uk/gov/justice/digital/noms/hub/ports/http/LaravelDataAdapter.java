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

import static java.util.stream.Collectors.groupingBy;
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
                .filter(contentItem -> "book".equals(contentItem.contentType()))
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("items", items);

        return populateTemplate(data, "books.vm");
    }

    @RequestMapping(value = "/radio/landing", method = RequestMethod.GET)
    public String radioProviders() throws Exception {
        return populateTemplate(Collections.emptyMap(), "radio-providers.vm");
    }

    @RequestMapping(value = "/video/landing", method = RequestMethod.GET)
    public String videoProviders() throws Exception {
        List<ContentItem> results = metadataRepository.findAll();
        Map<String, List<ContentItem>> items = results.stream()
                .filter(contentItem -> "video".equals(contentItem.contentType()))
                .collect(groupingBy(ContentItem::provider));

        System.out.println(items);
        return populateTemplate(items, "video-providers.vm");
    }

    @RequestMapping(value = "/video/recent", method = RequestMethod.GET)
    public String recentVideos() throws Exception {
        return populateTemplate(Collections.emptyMap(), "video-recent.vm");
    }

    @RequestMapping(value = "pdf/course/{providerId}", method = RequestMethod.GET)
    public String findCourseCategoriesForProviderId(@PathVariable String providerId) throws Exception {
        List<ContentItem> results = metadataRepository.findAll();
        List<String> categories = results.stream()
                .filter(contentItem -> "prospectus".equals(contentItem.contentType()))
                .filter(contentItem -> providerId.equals(contentItem.provider()))
                .map(ContentItem::category)
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
                .filter(contentItem -> "prospectus".equals(contentItem.contentType()))
                .filter(contentItem -> providerId.equals(contentItem.provider()))
                .filter(contentItem -> categoryId.equals(contentItem.category()))
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
                .filter(contentItem -> "radio".equals(contentItem.contentType()))
                .filter(contentItem -> providerId.equals(contentItem.provider()))
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("providerId", providerId);
        data.put("items", items);

        return populateTemplate(data, "radio-episode.vm");
    }

    private String populateTemplate(Map<String, ? extends Object> data, String name) throws Exception {
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