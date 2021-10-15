package pwcg.campaign.io.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.group.airfield.hotspot.AirfieldHotSpotCollection;
import pwcg.core.exception.PWCGException;

@ExtendWith(MockitoExtension.class)
public class AirfieldHotSpotsIOJsonTest
{
    @Test
    public void readJsonTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.FC);
        AirfieldHotSpotCollection airfieldHotSpotCollection = AirfieldHotSpotsIOJson.readJson();
        assert (airfieldHotSpotCollection.getAirfieldHotSpots().size() > 0);
    }
    
    @Test
    public void readJsonBoSTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        AirfieldHotSpotCollection airfieldHotSpotCollection = AirfieldHotSpotsIOJson.readJson();
        assert (airfieldHotSpotCollection.getAirfieldHotSpots().size() > 0);
    }
}
