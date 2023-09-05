package ch.zli.m223.controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.UserService;

/**
 * @author Aksel Jessen
 * @date: 5.09.2023
 * @classname: UserController.java
 */

@Path("/user")
@Tag(name = "UserController", description = "stuff")
public class UserController {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;


    /**
     * 
     * @return calls userService.findAll() to retrieve all Users in the database
     * @throws Exception
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Users.", description = "Returns a list of all users on this platform")
    public List<UserEntity> index() throws Exception {
        try {
            return userService.findAll();
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * 
     * @param id user ID
     * @return returns a server Response if it can find the user by it's id. or if they are an admin.
     * @throws Exception 
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Show a user", description = "Returns a single users data using his ID")
    public Response getUserById(Long id) throws Exception {
        try {
            String userId = id + "";
            if(userId.equals(jwt.getName()) || jwt.getGroups().iterator().next().equals("Admin")) {
                return Response.ok(userService.findById(userId)).build();
            }

            return Response.ok("Not valid user").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * 
     * @param user object of the UserEntity class
     * @return returns the newly created / registered user.
     * @throws Exception
     */
    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "creates a new user or registers a new user", description = " as mention in summary")
    public UserEntity create(UserEntity user) throws Exception{
        try {
            return userService.create(user);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 
     * @param id id of the requested user
     * @return returns a response confirming the user has been deleted, unless it wasn't a valid user.
     * @throws Exception
     */
    @Path("/{id}")
    @DELETE
    @RolesAllowed({"Admin"})
    @Operation (summary = "Deletes a specific user", description = "Deletes a user with it's respective id.")
    public Response delete(int id) throws Exception {
        try {
            String userId = id + "";
            if(userId.equals(jwt.getName()) || jwt.getGroups().iterator().next().equals(userId)) {
                userService.delete(id);
                return Response.ok("Deleted User").build();
            }

            return Response.ok("Not Valid User").build();
        } catch (Exception e) {
            throw e;
        }

        

    }           
    
    /**
     * 
     * @param id id of the user to be updated / replaced.
     * @param user requires the data of a user entity to replace the preexisting user.
     * @return returns the updated users profile.
     */
    @Path("/{id}")
    @PUT
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a Users profile", description = "Updates and returns the recently updated profile of a specific user.")
    public UserEntity update(Long id, UserEntity user) {
        try {
            return userService.update(id, user);
        } catch (Exception e) {
            throw e;
        }
    }
}
