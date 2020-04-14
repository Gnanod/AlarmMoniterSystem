/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.ProxyHandler;

import java.rmi.Naming;
import java.rmi.RemoteException;
import lk.amc.sensor.impl.SensorServiceImpl;
import lk.amc.service.SensorService;

/**
 *
 * @author Dakshika
 */
public class ProxyHandler {
    
    private static ProxyHandler proxyHandler;
    private SensorService sensorService;
  
    
    public static ProxyHandler getInstance()throws Exception{
        if(proxyHandler==null){
            proxyHandler= new ProxyHandler();
        }
        return proxyHandler;
    }
     
     private  ProxyHandler() throws Exception{
        sensorService =(SensorService) Naming.lookup("rmi://localhost:5050/sensor");
    }
     
     public SensorService getService() throws RemoteException{
         
         if(sensorService==null){
             sensorService = new  SensorServiceImpl();
         }
         return sensorService;
     }
}
