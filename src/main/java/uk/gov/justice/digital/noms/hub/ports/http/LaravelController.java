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
import uk.gov.justice.digital.noms.hub.ports.http.response.ContentItemList;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/pdf/course/")
@Slf4j
public class LaravelController {

    private final MetadataRepository metadataRepository;

    public LaravelController(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String findCourseCategoriesItemForID(@PathVariable String id) throws Exception {
        System.out.println("Foo");
        List<ContentItem> results = metadataRepository.findAll();

        List<String> categories = results.stream()
                .map(ContentItem::getCategory)
                .distinct()
                .collect(toList());

        System.out.println(categories);

        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template template = ve.getTemplate("category-response.vm");

        VelocityContext context = new VelocityContext();
        Map<String, Object> data = new HashMap<>();

        data.put("id", id);
        data.put("cats", categories);

        context.put("data", data);
        StringWriter out = new StringWriter();
        template.merge(context, out);


        return out.toString();
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