package pl.kielce.tu.neo4j.straz;

import org.neo4j.ogm.session.Session;

class ActionService extends GenericService<Action> {

    public ActionService(Session session) {
        super(session);
    }

    @Override
    Class<Action> getEntityType() {
        return Action.class;
    }

}
