package uk.gov.justice.digital.noms.hub

import com.jayway.jsonpath.JsonPath
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import groovy.util.logging.Slf4j
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.containsString
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
        clearDownMongoDB()
    }

    def 'Call Rest Service'() throws Exception {
        setup:
        String id = setupMongoRecord(TITLE_PREFIX+'-Call Rest Service', 'http://localhost/test/somefile.pdf')

        when:
        ResponseEntity<String>  response= restTemplate.getForEntity(deployedUrl+'/content-items/'+id, String.class)

        then:
        assertThat(response, notNullValue())
        assertThat(response.statusCode, is(HttpStatus.OK))
        assertThat(JsonPath.read(response.getBody(),'$.title'), is(TITLE_PREFIX+'-Call Rest Service'))
        assertThat(JsonPath.read(response.getBody(),'$.mediaUri'), is('http://localhost/test/somefile.pdf'))

    }

    private void setupMongoDB() {
        mongoDbURL = System.getenv('mongoDbURL')
        if(!mongoDbURL) {
            mongoDbURL = 'mongodb://localhost:27017'
        }
        log.info('mongoDbURL: {}', mongoDbURL)
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoDbURL))
        mongoDatabase = mongoClient.getDatabase('hub_metadata')
    }

    private void setupDeployedUrl() {
        deployedUrl = System.getenv('deployedURL')
        if(!deployedUrl) {
            deployedUrl = 'http://localhost:8080/hub-content-feed'
        }
        log.info('deployedUrl: {}', deployedUrl)
    }

    private void clearDownMongoDB() {
        Bson deleteFilter = new Document('title' : '{$regex: /^hub-content-feed/i}')
        mongoDatabase.getCollection(CONTENT_ITEM).deleteMany(deleteFilter)
    }

    private String setupMongoRecord(String title, String mediaUri) {
        Document document = new Document(title: title, mediaUri: mediaUri)
        mongoDatabase.getCollection(CONTENT_ITEM).insertOne(document)
        document.get('_id').toString()
    }
}
