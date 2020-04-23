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

    public SensorServiceImpl() throws RemoteException {

    }

    private static int count = 0;

    @Override
    public boolean addSensor(Sensor s) throws Exception {
        JSONObject sensorDetails = new JSONObject();
        sensorDetails.put("sensorId", s.getSensorId());
        sensorDetails.put("floorNumber", s.getFloorNumber());
        sensorDetails.put("roomNumber", s.getRoomNumber());
        sensorDetails.put("smokeLevel", s.getSmokeLevel());
        sensorDetails.put("status", s.getStatus());
        JSONObject sensorObject = new JSONObject();
        sensorObject.put("Sensor", sensorDetails);
        URL url = new URL("http://localhost:8080/SensorController/addSensor");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = sensorDetails.toString();
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        con.disconnect();
        return true;
    }

    @Override
    public synchronized int increment() throws Exception {
        count++;
        return count;

    }

    @Override
    public String getLastId() throws Exception {
        HttpURLConnection con = null;
        StringBuffer content = null;
        try {
            URL url = new URL("http://localhost:8080/SensorController/getLastID");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            String contentType = con.getHeaderField("Content-Type");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return content.toString();
    }

    @Override
    public List<Sensor> getAllSensorDetails() throws Exception {

        URL obj;
        List<Sensor> list = null;
        obj = new URL("http://localhost:8080/SensorController/getAllSensorDetails");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String result = response.toString();
            JSONArray array = new JSONArray(result);

            list = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject jobj = array.getJSONObject(i);

                Sensor s = new Sensor();
                s.setCo2Level(jobj.getInt("co2Level"));
                s.setFloorNumber(jobj.getInt("floorNumber"));
                s.setRoomNumber(jobj.getInt("roomNumber"));
                s.setSensorId(String.valueOf(jobj.getString("sensorId")));
                s.setSmokeLevel(jobj.getInt("smokeLevel"));
                s.setStatus(String.valueOf(jobj.getString("status")));
                list.add(s);
            }
        } else {
            System.out.println("Error Occured");
        }
        return list;
    }

    @Override
    public Sensor getSensorDetailsAccordingToID(String sensorId) throws Exception {

        Sensor sensor = new Sensor();

        URL obj = new URL("http://localhost:8080/SensorController/getSensorDetailsAccordingToID/" + sensorId);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            if ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String result = response.toString();

            JSONObject jobj = new JSONObject(result);
            sensor.setCo2Level(jobj.getInt("co2Level"));
            sensor.setFloorNumber(jobj.getInt("floorNumber"));
            sensor.setRoomNumber(jobj.getInt("roomNumber"));
            sensor.setSensorId(String.valueOf(jobj.getString("sensorId")));
            sensor.setSmokeLevel(jobj.getInt("smokeLevel"));
            sensor.setStatus(String.valueOf(jobj.getString("status")));
            
            System.out.println("GG "+sensor.getStatus());
               

        } else {
            System.out.println("Error Occured");
        }
        return sensor;
    }

    @Override
    public boolean addUser(User user) throws Exception {
        
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", user.getUsername());
        userDetails.put("email", user.getEmail());
        userDetails.put("phoneNo", user.getPhoneNo());
        userDetails.put("password", user.getPassword());
        JSONObject userObject = new JSONObject();
        userObject.put("User", userDetails);
        URL url = new URL("http://localhost:8080/SensorController/addUser");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = userDetails.toString();
        
         try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        con.disconnect();
        return true;
    }

}
