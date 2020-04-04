/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.service.custom;

import lk.amc.dto.Sensor;
import lk.amc.service.SuperService;



/**
 *
 * @author Dakshika
 */
public interface SensorService extends SuperService{
    
    public boolean addSensor(Sensor s) throws Exception;
    
    public String getLatId() throws Exception;

    public Sensor getSensorDetailsAccordingToID(String sensorId)throws Exception;
}
