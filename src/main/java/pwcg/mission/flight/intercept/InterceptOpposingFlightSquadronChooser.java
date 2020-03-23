package pwcg.mission.flight.intercept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pwcg.campaign.plane.Role;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.RandomNumberGenerator;
import pwcg.mission.OpposingSquadronChooser;
import pwcg.mission.flight.IFlightInformation;
import pwcg.mission.target.TargetCategory;
import pwcg.mission.target.TargetDefinition;

public class InterceptOpposingFlightSquadronChooser
{
    private IFlightInformation playerFlightInformation;

    public InterceptOpposingFlightSquadronChooser(IFlightInformation playerFlightInformation)
    {
        this.playerFlightInformation = playerFlightInformation;
    }

    public List<Squadron> getOpposingSquadrons() throws PWCGException
    {
        int numSquadronsToGet = determineNumberOfOpposingFlights();
        List<Role> opposingFlightRoles = determineOpposingRoles();
        OpposingSquadronChooser opposingSquadronChooser = new OpposingSquadronChooser(playerFlightInformation, opposingFlightRoles, numSquadronsToGet);
        return opposingSquadronChooser.getOpposingSquadrons();            
    }

    private List<Role> determineOpposingRoles()
    {
        TargetDefinition interceptedFlightTargetDefinitions = playerFlightInformation.getTargetDefinition().getLinkedFlightTargetDefinition();
        if (interceptedFlightTargetDefinitions.getTargetCategory() == TargetCategory.TARGET_CATEGORY_STRATEGIC)
        {
            return new ArrayList<>(Arrays.asList(Role.ROLE_BOMB, Role.ROLE_STRAT_BOMB, Role.ROLE_RECON));
        }
        else
        {
            return new ArrayList<>(Arrays.asList(Role.ROLE_BOMB, Role.ROLE_ATTACK, Role.ROLE_DIVE_BOMB, Role.ROLE_STRAT_BOMB, Role.ROLE_RECON));
        }
    }

    private int determineNumberOfOpposingFlights() 
    {
        int numOpposingFlights = 1 + RandomNumberGenerator.getRandom(3);
        if (playerFlightInformation.getCampaign().isCoop())
        {
            numOpposingFlights = 1;
        }
        return numOpposingFlights;
    }
}
