package uk.gov.justice.digital.noms.hub

import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.MatcherAssert.assertThat

@Slf4j
class GetArticleTest extends Specification {

    def 'Call Rest Service'() throws Exception {
        setup:
        String deployedUrl = System.getenv('deployedURL')
        log.info('URL1: {}', deployedUrl)
        RestTemplate restTemplate = new RestTemplate();

        when:
        ResponseEntity<String>  response= restTemplate.getForEntity(deployedUrl, String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.toString(), containsString('CANNED TITLE'))
    }
}
