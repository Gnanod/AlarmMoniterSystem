package lk.fas.services;

import lk.fas.Entity.Sensor;

import java.util.List;

public interface SensorService {
    Sensor addSensor(Sensor sensor);

    String getResult();

    Sensor getSensorDetailsAccordingToID(String sensorId);

    List<Sensor> getActiveSensorDetails();

    List<Sensor> getAllSensorDetails();
}
