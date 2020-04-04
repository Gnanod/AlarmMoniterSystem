package lk.fas.controller;


import lk.fas.Entity.Sensor;
import lk.fas.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping(value = "/SensorController")
public class SensorController {

    @Autowired
    private SensorService sensorService;


    @PostMapping(value = "addSensor")
    public Sensor addSensor(@RequestBody Sensor sensor) {
        System.out.println(sensor.getFloorNumber());
        System.out.println(sensor.getRoomNumber());
        System.out.println(sensor.getSensorId());
        System.out.println(sensor.getSmokeLevel());
        System.out.println(sensor.getStatus());
        System.out.println(sensor);
        System.out.println("GGGGGG");
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

            //return newID;
        } else {
            newID="S" + getCurrentYear() + "0001";
        }

        return newID;

    }

    public static String getCurrentYear() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        return newDate;
    }

}
