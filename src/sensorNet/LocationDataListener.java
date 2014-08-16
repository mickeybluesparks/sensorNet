/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sensorNet;

/**
 *
 * @author Mike
 */
public interface LocationDataListener {
    
    enum actionRequest {ADD, DELETE, EDIT, UPDATE}
    
    public void locationDatabaseAction(actionRequest request);
}
