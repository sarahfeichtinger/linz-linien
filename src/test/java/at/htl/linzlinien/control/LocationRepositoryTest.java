package at.htl.linzlinien.control;

import at.htl.linzlinien.entity.Location;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class LocationRepositoryTest {

    @Inject
    LocationRepository locationRepository;

    @Test
    void createLocation() {
        locationRepository.save(new Location("ABC"));
        Location retrievedLoation = locationRepository.findByName("ABC");
        assertThat(retrievedLoation.getId()).isNotNull();
        assertThat(retrievedLoation.getName()).isEqualTo("ABC");
    }

    @Test
    void searchNonExistingLocation() {
        Location retrievedLoation = locationRepository.findByName("DEF");
        assertThat(retrievedLoation).isNull();
    }

}