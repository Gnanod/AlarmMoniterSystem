package lk.fas.controller;


import lk.fas.Entity.Sensor;
import lk.fas.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/SensorController")
public class SensorController {

    @Autowired
    private SensorService sensorService;


    @PostMapping(value = "addSensor")
    public Sensor addSensor(@RequestBody Sensor sensor) {

        return sensorService.addSensor(sensor);

    }

    @PostMapping(value = "updateSensor")
    public Sensor updateSensorStatus(@RequestBody Sensor sensor) {

        return sensorService.addSensor(sensor);

    }

    @GetMapping(value = "/getLastID")
    public String getLastID() {
        String lastId = sensorService.getResult();
        String newID = "";
        if (lastId != null) {
            String subid = lastId.substring(5);
            int id = Integer.parseInt(subid);
            id++;
            NumberFormat numberFormat = NumberFormat.getIntegerInstance();
            numberFormat.setMinimumIntegerDigits(4);
            numberFormat.setGroupingUsed(false);
            newID = "S" + getCurrentYear() + numberFormat.format(id);

        } else {
            newID="S" + getCurrentYear() + "0001";
        }

        return newID;

    }

    @GetMapping(value = "/getSensorDetailsAccordingToID/{sensorId}")
    public Sensor getSensorDetailsAccordingToID(@PathVariable String sensorId){
        return sensorService.getSensorDetailsAccordingToID(sensorId);
    }

    @GetMapping(value = "/getAllSensorDetails")
    public List<Sensor> getAllSensorDetails(){
        return sensorService.getAllSensorDetails();
    }

    @GetMapping(value = "/getActiveSensorDetails")
    public List<Sensor> getActiveSensorDetails(){

        updateSensorValues(sensorService.getActiveSensorDetails());
        return sensorService.getActiveSensorDetails();
    }

    public void updateSensorValues(List<Sensor> senosr){
        for (Sensor s: senosr
             ) {
            Sensor s1 = new Sensor();
            s1.setRoomNumber(s.getRoomNumber());
            s1.setFloorNumber(s.getFloorNumber());
            s1.setSensorId(s.getSensorId());
            s1.setStatus("Active");
            s1.setSmokeLevel(getRandom(10));
            s1.setCo2Level(getRandom(10));
            sensorService.addSensor(s1);
        }
    }

    public static int getRandom(int max){
        return (int) (Math.random()*max);
    }


    public static String getCurrentYear() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        return newDate;
    }

}
