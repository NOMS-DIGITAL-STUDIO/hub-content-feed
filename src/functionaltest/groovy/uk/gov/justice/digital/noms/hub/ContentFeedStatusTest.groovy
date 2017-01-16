package uk.gov.justice.digital.noms.hub

import com.jayway.jsonpath.JsonPath
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.MatcherAssert.assertThat
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;

@Slf4j
class ContentFeedStatusTest extends Specification {

    private String deployedUrl
    private RestTemplate restTemplate

    def setup() {
        restTemplate = new RestTemplate()
        setupDeployedUrl()
    }

    def 'Call Status Endpoint'() throws Exception {
        when:
        ResponseEntity<String> response= restTemplate.getForEntity(deployedUrl+'/health', String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.statusCode, is(HttpStatus.OK))
        assertThat(JsonPath.read(response.getBody(),'$.status'), is('UP'))
    }

    private void setupDeployedUrl() {
        deployedUrl = System.getenv('deployedURL')
        if(!deployedUrl) {
            deployedUrl = 'http://localhost:8080/hub-content-feed'
        }
        log.info('deployedUrl: {}', deployedUrl)
    }
}
