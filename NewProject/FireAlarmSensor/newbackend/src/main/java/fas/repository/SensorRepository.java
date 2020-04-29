package fas.repository;



import fas.Entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor,String> {

    //get Last Id
    @Query(value = " select sensor_id from sensor order by 1 desc limit 1",nativeQuery = true)
    Object getLastId();

    // get sensor details according to its id
    @Query(value = "from Sensor where sensorId =?1")
    Sensor getSensorDetailsAccordingToID(String sensorId);

    // get only active sensor details

    @Query(value = "from Sensor where status ='Active'")
    List<Sensor> getActiveSensorDetails();

}
