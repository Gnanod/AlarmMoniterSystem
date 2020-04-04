package lk.fas.repository;


import lk.fas.Entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SensorRepository extends JpaRepository<Sensor,String> {


    @Query(value = " select sensor_id from sensor order by 1 desc limit 1",nativeQuery = true)
    Object getLastId();


    @Query(value = "from Sensor where sensorId =?1")
    Sensor getSensorDetailsAccordingToID(String sensorId);
}
