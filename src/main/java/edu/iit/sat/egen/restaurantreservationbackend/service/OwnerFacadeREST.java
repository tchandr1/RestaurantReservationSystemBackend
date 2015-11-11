/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.egen.restaurantreservationbackend.service;

import edu.iit.sat.egen.restaurantreservationbackend.Owner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Thanusha
 */
@Stateless
@Path("owner")
public class OwnerFacadeREST extends AbstractFacade<Owner> {
    @PersistenceContext(unitName = "rrPU")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(OwnerFacadeREST.class.getName());
    
    

    public OwnerFacadeREST() {
        super(Owner.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Owner entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") String id, Owner entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @POST
    @Path("/login/{id}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Owner find(@PathParam("id") String id) {
       // return super.find(id);
        Owner owner = new Owner();
          Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
                
         try{
                LOG.info("Entered try block of find owner method");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql:///restaurant", "itmd4515", "itmd4515");
                
                ps = conn.prepareStatement("SELECT username,passsword FROM owner where username=?");
                ps.setString(1, id);
                
                rs=ps.executeQuery();
                
                if(rs.next()){
                    LOG.info("Person with the entered username IS present");
                    owner.setUsername(rs.getString("USERNAME"));
                    owner.setPasssword(rs.getString("PASSSWORD"));
                   
                }else{
                     LOG.info("Person with the entered username NOT present");
                }
            
            }catch(Exception e){
                LOG.info("Catch Exception while fetching record from database"+e);
            }
            return owner;

    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Owner> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Owner> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
