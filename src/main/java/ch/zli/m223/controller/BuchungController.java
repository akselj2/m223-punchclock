package ch.zli.m223.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.lang.Long;

import ch.zli.m223.model.Buchung;
import ch.zli.m223.service.BuchungService;

@Path("/buchungen")
@RequestScoped
@Tag(name = "Buchungen", description = "Handling of bookings")
public class BuchungController {

    @Inject
    BuchungService buchungService;

    @Inject
    JsonWebToken jwt;

    /**
     * 
     * @return returns either all bookings if you're an admin, and only those that you've booked if you're a member. otherwise, nothing gets returned.
     * @throws Exception
     */
    @GET
    @RolesAllowed({"Admin", "Member"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Bookings.", description = "Returns a list of all bookings.")
    public List<Buchung> index() throws Exception {
        try {
            var userId = jwt.getName();
            var groups = jwt.getGroups();
            if (groups.iterator().next().equals("Admin")) {
                return buchungService.findAll();
            } else {
                return buchungService.findAllByUser(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param id the id of a booking
     * @return returns the booking that's requested.
     * @throws Exception
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Finds booking by id", description = "Search for a specific booking by it's id")
    public Buchung getBuchungById(Long id) throws Exception {
        try {
            return buchungService.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 
     * @param booking object of the booking entity
     * @return returns the newly created booking
     * @throws Exception
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new booking.", description = "Creates a new booking and returns the newly added booking.")
    public Buchung create(Buchung booking) throws Exception {
        try {
            return buchungService.createBuchung(booking);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * deletes the requested booking
     * @param id id of the booking
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "Member"})
    @Operation(summary = "Deletes a booking.", description = "Deletes a previously created booking from the database.")
    public void delete(Long id) {
        try {
            buchungService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param id id of the booking
     * @param booking object of type booking with all of it's details
     * @return returns updated booking
     * @throws Exception
     */
    @PUT
    @Path("/edit/{id}")
    @RolesAllowed({"Admin", "Member"})
    @Operation(summary = "Updates a booking.", description = "Updates an existing booking in the database.")
    public Response editEntry(Long id, Buchung booking) throws Exception {
        try {
            return buchungService.editEntry(id, booking);
        } catch (Exception e) {
            throw e;
        }
    }

}
