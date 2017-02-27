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

@RestController
@RequestMapping("/api")
@Slf4j
public class LaravelController {

    private final MetadataRepository metadataRepository;

    public LaravelController(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @RequestMapping(value = "/hub", method = RequestMethod.GET)
    public String homePage() throws Exception {
        return populateTemplate(Collections.emptyMap(), "home-page.vm");
    }

    @RequestMapping(value = "/hub/{id}", method = RequestMethod.GET)
    public String sectionPage(@PathVariable String id) throws Exception {
        return populateTemplate(Collections.emptyMap(), "section-page.vm");
    }

    @RequestMapping(value = "pdf/course/{providerId}", method = RequestMethod.GET)
    public String findCourseCategoriesItemForProviderId(@PathVariable String providerId) throws Exception {
        List<ContentItem> results = metadataRepository.findAll();
        List<String> categories = results.stream()
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
    public String findCoursesItemForID(@PathVariable String id) throws Exception {
        String providerId = id.split(":")[0];
        String categoryId = id.split(":")[1];

        List<ContentItem> results = metadataRepository.findAll();
        List<ContentItem> items = results.stream()
                .filter(contentItem -> contentItem.getMetadata() != null && providerId.equals(contentItem.getMetadata().get("provider")))
                .filter(contentItem -> contentItem.getMetadata() != null && categoryId.equals(contentItem.getMetadata().get("category")))
                .map(contentItem -> contentItem)
                .collect(toList());

        Map<String, Object> data = new HashMap<>();
        data.put("providerId", providerId);
        data.put("categoryId", categoryId);
        data.put("items", items);

        return populateTemplate(data, "course-list.vm");
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