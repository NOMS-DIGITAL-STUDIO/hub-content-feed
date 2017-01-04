package uk.gov.justice.digital.noms.hub.ports.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.justice.digital.noms.hub.domain.Article;
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository;
import spark.Request;
import spark.Response;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetMetadataRouteTest {

    @Mock
    MetadataRepository metadataRepository;

    @Mock
    Request request;

    @Mock
    Response response;

    @Test
    public void retrievesTheArticleForAGivenId() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        when(request.params(":id")).thenReturn(uuid.toString());
        when(metadataRepository.getArticleForId(uuid)).thenReturn(new Article("title"));
        GetMetadataRoute getMetadataRoute = new GetMetadataRoute(metadataRepository);

        // when
        Object result = getMetadataRoute.handle(request, response);

        // then
        assertThat(((Article) result).getTitle()).isEqualTo("title");
    }
}