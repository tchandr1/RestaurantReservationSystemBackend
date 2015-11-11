/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.egen.restaurantreservationbackend.service;

import edu.iit.sat.egen.restaurantreservationbackend.Customer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
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
@Path("customers")
public class CustomerFacadeREST extends AbstractFacade<Customer> {
    @PersistenceContext(unitName = "rrPU")
    private EntityManager em;
    private static final Logger LOG = Logger.getLogger(CustomerFacadeREST.class.getName());
    
    
    
    
    //JDBC resource will be tchandr1Mp2DS
    @Resource(lookup = "jdbc/rrDS")
    private DataSource dataSource;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Path("/insert")
    @Override
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public void create(Customer entity) {
       // super.create(entity);
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
            try{
                LOG.info("Entered try block of create method");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql:///restaurant", "itmd4515", "itmd4515");
                    ps = conn.prepareStatement("insert into customer(firstname,lastname,arrivaldate,arrivaltime,peoplecount,phonenumber,email) values (?,?,?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                   
                /*    String formDate;
                   formDate =entity.getArrivaldate();
                   Date utilDate =  new SimpleDateFormat("yyyy-MM-dd").parse(formDate);
                   java.sql.Date mySqlDate;
            mySqlDate =new java.sql.Date(utilDate.getTime());*/
                    
                    
                    
                    ps.setString(1, entity.getFirstname());
                    ps.setString(2, entity.getLastname());
                    ps.setString(3, entity.getArrivaldate());
                    ps.setString(4, entity.getArrivaltime());
                    ps.setInt(5,entity.getPeoplecount());
                    ps.setString(6,entity.getPhonenumber());
                    ps.setString(7,entity.getEmail());
                    int i =ps.executeUpdate();
                    rs=ps.getGeneratedKeys();
                    
            
            if(i>0){
                LOG.info("Inserted into customer table");
                if(rs.next()){
                    entity.setCustomerId(rs.getInt(1));
                    LOG.info("customerId is set");
                }
                
            }else{
                LOG.info("Not inserted into customer table");
            }
             
          }catch(Exception e){
              throw new RuntimeException(e);
            //  LOG.info("Catch Exception while inserting:"+e);
          }
      
     
    }

    @PUT
    @Path("/update")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public void edit(@PathParam("id") Integer id, Customer entity) {
       // super.edit(entity);
        
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
            try{
                LOG.info("Entered try block of UPDATE method");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql:///restaurant", "itmd4515", "itmd4515");
                    ps = conn.prepareStatement("UPDATE customer SET arrivaldate=?,arrivaltime=?,peoplecount=?,phonenumber=? WHERE customerId = ?");
                 
                    ps.setString(1, entity.getArrivaldate());
                    ps.setString(2, entity.getArrivaltime());
                    ps.setInt(3,entity.getPeoplecount());
                    ps.setString(4,entity.getPhonenumber());
                    
                    ps.setInt(5,entity.getCustomerId());
                    
                   
                    int i =ps.executeUpdate();
                   
                               if(i>0){
                                    LOG.info("Updated into customer table");
                                     
                    LOG.info("customerId is set");
                                                   
                                }else{
                                    LOG.info("Not Updated into customer table");
                                }
             
          }catch(Exception e){
              LOG.info("Catch Exception while UPDATING:"+e);
          }
      
        
        
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("/findId/{id}")
    @Produces({"application/json"})
    public Customer find(@PathParam("id") Integer id) {
        Customer cust =new Customer();
       // return super.find(id);
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
            try{
                LOG.info("Entered try block of find method");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql:///restaurant", "itmd4515", "itmd4515");
                
                ps = conn.prepareStatement("SELECT customerId,firstname,lastname,arrivaldate,arrivaltime,peoplecount,phonenumber,email from customer WHERE customerId=?");
                ps.setInt(1, id);
                
                rs=ps.executeQuery();
                
                if(rs.next()){
                    LOG.info("Person with the entered id IS present");
                    cust.setCustomerId(rs.getInt("CUSTOMERID"));
                    cust.setFirstname(rs.getString("FIRSTNAME"));
                    cust.setLastname(rs.getString("LASTNAME"));
                    cust.setArrivaldate(rs.getString("ARRIVALDATE"));
                    cust.setArrivaltime(rs.getString("ARRIVALTIME"));
                    cust.setPeoplecount(rs.getInt("PEOPLECOUNT"));
                    cust.setPhonenumber(rs.getString("PHONENUMBER"));
                    cust.setEmail(rs.getString("EMAIL"));
                }else{
                     LOG.info("Person with the entered id NOT present");
                }
            
            }catch(Exception e){
                throw new RuntimeException(e);
               // LOG.info("Catch Exception while fetching record from database");
            }
            return cust;
    }

    @GET
  //  @Path("/findallcustomers")
    @Override
    @Produces({"application/json"})
    public List<Customer> findAll() {
       // return super.findAll();
         List<Customer> custList =new ArrayList<Customer>();
       // return super.find(id);
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
            try{
                LOG.info("Entered try block of findAll method");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql:///restaurant", "itmd4515", "itmd4515");
                
                ps = conn.prepareStatement("SELECT customerId,firstname,lastname,arrivaldate,arrivaltime,peoplecount,phonenumber,email from customer");
                rs=ps.executeQuery();
                
                    while(rs.next()){
                        Customer cust =new Customer();
                        cust.setCustomerId(rs.getInt("CUSTOMERID"));
                        cust.setFirstname(rs.getString("FIRSTNAME"));
                        cust.setLastname(rs.getString("LASTNAME"));
                        cust.setArrivaldate(rs.getString("ARRIVALDATE"));
                        cust.setArrivaltime(rs.getString("ARRIVALTIME"));               
                        cust.setPeoplecount(rs.getInt("PEOPLECOUNT"));
                        cust.setPhonenumber(rs.getString("PHONENUMBER"));
                        cust.setEmail(rs.getString("EMAIL"));

                        custList.add(cust);
                     }
            
            }catch(Exception e){
               // e.printStackTrace();
              //  throw new AppException("Error in fetchinf all records:"e.getCause());
                LOG.info("Catch Exception while fetching ALL records from database");
            }
           /* finally{
                conn.close();
                ps.close();
                rs.close();
            }*/
            return custList;
   
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
