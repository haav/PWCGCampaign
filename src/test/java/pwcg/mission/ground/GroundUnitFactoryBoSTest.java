package pwcg.mission.ground;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pwcg.campaign.Campaign;
import pwcg.campaign.api.ICountry;
import pwcg.campaign.context.Country;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.factory.CountryFactory;
import pwcg.core.config.ConfigItemKeys;
import pwcg.core.config.ConfigManagerCampaign;
import pwcg.core.config.ConfigSimple;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.core.location.Orientation;
import pwcg.core.utils.DateUtils;
import pwcg.mission.Mission;
import pwcg.mission.ground.builder.AAAUnitBuilder;
import pwcg.mission.ground.builder.AssaultBuilder;
import pwcg.mission.ground.org.IGroundUnit;
import pwcg.mission.ground.org.IGroundUnitCollection;
import pwcg.mission.ground.unittypes.infantry.AssaultGroundUnitFactory;
import pwcg.mission.ground.unittypes.infantry.GroundAntiTankArtillery;
import pwcg.mission.ground.vehicle.VehicleClass;
import pwcg.mission.target.TacticalTarget;
import pwcg.mission.target.TargetDefinition;
import pwcg.mission.target.TargetDefinitionBuilderGround;

@RunWith(MockitoJUnitRunner.class)
public class GroundUnitFactoryBoSTest
{
    @Mock private Campaign campaign;
    @Mock private Mission mission;
    @Mock private ConfigManagerCampaign configManager;
    
    private ICountry country = CountryFactory.makeCountryByCountry(Country.GERMANY);

    @Before
    public void setup() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        Mockito.when(campaign.getCampaignConfigManager()).thenReturn(configManager);
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19430401"));
        Mockito.when(configManager.getStringConfigParam(ConfigItemKeys.SimpleConfigGroundKey)).thenReturn(ConfigSimple.CONFIG_LEVEL_MED);
    }

    @Test
    public void createAAAArtilleryBatteryTest () throws PWCGException 
    {
        AAAUnitBuilder groundUnitFactory = new AAAUnitBuilder(campaign, country, new Coordinate (100000, 0, 100000));
        IGroundUnitCollection groundUnitGroup = groundUnitFactory.createAAAArtilleryBattery(GroundUnitSize.GROUND_UNIT_SIZE_MEDIUM);
        for (IGroundUnit groundUnit : groundUnitGroup.getGroundUnits())
        {
            assert (groundUnit.getVehicleClass() == VehicleClass.AAAArtillery);
            assert (groundUnit.getCountry().getCountry() == Country.GERMANY);
            assert (groundUnit.getSpawners().size() >= 1);
            assert (groundUnit.getSpawners().size() <= 4);
        }
    }

    @Test
    public void createAAAMGBatteryTest () throws PWCGException 
    {
        AAAUnitBuilder groundUnitFactory = new AAAUnitBuilder(campaign,country, new Coordinate (100000, 0, 100000));
        IGroundUnitCollection groundUnitGroup = groundUnitFactory.createAAAMGBattery(GroundUnitSize.GROUND_UNIT_SIZE_HIGH);
        for (IGroundUnit groundUnit : groundUnitGroup.getGroundUnits())
        {
            assert (groundUnit.getVehicleClass() == VehicleClass.AAAMachineGun);
            assert (groundUnit.getCountry().getCountry() == Country.GERMANY);
            assert (groundUnit.getSpawners().size() >= 2);
            assert (groundUnit.getSpawners().size() <= 8);
        }
        groundUnitGroup.validate();
    }

    @Test
    public void createAssaultTest () throws PWCGException 
    {
        TargetDefinitionBuilderGround targetDefinitionBuilder = new TargetDefinitionBuilderGround(campaign);
        TargetDefinition targetDefinition = targetDefinitionBuilder.buildTargetDefinitionBattle(
                CountryFactory.makeCountryByCountry(Country.GERMANY), 
                CountryFactory.makeCountryByCountry(Country.RUSSIA), 
                TacticalTarget.TARGET_ASSAULT, new Coordinate (102000, 0, 100000), true);

        IGroundUnitCollection groundUnitGroup = AssaultBuilder.generateAssault(mission, targetDefinition);
        for (IGroundUnit groundUnit : groundUnitGroup.getGroundUnits())
        {
            assert (groundUnit.getVehicleClass() == VehicleClass.ArtilleryAntiTank);
            assert (groundUnit.getCountry().getCountry() == Country.GERMANY);
            assert (groundUnit.getSpawners().size() >= 2);
            assert (groundUnit.getSpawners().size() <= 8);
        }
        groundUnitGroup.validate();
    }

    @Test
    public void createAssaultTest () throws PWCGException 
    {
        String name = country.getCountryName() + " AntiTank";
        GroundUnitInformation groundUnitInformation = GroundUnitInformationFactory.buildGroundUnitInformation(
                campaign, 
                country,
                name, 
                TacticalTarget.TARGET_ARTILLERY, 
                new Coordinate (100000, 0, 100000), 
                new Coordinate (102000, 0, 100000), 
                new Orientation(), 
                true);

        AssaultGroundUnitFactory groundUnitFactory = new AssaultGroundUnitFactory();
        GroundAntiTankArtillery groundUnitGroup = (GroundAntiTankArtillery)groundUnitFactory.createAntiTankGunUnit(groundUnitInformation);
        groundUnitGroup.validate();
    }
}
