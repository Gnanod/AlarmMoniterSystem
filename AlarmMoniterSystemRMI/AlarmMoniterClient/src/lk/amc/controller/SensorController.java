/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.controller;

import lk.amc.dto.Sensor;
import lk.amc.proxy.ProxyHandler;
import lk.amc.service.ServiceFactory;
import lk.amc.service.custom.SensorService;

/**
 *
 * @author Dakshika
 */
public class SensorController {

    public static boolean addSensor(Sensor s) throws Exception {
        SensorService service = (SensorService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceTypes.SENSOR);
        return service.addSensor(s);
    }

    public static String getLastId() throws Exception {
        SensorService service = (SensorService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceTypes.SENSOR);
        return service.getLatId();
    }
    
    public static Sensor getSensorDetailsAccordingToID(String sensorId)throws Exception{
         SensorService service = (SensorService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceTypes.SENSOR);
        return service.getSensorDetailsAccordingToID(sensorId);
    }
}
