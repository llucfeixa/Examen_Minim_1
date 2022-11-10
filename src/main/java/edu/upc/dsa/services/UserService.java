package edu.upc.dsa.services;

import edu.upc.dsa.data.MyObjectManager;
import edu.upc.dsa.data.MyObjectManagerImpl;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.models.Credentials;
import edu.upc.dsa.models.MyObject;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserRegister;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserService {
    private MyObjectManager om;

    public UserService() { this.om = MyObjectManagerImpl.getInstance(); }

    @POST
    @ApiOperation(value = "create a new User", notes = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Mail already registered")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(UserRegister user) throws UserExistingException {
        if (user.getName() == null || user.getSurname() == null || user.getDate() == null || user.getCredentials().getMail() == null || user.getCredentials().getPassword() == null) {
            return Response.status(500).entity(user).build();
        }
        try {
            this.om.register(user.getName(), user.getSurname(), user.getDate(), user.getCredentials());
        }
        catch (UserExistingException e) {
            return Response.status(501).entity(user).build();
        }
        return Response.status(200).entity(user).build();
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Gets the users ordered alphabetically")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        List<User> users = this.om.usersByAlphabet();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {
        };
        return Response.status(200).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "login of a User", notes = "Login of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Error")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(Credentials credentials) {
        if (credentials.getMail() == null || credentials.getPassword() == null) {
            return Response.status(500).build();
        }
        int i = this.om.login(credentials);
        if (i == -1) {
            return Response.status(501).build();
        }
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get all Objects of a User", notes = "Gets the list of objects of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObject.class, responseContainer = "List"),
    })
    @Path("/{id}/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsByUser(@PathParam("id") String mail) {
        List<MyObject> myObjects = this.om.objectsByUser(mail);
        GenericEntity<List<MyObject>> entity = new GenericEntity<List<MyObject>>(myObjects) {
        };
        return Response.status(200).entity(entity).build();
    }
}
