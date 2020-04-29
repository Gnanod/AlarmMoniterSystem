/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.amc.dto;

import java.io.Serializable;

/**
 *
 * @author Dakshika
 */
public class Sensor implements Serializable {

    private String sensorId;
    private int floorNumber;
    private int roomNumber;
    private int smokeLevel;
    private int co2Level;
    private String status;

    public Sensor() {
    }

    public Sensor(String sensorId, int floorNumber, int roomNumber, int smokeLevel, int co2Level, String status) {
        this.sensorId = sensorId;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.smokeLevel = smokeLevel;
        this.co2Level = co2Level;
        this.status = status;
    }

    /**
     * @return the sensorId
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * @param sensorId the sensorId to set
     */
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * @return the floorNumber
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * @param floorNumber the floorNumber to set
     */
    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * @return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the smokeLevel
     */
    public int getSmokeLevel() {
        return smokeLevel;
    }

    /**
     * @param smokeLevel the smokeLevel to set
     */
    public void setSmokeLevel(int smokeLevel) {
        this.smokeLevel = smokeLevel;
    }

    /**
     * @return the co2Level
     */
    public int getCo2Level() {
        return co2Level;
    }

    /**
     * @param co2Level the co2Level to set
     */
    public void setCo2Level(int co2Level) {
        this.co2Level = co2Level;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
