package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Line;
import at.htl.linzlinien.entity.Location;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


@ApplicationScoped
public class LineRepository {

    @Inject
    EntityManager em;

    /**
     * search the line in the database.
     * - when it is already persisted, return the existing line
     * - when the line.name is not found, persist the line object
     *
     * @param line
     * @return the existing location, when line.name already exists in db
     * or return the persisted line, when not found in database
     */
    @Transactional
    public Line save(Line line) {
        Line l = findByName(line.getName());

        if (l != null){
            return l;
        }

        return em.merge(line);
    }

    /**
     * use a NamedQuery "Line.findByName" to retrieve the Line by name
     * when the NoResultException is thrown, return null
     *
     * @param name
     * @return the line (with the given name) or null, when the name is not in the db
     */
    public Line findByName(String name) {

        try {
            TypedQuery<Line> query = em
                    .createNamedQuery("Line.findByName", Line.class)
                    .setParameter("NAME", name);

            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }

    }

}
