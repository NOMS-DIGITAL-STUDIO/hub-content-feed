package uk.gov.justice.digital.noms.hub.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentItem {
    private final String title;
    public ContentItem(String title) {
        this.title = title;
    }

}
