/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.service;

import java.rmi.Remote;

/**
 *
 * @author Dakshika
 */
public interface ServiceFactory extends Remote{
    
    public enum ServiceTypes{
        SENSOR
    }
    
    public SuperService getService(ServiceTypes type) throws Exception;
}
