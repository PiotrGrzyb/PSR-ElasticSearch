package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.session.Session;

import java.util.HashMap;
import java.util.Map;

class FiretruckService extends GenericService<Firetruck> {

    public FiretruckService(Session session) {
        super(session);
    }

    @Override
    Class<Firetruck> getEntityType() {
        return Firetruck.class;
    }

    Iterable<Map<String, Object>> getFiremanRelationships() {
        String query =
                "MATCH (b:FIRETRUCK)-[r]-() " +
                        "WITH type(r) AS t, COUNT(r) AS c " +
                        "WHERE c >= 1 " +
                        "RETURN t, c";
        System.out.println("Executing " + query);
        HashMap<String, Object> params = new HashMap<String, Object>();
        return session.query(query, params).queryResults();
    }

}
