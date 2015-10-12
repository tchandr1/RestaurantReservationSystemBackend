/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.egen.restaurantreservationbackend.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Thanusha
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(edu.iit.sat.egen.restaurantreservationbackend.service.CustomerFacadeREST.class);
        resources.add(edu.iit.sat.egen.restaurantreservationbackend.service.NewCrossOriginResourceSharingFilter.class);
        resources.add(edu.iit.sat.egen.restaurantreservationbackend.service.OwnerFacadeREST.class);
        resources.add(edu.iit.sat.egen.restaurantreservationbackend.service.TablereserveFacadeREST.class);
    }
    
}
