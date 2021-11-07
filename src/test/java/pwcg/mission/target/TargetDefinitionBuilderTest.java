package pwcg.mission.target;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.plane.PwcgRole;
import pwcg.campaign.squadron.Squadron;
import pwcg.campaign.squadron.SquadronRolePeriod;
import pwcg.campaign.squadron.SquadronRoleSet;
import pwcg.campaign.squadron.SquadronRoleWeight;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;
import pwcg.mission.Mission;
import pwcg.mission.MissionGenerator;
import pwcg.mission.flight.IFlight;
import pwcg.testutils.CampaignCache;
import pwcg.testutils.SquadronTestProfile;
import pwcg.testutils.TestMissionBuilderUtility;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TargetDefinitionBuilderTest
{
    private Campaign campaign;

    @BeforeAll
    public void setupSuite() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        campaign = CampaignCache.makeCampaign(SquadronTestProfile.JG_26_PROFILE_WEST);
    }

    @Test
    public void randomTargetTypeTest() throws PWCGException
    {
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMission(TestMissionBuilderUtility.buildTestParticipatingHumans(campaign));
        
        IFlight playerFlight = mission.getFlights().getPlayerFlights().get(0);

        TargetDefinitionBuilder targetDefinitionBuilder = new TargetDefinitionBuilder(playerFlight.getFlightInformation()); 
        TargetDefinition targetDefinition =  targetDefinitionBuilder.buildTargetDefinition();
        assert(targetDefinition.getTargetType() != TargetType.TARGET_NONE);
    }
    
    @Test
    public void tankBustTargetTypeTest() throws PWCGException
    {
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.JG_26_PROFILE_WEST.getSquadronId());
        
        SquadronRoleWeight squadronRoleWeight = new SquadronRoleWeight();
        squadronRoleWeight.setRole(PwcgRole.ROLE_TANK_BUSTER);
        squadronRoleWeight.setWeight(100);
        
        SquadronRolePeriod squadronRolePeriod = new SquadronRolePeriod();
        squadronRolePeriod.setStartDate(DateUtils.getDateYYYYMMDD("19400101"));
        squadronRolePeriod.setWeightedRoles(Arrays.asList(squadronRoleWeight));

        SquadronRoleSet squadronRoleSet = squadron.getSquadronRoles();
        squadronRoleSet.overrideRolesForTest(Arrays.asList(squadronRolePeriod));

        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMission(TestMissionBuilderUtility.buildTestParticipatingHumans(campaign));
        
        IFlight playerFlight = mission.getFlights().getPlayerFlights().get(0);

        TargetDefinitionBuilder targetDefinitionBuilder = new TargetDefinitionBuilder(playerFlight.getFlightInformation()); 
        TargetDefinition targetDefinition =  targetDefinitionBuilder.buildTargetDefinition();
        assert(targetDefinition.getTargetType() == TargetType.TARGET_ARMOR);
    }
    
    @Test
    public void trainBustTargetTypeTest() throws PWCGException
    {
        Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(SquadronTestProfile.JG_26_PROFILE_WEST.getSquadronId());
        
        SquadronRoleWeight squadronRoleWeight = new SquadronRoleWeight();
        squadronRoleWeight.setRole(PwcgRole.ROLE_TRAIN_BUSTER);
        squadronRoleWeight.setWeight(100);
        
        SquadronRolePeriod squadronRolePeriod = new SquadronRolePeriod();
        squadronRolePeriod.setStartDate(DateUtils.getDateYYYYMMDD("19400101"));
        squadronRolePeriod.setWeightedRoles(Arrays.asList(squadronRoleWeight));

        SquadronRoleSet squadronRoleSet = squadron.getSquadronRoles();
        squadronRoleSet.overrideRolesForTest(Arrays.asList(squadronRolePeriod));

        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMission(TestMissionBuilderUtility.buildTestParticipatingHumans(campaign));
        
        IFlight playerFlight = mission.getFlights().getPlayerFlights().get(0);

        TargetDefinitionBuilder targetDefinitionBuilder = new TargetDefinitionBuilder(playerFlight.getFlightInformation()); 
        TargetDefinition targetDefinition =  targetDefinitionBuilder.buildTargetDefinition();
        assert(targetDefinition.getTargetType() == TargetType.TARGET_TRAIN);
    }
 }
