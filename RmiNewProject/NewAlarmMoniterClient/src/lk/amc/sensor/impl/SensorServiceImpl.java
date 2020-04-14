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
import lk.amc.dto.Sensor;
import lk.amc.service.SensorService;
import org.json.simple.JSONObject;

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
    public String getLastId()throws Exception {
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

}
