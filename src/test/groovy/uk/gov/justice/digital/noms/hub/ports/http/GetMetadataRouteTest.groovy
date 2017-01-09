package uk.gov.justice.digital.noms.hub.ports.http

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject
import uk.gov.justice.digital.noms.hub.controller.HubController
import uk.gov.justice.digital.noms.hub.domain.ContentItem
import uk.gov.justice.digital.noms.hub.domain.MetadataRepository

import static org.assertj.core.api.Assertions.assertThat
import static org.hamcrest.CoreMatchers.notNullValue
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetMetadataRouteTest extends Specification {
    @Subject
    HubController controller

    private MetadataRepository metadataRepository = Mock(MetadataRepository)

    private MockMvc mockMvc;

    def setup() {
        controller = new HubController(metadataRepository)
            this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def 'Retrieve the article for a given ID'() throws Exception {
        setup:
        UUID uuid = UUID.randomUUID()
        metadataRepository.getContentItemForId(uuid) >> new ContentItem('Title A')

        when:
        String result = mockMvc.perform(get('/content-items/' + uuid))
                .andDo(print())
                .andExpect(jsonPath('$.title').value('Title B'))
                .andExpect(status().isOk())

        then:
        assertThat(result, notNullValue())
    }

}