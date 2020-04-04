/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.proxy;

import java.rmi.Naming;
import lk.amc.service.ServiceFactory;
import lk.amc.service.SuperService;
import lk.amc.service.custom.SensorService;



/**
 *
 * @author Dakshika
 */
public class ProxyHandler implements ServiceFactory{
    
    private static ProxyHandler proxyHandler;
    private SensorService sensorService;
    private ServiceFactory serviceFactory;
    
    private ProxyHandler() throws Exception{
        serviceFactory =(ServiceFactory) Naming.lookup("rmi://localhost:5050/sensor");
        sensorService = (SensorService) serviceFactory.getService(ServiceTypes.SENSOR);
    }
    
    public static ProxyHandler getInstance()throws Exception{
        if(proxyHandler==null){
            proxyHandler= new ProxyHandler();
        }
        
        return proxyHandler;
    }

    @Override
    public SuperService getService(ServiceTypes type) throws Exception {
        switch(type){
            case SENSOR :
                return sensorService;
            default :
                return null;
        }
    }
    
    
    
}
