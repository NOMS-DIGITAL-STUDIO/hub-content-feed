package uk.gov.justice.digital.noms.hub

import com.jayway.jsonpath.JsonPath
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import groovy.util.logging.Slf4j
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static org.hamcrest.MatcherAssert.assertThat

@Slf4j
class GetContentItemTest extends Specification {

    private static final String TITLE_PREFIX = 'hub-content-feed'
    private static final String CONTENT_ITEM = 'contentItem'
    private String deployedUrl
    private String mongoDbURL
    private MongoDatabase mongoDatabase
    private RestTemplate restTemplate

    def setup() {
        restTemplate = new RestTemplate()
        setupDeployedUrl()
        setupMongoDB()
    }

    def 'Call Rest Service with a given Content Item ID'() throws Exception {
        setup:
        String id = setupMongoRecord(TITLE_PREFIX + '-Call Rest Service', 'http://localhost/test/somefile1.pdf')

        when:
        ResponseEntity<String> response = restTemplate.getForEntity(deployedUrl + '/content-items/' + id, String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.statusCode, is(HttpStatus.OK))
        assertThat(JsonPath.read(response.getBody(), '$.title'), is(TITLE_PREFIX + '-Call Rest Service'))
        assertThat(JsonPath.read(response.getBody(), '$.uri'), is('http://localhost/test/somefile1.pdf'))


        mongoDatabase.getCollection(CONTENT_ITEM).deleteOne(new Document("_id", new ObjectId("${id}")))
    }

    def 'Call Rest Service to get a list of content items'() throws Exception {
        when:
        ResponseEntity<String> response = restTemplate.getForEntity(deployedUrl + '/content-items', String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.statusCode, is(HttpStatus.OK))
    }

    private void setupMongoDB() {
        mongoDbURL = System.getenv('mongoDbURL')
        if (!mongoDbURL) {
            mongoDbURL = 'mongodb://localhost:27017'
            log.info('mongoDbURL: local')
        }
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoDbURL))
        mongoDatabase = mongoClient.getDatabase('hub_metadata')
    }

    private void setupDeployedUrl() {
        deployedUrl = System.getenv('deployedURL')
        if (!deployedUrl) {
            deployedUrl = 'http://localhost:8080/'
            log.info('deployedUrl: local')
        }
    }

    private String setupMongoRecord(String title, String uri) {
        Document document = new Document(title: title, uri: uri)
        mongoDatabase.getCollection(CONTENT_ITEM).insertOne(document)
        document.get('_id').toString()
    }
}
