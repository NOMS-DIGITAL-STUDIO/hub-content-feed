package uk.gov.justice.digital.noms.hub.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentItem {
    private String title;
    private String mediaUri;

    public ContentItem(String title, String mediaUri) {
        this.title = title;
        this.mediaUri = mediaUri;
    }
}
