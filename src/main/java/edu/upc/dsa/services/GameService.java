package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.GameCreate;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.UserPoints;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")
public class GameService {
    private MyGameManager gm;

    public GameService() { this.gm = MyGameManagerImpl.getInstance(); }

    @POST
    @ApiOperation(value = "create a new Game", notes = "Creates a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = GameCreate.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newGame(GameCreate gameCreate) {
        if (gameCreate.getGameCreateId() == null || gameCreate.getDescription() == null || gameCreate.getLevels() == 0) {
            return Response.status(500).entity(gameCreate).build();
        }
        this.gm.createGame(gameCreate.getGameCreateId(), gameCreate.getDescription(), gameCreate.getLevels());
        return Response.status(200).entity(gameCreate).build();
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Gets the users ordered by points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = UserPoints.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Game not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByOrder(@PathParam("id") String gameId) throws NotUserOrGameException {
        try {
            List<UserPoints> usersPoints = this.gm.usersByPoints(gameId);
            GenericEntity<List<UserPoints>> entity = new GenericEntity<List<UserPoints>>(usersPoints) {
            };
            return Response.status(200).entity(entity).build();
        }
        catch (NotUserOrGameException e) {
            return Response.status(404).build();
        }
    }
}
