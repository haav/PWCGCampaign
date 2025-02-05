package pwcg.mission;

import java.util.TreeMap;

import pwcg.campaign.Campaign;
import pwcg.campaign.api.ICountry;
import pwcg.campaign.context.Country;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.factory.CountryFactory;
import pwcg.campaign.group.airfield.Airfield;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.campaign.squadron.Squadron;
import pwcg.campaign.squadron.SquadronManager;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.CoordinateBox;

public class MissionAirfieldBuilder
{
    private Campaign campaign;
    private Mission mission;
    private CoordinateBox structureBorders;
    private TreeMap<String, Airfield> fieldSet = new TreeMap<>();

    public MissionAirfieldBuilder (Mission mission, CoordinateBox structureBorders)
    {
        this.mission = mission;
        this.campaign = mission.getCampaign();
        this.structureBorders = structureBorders;
    }
    
    public MissionAirfields findFieldsForPatrol() throws PWCGException 
    {
        selectAirfieldsWithinMissionBoundaries();
        selectPlayerAirfields();
        
        return new MissionAirfields(fieldSet);
    }
    
    public MissionAirfields buildFieldsForPatrol() throws PWCGException 
    {
        selectAirfieldsWithinMissionBoundaries();
        selectPlayerAirfields();
        addAirfieldObjects();
        
        return new MissionAirfields(fieldSet);
    }

    private void selectAirfieldsWithinMissionBoundaries() throws PWCGException
    {
        for (Airfield airfield :  PWCGContext.getInstance().getMap(campaign.getCampaignMap()).getAirfieldManager().getAllAirfields().values())
        {
            if (structureBorders.isInBox(airfield.getPosition()))
            {
                fieldSet.put(airfield.getName(), airfield);
            }
        }
    }

    private void selectPlayerAirfields() throws PWCGException
    {
        for (SquadronMember player : mission.getParticipatingPlayers().getAllParticipatingPlayers())
        {
            Squadron squadron = player.determineSquadron();
            Airfield playerField = squadron.determineCurrentAirfieldAnyMap(mission.getCampaign().getDate());
            fieldSet.put(playerField.getName(), playerField);
        }
    }

    private void addAirfieldObjects() throws PWCGException
    {
        for (Airfield airfield : fieldSet.values())
        {
            ICountry airfieldCountry = getAirfieldCountry(airfield);
            if (airfieldCountry.getCountry() != Country.NEUTRAL)
            {
                airfield.addAirfieldObjects(mission, airfieldCountry);
            }
        }
    }
    
    private ICountry getAirfieldCountry(Airfield airfield) throws PWCGException
    {
        ICountry airfieldCountry = airfield.getCountry(campaign.getCampaignMap(), campaign.getDate());
        if (airfieldCountry.getCountry() != Country.NEUTRAL)
        {
            return airfieldCountry;
        }
        
        SquadronManager squadronManager = PWCGContext.getInstance().getSquadronManager();
        Squadron squadronForField = squadronManager.getAnyActiveSquadronForAirfield(campaign.getCampaignMap(), airfield, campaign.getDate());
        if (squadronForField != null)
        {
            return squadronForField.getCountry();
        }

        return CountryFactory.makeCountryByCountry(Country.NEUTRAL);
    }
}
