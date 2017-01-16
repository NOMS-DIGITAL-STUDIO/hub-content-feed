package uk.gov.justice.digital.noms.hub

import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.MatcherAssert.assertThat

@Slf4j
class ContentFeedStatusTest extends Specification {

    private String deployedUrl

    def setup() {
        setupDeployedUrl()
    }

    def 'Call Status Endpoint'() throws Exception {
        setup:
        RestTemplate restTemplate = new RestTemplate()

        when:
        ResponseEntity<String> response= restTemplate.getForEntity(deployedUrl+'/content-items/health', String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.statusCode, is(HttpStatus.OK))
    }

    private void setupDeployedUrl() {
        deployedUrl = System.getenv('deployedURL')
        if(!deployedUrl) {
            deployedUrl = 'http://localhost:8080/hub-content-feed'
        }
        log.info('deployedUrl: {}', deployedUrl)
    }
}
