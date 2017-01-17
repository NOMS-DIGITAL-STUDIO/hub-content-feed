package uk.gov.justice.digital.noms.hub.ports.http.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.justice.digital.noms.hub.domain.ContentItem;

import java.util.List;

@Data
public class ContentItemList {

    @JsonProperty("contentItems")
    private List<ContentItem> list;

    public ContentItemList() {}

    public ContentItemList(List<ContentItem> list) {
            this.list = list;
        }

}
