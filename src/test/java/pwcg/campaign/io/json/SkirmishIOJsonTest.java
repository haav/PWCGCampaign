package pwcg.campaign.io.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.context.FrontMapIdentifier;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.skirmish.Skirmishes;
import pwcg.core.exception.PWCGException;

@ExtendWith(MockitoExtension.class)
public class SkirmishIOJsonTest
{
    @Test
    public void readJsonBoSTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        PWCGContext.getInstance();
        Skirmishes skirmishes = SkirmishIOJson.readJson(FrontMapIdentifier.BODENPLATTE_MAP.getMapName());
        assert (skirmishes.getSkirmishes().size() > 0);
    }
}
