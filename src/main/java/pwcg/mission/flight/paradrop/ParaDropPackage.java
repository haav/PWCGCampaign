package pwcg.mission.flight.paradrop;

import pwcg.core.exception.PWCGException;
import pwcg.mission.flight.FlightBuildInformation;
import pwcg.mission.flight.FlightInformationFactory;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.IFlightInformation;
import pwcg.mission.flight.IFlightPackage;
import pwcg.mission.target.ITargetDefinitionBuilder;
import pwcg.mission.target.TargetDefinition;
import pwcg.mission.target.TargetDefinitionBuilderAirToGround;

public class ParaDropPackage implements IFlightPackage
{
    public ParaDropPackage()
    {
    }

    @Override
    public IFlight createPackage (FlightBuildInformation flightBuildInformation) throws PWCGException 
    {
        IFlightInformation flightInformation = FlightInformationFactory.buildFlightInformation(flightBuildInformation, FlightTypes.PARATROOP_DROP);
        TargetDefinition targetDefinition = buildTargetDefintion(flightInformation);

        ParaDropFlight paraDropFlight = new ParaDropFlight (flightInformation, targetDefinition);
        paraDropFlight.createFlight();
        return paraDropFlight;
    }

    private TargetDefinition buildTargetDefintion(IFlightInformation flightInformation) throws PWCGException
    {
        ITargetDefinitionBuilder targetDefinitionBuilder = new TargetDefinitionBuilderAirToGround(flightInformation);
        return targetDefinitionBuilder.buildTargetDefinition();
    }
}
