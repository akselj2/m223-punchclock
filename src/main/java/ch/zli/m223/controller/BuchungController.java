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

    /**
     * TODO: Include JWT in most files, don't forget visitor role.
     * TODO: AuthService !!!
     * Note: Shouldn't require too much work, i estimate about 1-2hours. 
     */

    @Inject
    BuchungService buchungService;

    @Inject
    JsonWebToken jwt;

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public Buchung create(Buchung entry) throws Exception {
        try {
            return buchungService.createBuchung(entry);
        } catch (Exception e) {
            throw e;
        }
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "Member"})
    @Operation(summary = "Deletes an entry.", description = "Deletes a previously created entry from the database.")
    public void delete(Long id) {
        try {
            buchungService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PUT
    @Path("/edit/{id}")
    @RolesAllowed({"Admin", "Member"})
    @Operation(summary = "Updates an entry.", description = "Updates an existing entry in the database.")
    public Response editEntry(Long id, Buchung entry) throws Exception {
        try {
            return buchungService.editEntry(id, entry);
        } catch (Exception e) {
            throw e;
        }
    }

}
