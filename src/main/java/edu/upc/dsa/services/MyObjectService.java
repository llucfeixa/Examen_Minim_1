package edu.upc.dsa.services;

import edu.upc.dsa.data.MyObjectManager;
import edu.upc.dsa.data.MyObjectManagerImpl;
import edu.upc.dsa.exceptions.BuyObjectException;
import edu.upc.dsa.models.MyObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/object", description = "Endpoint to Object Service")
@Path("/object")
public class MyObjectService {
    private MyObjectManager om;

    public MyObjectService() {
        this.om = MyObjectManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "create a new Object", notes = "Creates a new object")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObject.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newObject(MyObject myObject) {
        if (myObject.getId() == null || myObject.getName() == null || myObject.getDescription() == null || myObject.getCoins() == 0) {
            return Response.status(500).entity(myObject).build();
        }
        this.om.addObject(myObject.getId(), myObject.getName(), myObject.getDescription(), myObject.getCoins());
        return Response.status(200).entity(myObject).build();
    }

    @GET
    @ApiOperation(value = "get all Objects", notes = "Gets all objects ordered by descending price")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = MyObject.class, responseContainer = "List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObject() {
        List<MyObject> myObjects = this.om.objectsByPrice();
        GenericEntity<List<MyObject>> entity = new GenericEntity<List<MyObject>>(myObjects) {
        };
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "buy an Object", notes = "Buys an object for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Error Buying")
    })
    @Path("/{id}/{objectId}")
    public Response buyObject(@PathParam("id") String mail, @PathParam("objectId") String objectId) throws BuyObjectException {
        try {
            om.buyObject(mail, objectId);
        }
        catch (BuyObjectException e) {
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }
}
