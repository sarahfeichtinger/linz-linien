package at.htl.linzlinien.boundary;

import at.htl.linzlinien.control.StationRepository;
import at.htl.linzlinien.entity.Station;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.lang.System.out;

public class StationEndpoint {

    /**
     * localhost:8080/api/station?line=1
     *
     * @param lineName
     * @return all Stations of the given line
     */
    public JsonArray printAllStationsPerLine(String lineName) {


        return null;
    }
}