package pwcg.mission.flight;

import pwcg.core.exception.PWCGException;
import pwcg.mission.flight.waypoint.WaypointAction;
import pwcg.mission.flight.waypoint.missionpoint.MissionPoint;
import pwcg.mission.ground.builder.AAASpotterBuilder;
import pwcg.mission.ground.org.GroundUnitCollection;

public class FlightSpotterBuilder
{

    public static void createSpotters(IFlight playerFlight, FlightInformation flightInformation) throws PWCGException
    {
        AAASpotterBuilder spotterBuilder = new AAASpotterBuilder(flightInformation);
        for (MissionPoint missionPoint : playerFlight.getWaypointPackage().getFlightMissionPoints())
        {
            if (missionPoint.getAction() == WaypointAction.WP_ACTION_PATROL)
            {
                GroundUnitCollection spotter = spotterBuilder.createAAASpotterBattery(missionPoint.getPosition());
                if (spotter != null)
                {
                    playerFlight.getMission().getMissionGroundUnitBuilder().addFlightSpecificGroundUnit(spotter);
                }
            }
        }
    }

}
