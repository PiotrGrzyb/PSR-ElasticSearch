package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.session.Session;

class FiremanService extends GenericService<Fireman> {

    public FiremanService(Session session) {
        super(session);
    }

    @Override
    Class<Fireman> getEntityType() {
        return Fireman.class;
    }

}
