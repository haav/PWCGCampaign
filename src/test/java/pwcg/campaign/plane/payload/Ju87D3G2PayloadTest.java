package pwcg.campaign.plane.payload;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.plane.PlaneTypeFactory;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.config.ConfigManagerCampaign;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;
import pwcg.mission.flight.FlightInformation;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.target.TargetCategory;
import pwcg.mission.target.TargetDefinition;
import pwcg.product.bos.plane.BosPlaneAttributeMapping;
import pwcg.product.bos.plane.payload.BoSPayloadFactory;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class Ju87D3G2PayloadTest
{
    @Mock
    IFlight flight;
    @Mock
    FlightInformation flightInformation;
    @Mock
    TargetDefinition targetDefinition;
    @Mock
    Campaign campaign;
    @Mock
    Squadron squadron;
    @Mock
    ConfigManagerCampaign configManagerCampaign;

    @BeforeEach
    public void setupTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);

        Mockito.when(flight.getTargetDefinition()).thenReturn(targetDefinition);
    }

    @Test
    public void testDiveBombBeforeG2() throws PWCGException
    {
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19430101"));
        IPlanePayload payloadGenerator = getPayloadGeneratorForDiveBomber();
        testDiveBombAndAttackBeforeG2(payloadGenerator);
    }

    @Test
    public void testDiveBombAfterG2() throws PWCGException
    {
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19430801"));
        IPlanePayload payloadGenerator = getPayloadGeneratorForDiveBomber();
        testDiveBombAndAttackBeforeG2(payloadGenerator);
    }

    @Test
    public void testAttackMissionAttackSquadronBeforeG3() throws PWCGException
    {
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19430101"));
        IPlanePayload payloadGenerator = getPayloadGeneratorForAttack();
        testDiveBombAndAttackBeforeG2(payloadGenerator);
    }

    @Test
    public void testAttackMissionAttackSquadronUsingG3() throws PWCGException
    {
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19430801"));
        IPlanePayload payloadGenerator = getPayloadGeneratorForAttack();
        testAttackAfterG2(payloadGenerator);
    }

    @Test
    public void validateStukaPayloadBeforeCannons() throws PWCGException
    {
        BoSPayloadFactory bosPayloadFactory = new BoSPayloadFactory();
        PlaneTypeFactory planeTypeFactory = PWCGContext.getInstance().getPlaneTypeFactory();

        PlaneType bosPlaneType = planeTypeFactory.createPlaneTypeByType(BosPlaneAttributeMapping.JU87_D3.getPlaneType());

        System.out.println(bosPlaneType.getType());

        IPlanePayload payload = bosPayloadFactory.createPlanePayload(bosPlaneType.getType(), DateUtils.getDateYYYYMMDD("19420801"));
        Assertions.assertTrue (payload != null);

        Assertions.assertTrue (payload.getAvailablePayloadDesignations(flight).size() == 8);

        List<PayloadElement> unexpectedElements = Arrays.asList(PayloadElement.BK37_AP_GUNPOD, PayloadElement.BK37_HE_GUNPOD);
        for (PayloadElement unexpectedElement : unexpectedElements)
        {
            Boolean found = false;
            for (PayloadDesignation payloadDesignation : payload.getAvailablePayloadDesignations(flight))
            {
                for (PayloadElement element : payloadDesignation.getPayloadElements())
                {
                    if (unexpectedElement == element)
                    {
                        found = true;
                    }
                }
            }
            Assertions.assertTrue (!found);
        }
    }

    @Test
    public void validateStukaPayloadAfterCannons() throws PWCGException
    {
        BoSPayloadFactory bosPayloadFactory = new BoSPayloadFactory();
        PlaneTypeFactory planeTypeFactory = PWCGContext.getInstance().getPlaneTypeFactory();

        PlaneType bosPlaneType = planeTypeFactory.createPlaneTypeByType(BosPlaneAttributeMapping.JU87_D3.getPlaneType());

        System.out.println(bosPlaneType.getType());

        Date date = DateUtils.getDateYYYYMMDD("19430503");

        IPlanePayload payload = bosPayloadFactory.createPlanePayload(bosPlaneType.getType(), date);
        Assertions.assertTrue (payload != null);

        Assertions.assertTrue (payload.getAvailablePayloadDesignations(flight).size() == 10);
        
        List<PayloadElement> expectedElements = Arrays.asList(PayloadElement.BK37_AP_GUNPOD, PayloadElement.BK37_HE_GUNPOD);
        for (PayloadElement expectedElement : expectedElements)
        {
            Boolean found = false;
            for (PayloadDesignation payloadDesignation : payload.getAvailablePayloadDesignations(flight))
            {
                for (PayloadElement element : payloadDesignation.getPayloadElements())
                {
                    if (expectedElement == element)
                    {
                        found = true;
                    }
                }
            }
            Assertions.assertTrue (found);
        }
    }

    private IPlanePayload getPayloadGeneratorForDiveBomber() throws PWCGException
    {
        PlaneType plane = PWCGContext.getInstance().getPlaneTypeFactory().createPlaneTypeByType(BosPlaneAttributeMapping.JU87_D3.getPlaneType());
        IPayloadFactory payloadFactory = PWCGContext.getInstance().getPayloadFactory();
        IPlanePayload payloadGenerator = payloadFactory.createPlanePayload(plane.getType(), campaign.getDate());
        return payloadGenerator;
    }

    private IPlanePayload getPayloadGeneratorForAttack() throws PWCGException
    {
        PlaneType plane = PWCGContext.getInstance().getPlaneTypeFactory().createPlaneTypeByType(BosPlaneAttributeMapping.JU87_D3.getPlaneType());
        IPayloadFactory payloadFactory = PWCGContext.getInstance().getPayloadFactory();
        IPlanePayload payloadGenerator = payloadFactory.createPlanePayload(plane.getType(), campaign.getDate());
        return payloadGenerator;
    }

    private void testDiveBombAndAttackBeforeG2(IPlanePayload payloadGenerator) throws PWCGException
    {
        Mockito.when(flight.getFlightType()).thenReturn(FlightTypes.DIVE_BOMB);

        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_SOFT);
        runPayload(payloadGenerator, Arrays.asList(1));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_ARMORED);
        runPayload(payloadGenerator, Arrays.asList(2));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_MEDIUM);
        runPayload(payloadGenerator, Arrays.asList(2));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_HEAVY);
        runPayload(payloadGenerator, Arrays.asList(2));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_STRUCTURE);
        runPayload(payloadGenerator, Arrays.asList(6));
    }

    private void testAttackAfterG2(IPlanePayload payloadGenerator) throws PWCGException
    {
        Mockito.when(flight.getFlightType()).thenReturn(FlightTypes.GROUND_ATTACK);

        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_SOFT);
        runPayload(payloadGenerator, Arrays.asList(9));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_ARMORED);
        runPayload(payloadGenerator, Arrays.asList(9));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_MEDIUM);
        runPayload(payloadGenerator, Arrays.asList(9));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_HEAVY);
        runPayload(payloadGenerator, Arrays.asList(9));
        Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_STRUCTURE);
        runPayload(payloadGenerator, Arrays.asList(6));
    }

    private void runPayload(IPlanePayload payloadGenerator, List<Integer> expectedPayloadSet) throws PWCGException
    {
        int payloadId = payloadGenerator.createWeaponsPayload(flight);
        Assertions.assertTrue (expectedPayloadSet.contains(payloadId));
    }
}
