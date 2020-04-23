/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.service;

import java.rmi.Remote;
import java.util.List;
import lk.amc.dto.Sensor;
import lk.amc.dto.User;

/**
 *
 * @author Dakshika
 */
public interface SensorService extends Remote{

    public boolean addSensor(Sensor sensor)throws Exception;
    
   
    public int  increment()throws Exception;

    public String getLastId()throws Exception;

    public List<Sensor> getAllSensorDetails()throws Exception;

    public Sensor getSensorDetailsAccordingToID(String sensorId)throws Exception;
    
    public boolean addUser(User user) throws Exception;
}
