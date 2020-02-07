package pwcg.campaign.plane.payload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.config.ConfigManagerCampaign;
import pwcg.core.exception.PWCGException;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.IFlightInformation;
import pwcg.mission.target.TargetCategory;
import pwcg.mission.target.TargetDefinition;
import pwcg.product.bos.plane.BosPlaneAttributeMapping;

@RunWith(MockitoJUnitRunner.class)
public class FW190F2PayloadTest 
{
	@Mock IFlight flight;
	@Mock IFlightInformation flightInformation;
	@Mock TargetDefinition targetDefinition;
	@Mock Campaign campaign;
	@Mock Squadron squadron;
	@Mock ConfigManagerCampaign configManagerCampaign;
	
	@Before
	public void setup() throws PWCGException
	{
		PWCGContext.setProduct(PWCGProduct.BOS);
        
        Mockito.when(campaign.getCampaignConfigManager()).thenReturn(configManagerCampaign);
		Mockito.when(flight.getSquadron()).thenReturn(squadron);
        Mockito.when(flight.getFlightInformation()).thenReturn(flightInformation);
        Mockito.when(flightInformation.getTargetDefinition()).thenReturn(targetDefinition);
	}

	@Test
	public void payloadNormalTest() throws PWCGException
	{
		PlaneType fw190A5 = PWCGContext.getInstance().getPlaneTypeFactory().createPlaneTypeByType(BosPlaneAttributeMapping.FW190_A5.getPlaneType());
		IPayloadFactory payloadFactory = PWCGContext.getInstance().getPayloadFactory();
    	IPlanePayload payloadGenerator = payloadFactory.createPlanePayload(fw190A5.getType());
    	testPatrolPayload(payloadGenerator);
    	testInterceptPayload(payloadGenerator);
    	testGroundAttackPayload(payloadGenerator);
	}

	@Test
	public void payloadFw190F2Test() throws PWCGException
	{
		PlaneType fw190f2 = PWCGContext.getInstance().getPlaneTypeFactory().createPlaneTypeByType(BosPlaneAttributeMapping.FW190_A5.getPlaneType());
		IPayloadFactory payloadFactory = PWCGContext.getInstance().getPayloadFactory();
    	IPlanePayload payloadGenerator = payloadFactory.createPlanePayload(fw190f2.getType());
    	testGroundAttackPayload(payloadGenerator);
	}

	private void testPatrolPayload(IPlanePayload payloadGenerator) throws PWCGException
	{
		Mockito.when(flight.getFlightType()).thenReturn(FlightTypes.PATROL);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_NONE);
		runPayload(payloadGenerator);
	}
	
	private void testInterceptPayload(IPlanePayload payloadGenerator) throws PWCGException
	{
		Mockito.when(flight.getFlightType()).thenReturn(FlightTypes.INTERCEPT);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_NONE);
		runPayload(payloadGenerator);
	}
	
	private void testGroundAttackPayload(IPlanePayload payloadGenerator) throws PWCGException
	{
		Mockito.when(flight.getFlightType()).thenReturn(FlightTypes.GROUND_ATTACK);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_SOFT);
		runPayload(payloadGenerator);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_ARMORED);
		runPayload(payloadGenerator);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_MEDIUM);
		runPayload(payloadGenerator);
		Mockito.when(targetDefinition.getTargetCategory()).thenReturn(TargetCategory.TARGET_CATEGORY_HEAVY);
		runPayload(payloadGenerator);
	}

	private void runPayload(IPlanePayload payloadGenerator) throws PWCGException {
		for (int i = 0; i < 100; ++i)
		{
			int payloadId = payloadGenerator.createWeaponsPayload(flight);
			PayloadDesignation payloadDesignation = payloadGenerator.getSelectedPayloadDesignation();
			assert(payloadDesignation.getPayloadId() == payloadId);
		}
	}
}
