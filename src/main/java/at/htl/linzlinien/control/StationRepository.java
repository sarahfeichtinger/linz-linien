package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Line;
import at.htl.linzlinien.entity.Location;
import at.htl.linzlinien.entity.Station;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StationRepository {

    @Inject
    LineRepository lineRepository;

    @Inject
    LocationRepository locationRepository;

    @Inject
    EntityManager em;

    @Transactional
    public Station save(Station station) {
        return em.merge(station);
    }


    /**
     * persist all stations from the List "stations" in the database
     *
     * @param lineName
     * @param stations
     */
    public void saveStationsFromLine(String lineName, List<String> stations) {

    }

    /**
     * find all stations per line in the database
     *
     * @param lineName
     * @return
     */
    public List<Station> stationsPerLine(String lineName) {
        return null;
    }

}
