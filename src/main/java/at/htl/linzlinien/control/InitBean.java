package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Line;
import at.htl.linzlinien.entity.Station;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@ApplicationScoped
public class InitBean {

    @Inject
    Logger LOG;

    @Inject
    StationRepository stationRepository;

    @Inject
    LocationRepository locationRepository;

    @Inject
    LineRepository lineRepository;


    Map<String, String> lines = new HashMap<>(10);

    public InitBean() {
        lines.put("1", "https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_1/");
        lines.put("2", "https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_2/");
        lines.put("3", "https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_3/");
        lines.put("4", "https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_4/");
        lines.put("N82", "https://www.linzwiki.at/wiki/N82/");
        lines.put("N84", "https://www.linzwiki.at/wiki/N84/");
        lines.put("PBB", "https://www.linzwiki.at/wiki/P%C3%B6stlingbergbahn/");
        lines.put("70", "https://www.linzwiki.at/wiki/Linie_70_(Linz_Linien)/");
        lines.put("43", "https://www.linzwiki.at/wiki/Linie_43_(Linz_Linien)/");
        lines.put("25", "https://www.linzwiki.at/wiki/Linie_25_(Linz_Linien)/");
    }

    /**
     *
     *
     * Runs when starting the app
     * In TEST-Profile, the locations, lines and stations are not imported
     *
     * https://stackoverflow.com/a/69870750
     *
     * @param event
     */
    void onStart(@Observes StartupEvent event) {
        // Kommentieren Sie diese beiden Methodenaufrufe aus, wenn sie mit der Abarbeitung der Map beginnen
        parseRouteOne();
        parseRouteTwo();

        // Kommentieren Sie dies wieder ein
        if(!"test".equals(io.quarkus.runtime.configuration.ProfileManager.getActiveProfile())) {
            getAllLines();
        }
    }

    private void parseRouteOne() {
        try {
            Document doc = Jsoup.connect("https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_1/").get();
            //Elements stations = doc.select(".mw-parser-output > ul:nth-child(8) > li > a");
            Elements stations = doc.select("li > a");
            LOG.info("---------------------> Linie 1");
            for (Element station : stations) {
                //logger.info(station.toString());
                if (station.attr("title").startsWith("Haltestelle")) {
                    LOG.infof("%s", station.attr("title"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseRouteTwo() {
        try {
            Document doc = Jsoup.connect("https://www.linzwiki.at/wiki/Stra%C3%9Fenbahnlinie_2/").get();
            Elements stations = doc.select("li > a");
            LOG.info("---------------------> Linie 2");
            for (Element station : stations) {
                //logger.info(station.toString());
                if (station.attr("title").startsWith("Haltestelle"))
                    LOG.infof("%s", station.attr("title"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * iterates the lines-map
     *    persists the line-name in the database
     *    gets all stations from HTML-page
     *    persists all locations in the database
     *    persists all stations in the database
     */
    void getAllLines() {
        for (String lineKey : lines.keySet()) {

            // add Line
            lineRepository.save(new Line(lineKey));

            // extract stations from HTML-page
            List<String> stations = parseRoute(lineKey);

            // add Locations
            locationRepository.saveLocationBulk(stations);

            // add Station
            stationRepository.saveStationsFromLine(lineKey, stations);
        }
    }

    /**
     * returns a List of Stations
     * All titles which begin with "Haltestelle"
     * Remove the substring "Haltestelle" from title
     *
     * @param key
     * @return
     */
    private List<String> parseRoute(String key) {
        List<String> stationList = new LinkedList<>();

        try {
            Document doc = Jsoup.connect(lines.get(key)).get();
            Elements stations = doc.select("li > a");
            for (Element station : stations) {
                //logger.info(station.toString());
                if (station.attr("title").startsWith("Haltestelle"))
                    stationList.add(station.attr("title").replace("Haltestelle ", ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationList;
    }


}
