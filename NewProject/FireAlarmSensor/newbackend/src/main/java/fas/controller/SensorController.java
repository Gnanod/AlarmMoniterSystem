package fas.controller;


import fas.Entity.Sensor;
import fas.Entity.User;
import fas.services.EmailUtil;
import fas.services.SensorService;
import fas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
                System.out.println("Smoke Level above 5");
            }
            if(co2Level >=5){
                sendMessage(loggedUsers, s.getSensorId(),co2Level,"Co2");
                System.out.println("Co2 Level above 5");
            }
            sensorService.addSensor(s1);
        }
    }

    private void sendMessage(List<User> loggedUsers, String sensorId, int level, String type) {
        System.out.println("Mail Sending");

        for (User user: loggedUsers ) {
            System.out.println(user.getEmail());
            if(type.equals("Smoke")){
                System.err.println("Mail Co2");
                sendTextMessage("Co2",user,sensorId,level);
                sendEmail("Co2",user,sensorId,level);
            }else{
                sendTextMessage("Co2",user,sensorId,level);
                sendEmail("Co2",user,sensorId,level);
            }
        }
    }

    public void sendEmail(String type,User user,String sensorId,int level){
        try {
            final String fromEmail = " "; //requires valid gmail id
            final String password = " "; // correct password for gmail id
            final String toEmail = "1995gnanod@gmail.com"; // can be any email id
//                    final String toEmail = user.getEmail(); // can be any email id

            System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            System.err.println("Mail Sending");

            EmailUtil.sendEmail(session, toEmail,type+"Level Alert", type+" level has increased than usual.(5)Please Take Necessary Steps! ThankYou");

            //sendTextMessage("Smoke",user,sensorId,level);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendTextMessage(String type,User user,String sensorId,int level){
        String msg = "Enter%20Message" ;  // to spaces use %20 examle---->  Hi Hi Hi   ===> Hi%20Hi%20%20
        System.out.println("Phone Number :"+Integer.toString(user.getPhoneNo()));
        String number = "94" + Integer.toString(user.getPhoneNo());
       // String number = "94" + 772218111;
        String accountName = "94772218111";
        String password = "8694";
        System.out.println("hhhhhhh");
        URL textit = null;
//        HttpURLConnection connection = null;
        try {
            textit = new URL("http://textit.biz/sendmsg/index.php?id=" + accountName + "&pw=" + password + "&to=" + number + "&text="+msg);

            BufferedReader in = new BufferedReader(new InputStreamReader(textit.openStream()));

            String inputLine;
            while((inputLine = in.readLine()) != null)
                System.out.println("GGG"+inputLine);
            in.close();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method is used to get random number
    public static int getRandomNumber(int maxNumber, int minNumber){
        return ((int) (Math.random()*(maxNumber - minNumber))) + minNumber;
    }

    public void sendEmail(){

    }


    // this method is used to get current year (to create Last id)
    public static String getCurrentYear() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String newDate = dateFormat.format(date);
        return newDate;
    }



}
