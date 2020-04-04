/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ams.custom.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import lk.amc.common.ParameterStringBuilder;
import lk.amc.dto.Sensor;
import lk.amc.service.custom.SensorService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dakshika
 */
public class SensorServiceImpl extends UnicastRemoteObject implements SensorService {

    public SensorServiceImpl() throws RemoteException {

    }

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
    public String getLatId() throws Exception {
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

//            System.out.println("Read" + content.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                System.out.println(con);
//                
//                int status = con.getResponseCode();
//                con.setInstanceFollowRedirects(false);
//                HttpURLConnection.setFollowRedirects(false);
//                if (status == HttpURLConnection.HTTP_MOVED_TEMP
//                        || status == HttpURLConnection.HTTP_MOVED_PERM) {
////                    String location = con.getHeaderField("Location");
//                    URL newUrl = new URL("");
//                    con = null;
//                    con.setDoOutput(false);
//                    con.disconnect();
//                }
//                System.out.println(con);
//            }
//        }
        return content.toString();
    }

    @Override
    public Sensor getSensorDetailsAccordingToID(String sensorId) throws Exception {

        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL("http://localhost:8080/SensorController/getSensorDetailsAccordingToID/" + sensorId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.close();
            
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

//        
//        URL url = new URL("http://localhost:8080/SensorController/getSensorDetailsAccordingToID");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//        //Request Setup
//        con.setRequestMethod("GET");
//        con.setConnectTimeout(5000);
//        con.setReadTimeout(5000);
//
//        int status = con.getResponseCode();
//        System.out.println("GGG"+status);
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("sensorId", sensorId);
//
//        System.out.println("connnnn" + con);
//        con.setUseCaches(false);
//        con.setDoOutput(true);
//
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());
//        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//        out.flush();
//        out.close();
//        con.setRequestProperty("Content-Type", "application/json");
//        String contentType = con.getHeaderField("Content-Type");
//
//        int status = con.getResponseCode();
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer content = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//            content.append(inputLine);
//        }
//        in.close();
//        con.disconnect();
//        System.out.println("Cotent" + content.toString());
        return null;
    }

}
