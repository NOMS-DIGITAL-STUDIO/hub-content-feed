package uk.gov.justice.digital.noms.hub

import spock.lang.Specification


import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class GetArticleTest extends Specification {


    def 'Call Rest Service'() throws Exception {

    }

    def 'Get with Jersey Client'() throws Exception {
//        given:
//        UUID uuid = anArticleExistsInTheDb()

//        when:
//        Map<String, Object> article = getArticleFromService(uuid)

//        then:
//        article.get('title') == 'aTitle'
    }

//    private Map<String, Object> getArticleFromService(UUID uuid) {
//        Client client = ClientBuilder.newClient()
//
//        client
//                .target('http://localhost:5000')
//                .path('articles/' + uuid)
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .get(new GenericType<Map<String, Object>>() {})
//    }
//
//    private UUID anArticleExistsInTheDb() throws SQLException, URISyntaxException {
//        UUID uuid = UUID.randomUUID()
////        String dbUrl = System.getProperty('JDBC_DATABASE_URL')
//        String dbUrl = 'jdbc:postgresql://localhost:5432/hub_metadata'
//        System.out.print("dbUrl $dbUrl")
//        System.out.println('IM HERE')
//
//        Connection connection = DriverManager.getConnection(dbUrl)
//        Statement stmt = connection.createStatement()
//        stmt.executeUpdate('''INSERT INTO hub_metadata VALUES ('' + uuid.toString() + '', 'aTitle')''')
//        return uuid
//    }

}
