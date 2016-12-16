package org.moj.hub.domain;

import java.util.UUID;

public interface MetadataRepository {
    Article getArticleForId(UUID id);
}
