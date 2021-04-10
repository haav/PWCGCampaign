package pwcg.mission.utils;

import pwcg.campaign.api.Side;
import pwcg.core.exception.PWCGException;
import pwcg.mission.Mission;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.target.TargetType;

public class MissionInformationUtils
{

    public static void verifyFlightTargets(Mission mission, FlightTypes expectedFlightType, TargetType expectedTargetType, Side side) throws PWCGException
    {
        boolean targetTypeFound = false;
        for (IFlight flight : mission.getMissionFlights().getAiFlightsForSide(side))
        {
            if (flight.getFlightType() == expectedFlightType)
            {
                targetTypeFound = true;
            }
        }

        assert (targetTypeFound);
    }

    public static void verifyAiFlightTypeInMission(Mission mission, FlightTypes flightType, Side side) throws PWCGException
    {
        boolean flightTypeFound = false;
        for (IFlight flight : mission.getMissionFlights().getAiFlightsForSide(side))
        {
            if (flight.getFlightInformation().getFlightType() == flightType)
            {
                flightTypeFound = true;
            }
        }
        assert (flightTypeFound);        
    }

}
