package lk.fas.controller;


import lk.fas.Entity.Sensor;
import lk.fas.Entity.User;
import lk.fas.services.SensorService;
import lk.fas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
    @Autowired
    private UserService userService;

    //Add Sensor Details to Database
    @PostMapping(value = "addSensor")
    public Sensor addSensor(@RequestBody Sensor sensor) {

        return sensorService.addSensor(sensor);

    }

    //Update Sesnor Detail
    @PostMapping(value = "updateSensor")
    public Sensor updateSensorStatus(@RequestBody Sensor sensor) {

        return sensorService.addSensor(sensor);

    }

    //Get Last Id Of Sensor
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
            newID = "S" + getCurrentYear() + numberFormat.format(id); // Create last id using S , currentyear and last intger number

        } else {
            newID="S" + getCurrentYear() + "0001"; // Create First  id using S , currentyear and last intger number
        }

        return newID;

    }

    //this method is used to get Sensor details according to its sensor Id
    @GetMapping(value = "/getSensorDetailsAccordingToID/{sensorId}")
    public Sensor getSensorDetailsAccordingToID(@PathVariable String sensorId){
        return sensorService.getSensorDetailsAccordingToID(sensorId);
    }

    // Get All Sensors Details
    @GetMapping(value = "/getAllSensorDetails")
    public List<Sensor> getAllSensorDetails(){
        return sensorService.getAllSensorDetails();
    }


    // Get All Active Sensor Details
    @GetMapping(value = "/getActiveSensorDetails")
    public List<Sensor> getActiveSensorDetails(){

        updateSensorValues(sensorService.getActiveSensorDetails());

        return sensorService.getActiveSensorDetails();
    }

    //Get All Logged Users
    @GetMapping(value = "/getLoggedUserDetails")
    public List<User> getLoggedUserDetails(){
        return userService.getLoggedUserDetails();
    }

    // Update Sensor values( Co2 level and Smoke Level ) Using Random Number
    public void updateSensorValues(List<Sensor> sensor){
        for (Sensor s: sensor
             ) {
            Sensor s1 = new Sensor();
            s1.setRoomNumber(s.getRoomNumber());
            s1.setFloorNumber(s.getFloorNumber());
            s1.setSensorId(s.getSensorId());
            s1.setStatus("Active");
            int smokeLevel = getRandomNumber(10, 1);
            int co2Level = getRandomNumber(10, 1);
            s1.setSmokeLevel(smokeLevel);  //get random number to update Smoke level
            s1.setCo2Level(co2Level); //get random number to update Co2 level
            List<User> loggedUsers = getLoggedUserDetails();  // Get Currently Active users
            if(smokeLevel >=5){
                sendMessage(loggedUsers, s.getSensorId(),smokeLevel,"Smoke");
            }
            if(co2Level >=5){
                sendMessage(loggedUsers, s.getSensorId(),co2Level,"Co2");
            }
            sensorService.addSensor(s1);
        }
    }

    private void sendMessage(List<User> loggedUsers, String sensorId, int level, String type) {

        for (User user: loggedUsers ) {
            if(type.equals("Smoke")){
                sendTextMessage("Smoke",user);
            }else{
                sendTextMessage("Co2",user);
            }
        }
    }

    public void sendTextMessage(String type,User user){
        String msg = " " ;
        String number = "94" + Integer.toString(user.getPhoneNo()).substring(1);
        String accountName = " ";
        String password = " ";
        URL textit = null;
        try {
            textit = new URL("http://textit.biz/sendmsg/index.php?id=" + accountName + "&pw=" + password + "&to=" + number + "&text=Dear valuable customer, we started servicing your vehicle. -TurismoAuto");
            BufferedReader in = new BufferedReader(new InputStreamReader(textit.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method is used to get random number
    public static int getRandomNumber(int maxNumber, int minNumber){
        return ((int) (Math.random()*(maxNumber - minNumber))) + minNumber;
    }



    // this method is used to get current year (to create Last id)
    public static String getCurrentYear() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        return newDate;
    }



}
