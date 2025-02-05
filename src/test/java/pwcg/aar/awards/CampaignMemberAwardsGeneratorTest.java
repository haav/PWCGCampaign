package pwcg.aar.awards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.aar.data.AARContext;
import pwcg.aar.data.AARPersonnelAwards;
import pwcg.aar.data.AARPersonnelLosses;
import pwcg.aar.inmission.phase3.reconcile.victories.ReconciledMissionVictoryData;
import pwcg.aar.outofmission.phase2.awards.CampaignMemberAwardsGenerator;
import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.core.exception.PWCGException;
import pwcg.testutils.TestCampaignFactoryBuilder;
import pwcg.testutils.CampaignPersonnelTestHelper;
import pwcg.testutils.SquadronTestProfile;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CampaignMemberAwardsGeneratorTest
{
    private Campaign campaign;
    
    @Mock
    AARContext aarContext;
    
    @Mock
    private ReconciledMissionVictoryData reconciledVictoryData;
    
    @Mock
    private AARPersonnelLosses personnelLosses;
         
    @BeforeAll
    public void setupSuite() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.FC);
        campaign = TestCampaignFactoryBuilder.makeCampaign(this.getClass().getCanonicalName(), SquadronTestProfile.ESC_103_PROFILE);
        
    }

    @BeforeEach
    public void setupTest() throws PWCGException
    {        
        Mockito.when(aarContext.getPersonnelLosses()).thenReturn(personnelLosses);
        Mockito.when(personnelLosses.pilotisWoundedToday(Mockito.any())).thenReturn(false);
    }

    @Test
    public void testMedalAwardedForVictories () throws PWCGException
    {             
        SquadronMember aiSquadMember = CampaignPersonnelTestHelper.getSquadronMemberByRank(campaign, "Corporal");
        CampaignPersonnelTestHelper.addVictories(aiSquadMember, campaign.getDate(), 20);

        CampaignMemberAwardsGenerator awardsGenerator = new CampaignMemberAwardsGenerator(campaign, aarContext);
        AARPersonnelAwards campaignMemberAwards = awardsGenerator.generateAwards(aiSquadMember, 1);
        
        Assertions.assertTrue (campaignMemberAwards.getCampaignMemberMedals().size() >= 1);
        Assertions.assertTrue (campaignMemberAwards.getCampaignMemberMedals().containsKey(aiSquadMember.getSerialNumber()));
    }

    @Test
    public void testInjuredMembersAwardedWoundBadge () throws PWCGException
    {            
        SquadronMember aiSquadMember = CampaignPersonnelTestHelper.getSquadronMemberByRank(campaign, "Corporal");

        Mockito.when(personnelLosses.pilotisWoundedToday(Mockito.any())).thenReturn(true);
         
        CampaignMemberAwardsGenerator awardsGenerator = new CampaignMemberAwardsGenerator(campaign, aarContext);
        AARPersonnelAwards campaignMemberAwards = awardsGenerator.generateAwards(aiSquadMember, 1);
        
        Assertions.assertTrue (campaignMemberAwards.getCampaignMemberMedals().size() >= 1);
        Assertions.assertTrue (campaignMemberAwards.getCampaignMemberMedals().containsKey(aiSquadMember.getSerialNumber()));
    }

    @Test
    public void testPromotionAwardedForVictoriesAndMissionsFlown () throws PWCGException
    {     
        SquadronMember aiSquadMember = CampaignPersonnelTestHelper.getSquadronMemberByRank(campaign, "Corporal");
        CampaignPersonnelTestHelper.addVictories(aiSquadMember, campaign.getDate(), 20);
        aiSquadMember.setMissionFlown(100);

        CampaignMemberAwardsGenerator awardsGenerator = new CampaignMemberAwardsGenerator(campaign, aarContext);
        AARPersonnelAwards campaignMemberAwards = awardsGenerator.generateAwards(aiSquadMember, 1);
        
        Assertions.assertTrue (campaignMemberAwards.getPromotions().size() == 1);
        Assertions.assertTrue (campaignMemberAwards.getPromotions().containsKey(aiSquadMember.getSerialNumber()));
    }
}
