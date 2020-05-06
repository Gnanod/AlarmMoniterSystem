/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.sensor.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lk.amc.dto.Sensor;
import lk.amc.dto.User;
import lk.amc.service.SensorService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Dakshika
 */
public class SensorServiceImpl extends UnicastRemoteObject implements SensorService {

    private Boolean res = false;

    public SensorServiceImpl() throws RemoteException {

    }

    private static int count = 0;

    @Override
    public boolean addSensor(Sensor s) throws Exception {
        
        //Add Sensor Details to sensor Object
        JSONObject sensorDetails = new JSONObject();
        sensorDetails.put("sensorId", s.getSensorId());
        sensorDetails.put("floorNumber", s.getFloorNumber());
        sensorDetails.put("roomNumber", s.getRoomNumber());
        sensorDetails.put("smokeLevel", s.getSmokeLevel());
        sensorDetails.put("status", s.getStatus());
        JSONObject sensorObject = new JSONObject();
        sensorObject.put("Sensor", sensorDetails);
        
          //Send This  Sensor json object to springboot rest Api
        URL url = new URL("http://localhost:8080/SensorController/addSensor");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonString = sensorDetails.toString();
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        con.disconnect();
        return true;
    }

    @Override
    public synchronized int increment() throws Exception {
        count++;
        return count;

    }

    //This method is use to get Last Sensor Id
    @Override
    public String getLastId() throws Exception {
        HttpURLConnection connection = null;
        StringBuffer content = null;
        try {
            URL url = new URL("http://localhost:8080/SensorController/getLastID");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String contentType = connection.getHeaderField("Content-Type");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        
        //return the last sensor ID
        return content.toString();
    }

    @Override
    public List<Sensor> getAllSensorDetails() throws Exception {

        URL url;
        List<Sensor> sensorList = null;
        //Get All Sensor Details Using Spring Boot Rest Api
        url = new URL("http://localhost:8080/SensorController/getAllSensorDetails");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();
        //System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String result = response.toString();
            JSONArray jsonArray = new JSONArray(result);

            sensorList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jobj = jsonArray.getJSONObject(i);

                Sensor s = new Sensor();
                //Convert json object data and Set it to sensor Object
                s.setCo2Level(jobj.getInt("co2Level"));
                s.setFloorNumber(jobj.getInt("floorNumber"));
                s.setRoomNumber(jobj.getInt("roomNumber"));
                s.setSensorId(String.valueOf(jobj.getString("sensorId")));
                s.setSmokeLevel(jobj.getInt("smokeLevel"));
                s.setStatus(String.valueOf(jobj.getString("status")));
                sensorList.add(s);
            }
        } else {

        }
        return sensorList;
    }

    @Override
    public Sensor getSensorDetailsAccordingToID(String sensorId) throws Exception {

        Sensor sensor = new Sensor();
        //Send Sensor Id to the Spring boot Api to get Sensor Details
        URL url = new URL("http://localhost:8080/SensorController/getSensorDetailsAccordingToID/" + sensorId);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            if ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            String result = response.toString();

            JSONObject jobj = new JSONObject(result);
            
            //Convert json object data and Set it to sensor Object
            sensor.setCo2Level(jobj.getInt("co2Level"));
            sensor.setFloorNumber(jobj.getInt("floorNumber"));
            sensor.setRoomNumber(jobj.getInt("roomNumber"));
            sensor.setSensorId(String.valueOf(jobj.getString("sensorId")));
            sensor.setSmokeLevel(jobj.getInt("smokeLevel"));
            sensor.setStatus(String.valueOf(jobj.getString("status")));

        } else {
        }
        return sensor;
    }

    @Override
    public boolean addUser(User user) throws Exception {

        // Add User Details To the Json Object
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", user.getUsername());
        userDetails.put("email", user.getEmail());
        userDetails.put("phoneNo", user.getPhoneNo());
        userDetails.put("password", user.getPassword());
        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("User", userDetails);
        //Send JSON User Object to Spring boot Server
        URL url = new URL("http://localhost:8080/userController/addUser");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        String jsonInputString = userDetails.toString();

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        connection.disconnect();
        return true;
    }

    @Override
    public boolean loginUser(String username, String password) throws Exception {

       // Encoded UserName And Password Using Base64
        String encodedUserName = Base64.getEncoder().encodeToString(username.getBytes());
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
            
        User user = new User();
        
        // Send encoded password and Username to the Spring Boot Api Using 
        URL obj = new URL("http://localhost:8080/userController/loginUser/" + encodedUserName + "/" + encodedPassword);
        System.out.println("http://localhost:8080/userController/loginUser/" + encodedUserName + "/" + encodedPassword);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            if ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String result = response.toString();
            res = Boolean.parseBoolean(result);

        } else {
            System.out.println("Error Occured");

        }
        return res;
    }

}
