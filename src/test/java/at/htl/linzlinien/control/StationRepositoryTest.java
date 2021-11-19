package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Station;
import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class StationRepositoryTest {

    @Inject
    InitBean initBean;

    @Inject
    StationRepository stationRepository;

    @Inject
    AgroalDataSource dataSource;

    @Test
    void test00_countLocations() {
        initBean.getAllLines();
        Table table = new Table(dataSource, "LL_LOCATION");
        org.assertj.db.api.Assertions.assertThat(table).hasNumberOfRows(139);
    }

    @Test
    void test01_countLine() {
        Table table = new Table(dataSource, "LL_LINE");
        org.assertj.db.api.Assertions.assertThat(table).hasNumberOfRows(10);
    }

    @Test
    void test03_countStation() {
        Table table = new Table(dataSource, "LL_STATION");
        org.assertj.db.api.Assertions.assertThat(table).hasNumberOfRows(300);
    }

    @Test
    void test05_findStationsPerLine1() {
        List<Station> stations = stationRepository.stationsPerLine("1");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(37);
    }

    @Test
    void test10_findStationsPerLine2() {
        List<Station> stations = stationRepository.stationsPerLine("2");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(46);
    }

    @Test
    void test20_findStationsPerLine3() {
        List<Station> stations = stationRepository.stationsPerLine("3");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(26);
    }

    @Test
    void test30_findStationsPerLinePBB() {
        List<Station> stations = stationRepository.stationsPerLine("PBB");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(14);
    }

    @Test
    void test40_findStationsPerLine25() {
        List<Station> stations = stationRepository.stationsPerLine("25");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(28);
    }

    @Test
    void test50_findStationsPerLine4() {
        List<Station> stations = stationRepository.stationsPerLine("4");
        stations.forEach(out::println);
        assertThat(stations.size()).isEqualTo(26);
    }


}