/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ams.service.impl;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import lk.amc.service.ServiceFactory;
import lk.amc.service.SuperService;
import lk.ams.custom.impl.SensorServiceImpl;


/**
 *
 * @author Dakshika
 */
public class ServiceFactoryImpl extends UnicastRemoteObject implements ServiceFactory{

    private static ServiceFactory serviceFactory;
    
    private ServiceFactoryImpl()throws RemoteException{
        
    }

    public static ServiceFactory getInstance() throws RemoteException {
        if(serviceFactory == null){
            serviceFactory = new ServiceFactoryImpl();
        }
        
        return serviceFactory;
    }

    @Override
    public SuperService getService(ServiceTypes type) throws Exception {
        switch(type){
            case SENSOR:
                return new SensorServiceImpl();
                default:
                    return null;
                        
        }
    }
    
}
