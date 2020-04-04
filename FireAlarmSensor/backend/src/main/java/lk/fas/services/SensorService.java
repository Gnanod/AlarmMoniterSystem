package lk.fas.services;

import lk.fas.Entity.Sensor;

public interface SensorService {
    Sensor addSensor(Sensor sensor);

    String getResult();

    Sensor getSensorDetailsAccordingToID(String sensorId);
}
