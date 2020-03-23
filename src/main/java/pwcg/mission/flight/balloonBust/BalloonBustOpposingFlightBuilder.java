package pwcg.mission.flight.balloonBust;

import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.IFlightInformation;
import pwcg.mission.flight.balloondefense.BalloonDefensePackage;
import pwcg.mission.flight.plot.FlightInformationFactory;

public class BalloonBustOpposingFlightBuilder
{
    private IFlightInformation playerFlightInformation;

    public BalloonBustOpposingFlightBuilder(IFlightInformation playerFlightInformation)
    {
        this.playerFlightInformation = playerFlightInformation;
    }

    public List<IFlight> buildOpposingFlights() throws PWCGException
    {
        BalloonBustOpposingFlightSquadronChooser opposingSquadronChooser = new BalloonBustOpposingFlightSquadronChooser(playerFlightInformation);
        List<Squadron> opposingSquadrons = opposingSquadronChooser.getOpposingSquadrons();            
        return createOpposingFlights(opposingSquadrons);
    }
    
    private List<IFlight> createOpposingFlights(List<Squadron> opposingSquadrons) throws PWCGException
    {
        List<IFlight> opposingFlights = new ArrayList<>();
        for (Squadron squadron : opposingSquadrons)
        {
            IFlight opposingFlight = createOpposingFlight(squadron);
            if (opposingFlight != null)
            {
                opposingFlights.add(opposingFlight);
            }
        }
        return opposingFlights;
    }

    private IFlight createOpposingFlight(Squadron opposingSquadron) throws PWCGException
    {
        IFlight interceptOpposingFlight = null;

        String opposingFieldName = opposingSquadron.determineCurrentAirfieldName(playerFlightInformation.getCampaign().getDate());
        if (opposingFieldName != null)
        {
            interceptOpposingFlight = buildOpposingFlight(opposingSquadron);
        }
        
        return interceptOpposingFlight;
    }

    private IFlight buildOpposingFlight(Squadron opposingSquadron) throws PWCGException 
    {
        FlightTypes opposingFlightType = FlightTypes.BALLOON_DEFENSE;
        IFlightInformation opposingFlightInformation = FlightInformationFactory.buildAiFlightInformation(
                opposingSquadron, playerFlightInformation.getMission(), opposingFlightType);
        
        IFlight opposingFlight = buildOpposingFlight(opposingFlightInformation);
        opposingFlight.createFlight();
        return opposingFlight;
    }
    
    private IFlight buildOpposingFlight(IFlightInformation opposingFlightInformation) throws PWCGException
    {
        BalloonDefensePackage balloonDefensePackage = new BalloonDefensePackage(opposingFlightInformation);
        return balloonDefensePackage.createPackage();
    }
}
