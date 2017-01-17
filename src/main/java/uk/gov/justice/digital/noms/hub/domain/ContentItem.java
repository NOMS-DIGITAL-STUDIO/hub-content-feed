package uk.gov.justice.digital.noms.hub.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class ContentItem {
    private String title;
    private String uri;
    private String id;

    public ContentItem(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }
}
