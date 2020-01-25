package pwcg.mission.flight.divebomb;

import pwcg.core.exception.PWCGException;
import pwcg.mission.flight.Flight;
import pwcg.mission.flight.FlightPayloadBuilder;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.IFlightInformation;
import pwcg.mission.flight.initialposition.FlightPositionSetter;
import pwcg.mission.flight.waypoint.WaypointPriority;
import pwcg.mission.flight.waypoint.begin.AirStartWaypointFactory.AirStartPattern;
import pwcg.mission.flight.waypoint.begin.IngressWaypointFactory.IngressWaypointPattern;
import pwcg.mission.flight.waypoint.missionpoint.IMissionPointSet;
import pwcg.mission.flight.waypoint.missionpoint.MissionPointSetFactory;
import pwcg.mission.mcu.McuWaypoint;

public class DiveBombingFlight extends Flight implements IFlight
{
    public DiveBombingFlight(IFlightInformation flightInformation)
    {
        super (flightInformation);
    }

    public void createFlight() throws PWCGException
    {
        initialize(this);
        createWaypoints();
        FlightPositionSetter.setFlightInitialPosition(this);
        WaypointPriority.setWaypointsNonFighterPriority(this);
        setFlightPayload();
    }

    @Override
    public IMissionPointSet createFlightSpecificWaypoints(McuWaypoint ingressWaypoint) throws PWCGException
    {
        DiveBombingWaypointFactory missionWaypointFactory = new DiveBombingWaypointFactory(this);
        IMissionPointSet missionWaypoints = missionWaypointFactory.createWaypoints(ingressWaypoint);
        return missionWaypoints;
    }

    private void createWaypoints() throws PWCGException
    {
        MissionPointSetFactory.createStandardMissionPointSet(this, AirStartPattern.AIR_START_NEAR_AIRFIELD, IngressWaypointPattern.INGRESS_NEAR_FRONT);
    }

    private void setFlightPayload() throws PWCGException
    {
        FlightPayloadBuilder flightPayloadHelper = new FlightPayloadBuilder(this);
        flightPayloadHelper.setFlightPayload();
    }
}
