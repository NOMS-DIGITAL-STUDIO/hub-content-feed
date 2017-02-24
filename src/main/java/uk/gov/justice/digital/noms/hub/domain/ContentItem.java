package uk.gov.justice.digital.noms.hub.domain;

import lombok.Data;

import java.util.Map;

@Data
public class ContentItem {
    private String title;
    private String uri;
    private String id;
    private String category;

    private String filename;
    private Map<String, String> files;
    private Map<String, String> metadata;
    private String timestamp;


    public ContentItem(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }
}
