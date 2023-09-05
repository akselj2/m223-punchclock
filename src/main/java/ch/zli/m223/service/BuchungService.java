package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.lang.Long;

import ch.zli.m223.model.Buchung;

/**
 * @author Aksel Jessen
 * @date: 5.09.2023
 * @classname: BuchungService.java
 */

@ApplicationScoped
public class BuchungService {

    @Inject
    private EntityManager entityManager;
    @Inject
    JsonWebToken jwt;

    /**
     * @param buchung requires Buchung entity data
     * @return returns the newly created Buchung
     * @throws Exception
     */
    @Transactional
    public Buchung createBuchung(Buchung buchung) throws Exception {
        try {
            entityManager.persist(buchung);
            return buchung;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 
     * @return returns a list of all Buchungen
     * @throws Exception
     */
    public List<Buchung> findAll() throws Exception {
        try {
            var query = entityManager.createQuery("FROM Buchung", Buchung.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }

        
    }

    /**
     * 
     * @param id id for the request user's bookings.
     * @return returns a list of all Buchungen of a specific user.
     * @throws Exception
     */
    public List<Buchung> findAllByUser(String id) throws Exception {
        try {
            List<Buchung> bookings = new ArrayList<>();
            var query = entityManager.createQuery("FROM Buchung", Buchung.class);
            for (Buchung booking : query.getResultList()) {
                if (booking.getUser().getId().toString().equals(id)) {
                    bookings.add(booking);
                } 

            }
            return bookings;
        } catch (Exception e) {
            throw e;
        }
        

    }

    /**
     * 
     * @param id id of booking to be found
     * @return returns ResultList with data from query.
     * @throws Exception
     */
    @Transactional
    public Buchung findById(Long id) throws Exception {
        try {
            var query = entityManager.find(Buchung.class, id);
            return query;
        } catch (Exception e) {
            throw e;
        }
        
    }

    /**
     * 
     * @param id id of the booking to be deleted.
     */
    @Transactional
    public void delete(Long id) {
        Buchung booking = entityManager.find(Buchung.class, id);
        if (booking.getUser().getId().toString().equals(jwt.getName()) || jwt.getGroups().iterator().next().equals("Admin")) {
            entityManager.remove(booking);
        } else {
            System.out.println("didn't work :(");
        }
    }

    /**
     * @param id id of the Booking to be deleted.
     * @param booking
     * @return
     */
    @Transactional
    public Response editBooking(Long id, Buchung booking) {
        try {
            if (booking.getUser().getId().toString().equals(jwt.getName())
                    || jwt.getGroups().iterator().next().equals("Admin")) {
                entityManager.merge(booking);
                return Response.ok(booking).build();
            } else {
                // Nicht eigene Buchung und oder Kein Admin zum bearbeiten
                return Response.status(Response.Status.BAD_REQUEST).build();

            }

        } catch (Exception e) {
            throw e;
        }
    }

    
}
