package pwcg.mission.ground.factory;

import pwcg.campaign.Campaign;
import pwcg.campaign.api.ICountry;
import pwcg.campaign.target.TacticalTarget;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.mission.ground.GroundUnitInformation;
import pwcg.mission.MissionBeginUnitCheckZone;
import pwcg.mission.ground.GroundUnitInformationFactory;
import pwcg.mission.ground.unittypes.artillery.GroundAAABattery;
import pwcg.mission.mcu.Coalition;

public class AAAUnitFactory
{    
    private Campaign campaign;
    private ICountry country;
    private Coordinate position;
    
    public AAAUnitFactory (Campaign campaign, ICountry country, Coordinate location)
    {
        this.campaign  = campaign;
        this.country  = country;
        this.position  = location.copy();
    }

    public GroundAAABattery createAAAArtilleryBattery (int minUnits, int maxUnits) throws PWCGException
    {
        return createAAABattery(minUnits, maxUnits, false);
    }

    public GroundAAABattery createAAAMGBattery (int minUnits, int maxUnits) throws PWCGException
    {
        return createAAABattery(minUnits, maxUnits, true);
    }

    private GroundAAABattery createAAABattery (int minUnits, int maxUnits, boolean isMG) throws PWCGException
    {
        Coalition enemyCoalition =  Coalition.getEnemyCoalition(country);

        MissionBeginUnitCheckZone missionBeginUnit = new MissionBeginUnitCheckZone();
        missionBeginUnit.initialize(position, 5000, enemyCoalition);
        
        String nationality = country.getNationality();
        String name = nationality + " AAA";

        GroundUnitInformation groundUnitInformation = GroundUnitInformationFactory.buildGroundUnitInformation(
                missionBeginUnit, country, name, TacticalTarget.TARGET_DEFENSE, position, position);
        
        GroundAAABattery aaaBattery = new GroundAAABattery(campaign, groundUnitInformation, isMG);
        aaaBattery.setMinMaxRequested(minUnits, maxUnits);
        aaaBattery.createUnitMission();

        return aaaBattery;
    }
}
