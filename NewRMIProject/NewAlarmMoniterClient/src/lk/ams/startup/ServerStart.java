/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ams.startup;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.amc.sensor.impl.SensorServiceImpl;
import lk.amc.service.SensorService;
import lk.amc.view.LoginForm;




/**
 *
 * @author Gnanod
 */
public class ServerStart {
 
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(5050);
            SensorService stub = new SensorServiceImpl();
            registry.rebind("sensor", stub);
            System.out.println("Server Has Been Started");
//            LoginForm r1=new LoginForm();
//            r1.setVisible(true);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
