package ch.zli.m223.controller;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.UserEntity;
import ch.zli.m223.service.UserService;

@Path("/user")
@Tag(name = "UserController", description = "stuff")
public class UserController {

    @Inject
    UserService userService;

    @Inject
    JsonWebToken jwt;


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

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Show a user", description = "Returns a single users data using his ID")
    public Response getUserById(Long id) {
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

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "creates a new user or registers a new user", description = " as mention in summary")
    public UserEntity create(UserEntity user) {
        return userService.create(user);
    }
}
