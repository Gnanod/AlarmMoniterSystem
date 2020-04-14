/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.controller;

import lk.amc.ProxyHandler.ProxyHandler;
import lk.amc.dto.Sensor;
import lk.amc.service.SensorService;

/**
 *
 * @author Dakshika
 */
public class SensorController {

    public static Sensor getSensorDetailsAccordingToID(String sensorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SensorController() {
        
    }

    
    public  static int increment() throws Exception {
        SensorService sensorService = ProxyHandler.getInstance().getService();
        return sensorService.increment();
    }

    public static String getLastId() throws Exception {
        SensorService sensorService = ProxyHandler.getInstance().getService();
        return sensorService.getLastId();
    }

    
    public static boolean addSensor(Sensor sensor) throws Exception {
        SensorService sensorService = ProxyHandler.getInstance().getService();
        return sensorService.addSensor(sensor);
    }
    
}
