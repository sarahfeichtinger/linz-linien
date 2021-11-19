package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Location;
import at.htl.linzlinien.entity.Station;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LocationRepository {

    @Inject
    Logger LOG;

    @Inject
    EntityManager em;

    /**
     * search the location in the database.
     *  - when it is already persisted, return the existing location
     *  - when the location.name is not found, persist the location object
     *
     * @param location
     * @return the existing location, when location.name already exists in db
     *         or return the persisted location, when not found in database
     */
    @Transactional
    public Location save(Location location) {
        Location l = findByName(location.getName());

        if (l!=null){
            return l;
        }

        return em.merge(location);
    }

    /**
     * use a NamedQuery "Location.findByName" to retrieve the Location by name
     * when the NoResultException is thrown, return null
     *
     * @param name
     * @return the location (with the given name) or null, when the name is not in the db
     */
    public Location findByName(String name) {
        try {
            TypedQuery<Location> query = em
                    .createNamedQuery("Location.findByName", Location.class)
                    .setParameter("NAME", name);

            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }

    /**
     * iterate the stations-list
     *   save the stations as locations (Haltestellen)
     * @param stations
     */
    public void saveLocationBulk(List<String> stations) {
        stations.forEach(s -> save(new Location(s)));
    }
}
