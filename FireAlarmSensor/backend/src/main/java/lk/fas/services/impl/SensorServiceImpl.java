package lk.fas.services.impl;

import lk.fas.Entity.Sensor;
import lk.fas.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lk.fas.services.SensorService;

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
}
