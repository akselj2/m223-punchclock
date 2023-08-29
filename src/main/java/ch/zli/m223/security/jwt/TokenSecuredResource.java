package ch.zli.m223.security.jwt;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/secured")
@RequestScoped
public class TokenSecuredResource {
    
    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim(standard = Claims.birthdate)
    String birthdate;

    @GET()
    @Path("permit-all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx);
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + jwt.getClaim("birthdate").toString();
    }

    @GET
    @Path("roles-allowed-admin")
    @RolesAllowed({"Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowedAdmin(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + birthdate;
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if(!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JWT names do not match!");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello + %s,"
            + " isHttps: %s,"
            + " authScheme: %s,"
            + " hasJWT: %s",
            name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJWT());
    }

    private boolean hasJWT() {
        return jwt.getClaimNames() != null;
    }

}
