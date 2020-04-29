package fas.services.impl;


import fas.Entity.Sensor;
import fas.repository.SensorRepository;
import fas.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public Sensor addSensor(Sensor sensor) {

        return sensorRepository.save(sensor);
    }

    @Override
    public String getResult() {
        Object lastId =sensorRepository.getLastId();
        if(lastId!=null){
            return lastId.toString();
        }else{
            return null;
        }
    }

    @Override
    public Sensor getSensorDetailsAccordingToID(String sensorId) {
        return sensorRepository.getSensorDetailsAccordingToID(sensorId);
    }

    @Override
    public List<Sensor> getActiveSensorDetails() {

        return sensorRepository.getActiveSensorDetails();
    }

    @Override
    public List<Sensor> getAllSensorDetails() {
        return sensorRepository.findAll();
    }
}
