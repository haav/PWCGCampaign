package pwcg.product.bos.plane.payload.aircraft;

import java.util.Date;

import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.plane.payload.IPlanePayload;
import pwcg.campaign.plane.payload.PayloadElement;
import pwcg.campaign.plane.payload.PlanePayload;
import pwcg.mission.flight.IFlight;
import pwcg.mission.target.TargetCategory;

public class He111H16Payload extends PlanePayload
{
    public He111H16Payload(PlaneType planeType, Date date)
    {
        super(planeType, date);
        setNoOrdnancePayloadId(6);
    }

    protected void initialize()
	{
        setAvailablePayload(0, "1", PayloadElement.SC50_X16);
        setAvailablePayload(1, "1", PayloadElement.SC50_X32);
        setAvailablePayload(2, "1", PayloadElement.SC250_X4);
        setAvailablePayload(3, "1", PayloadElement.SC250_X8);
        setAvailablePayload(4, "1",PayloadElement.SC250_X4, PayloadElement.SC50_X16);
        setAvailablePayload(5, "1", PayloadElement.SC500_X1, PayloadElement.SC50_X16);
        setAvailablePayload(6, "1", PayloadElement.EMPTY);
        setAvailablePayload(7, "1", PayloadElement.SC500_X2);
        setAvailablePayload(8, "11", PayloadElement.SC1000_X1, PayloadElement.SC50_X16);
        setAvailablePayload(9, "11", PayloadElement.SC1000_X1, PayloadElement.SC250_X4);
        setAvailablePayload(10, "11", PayloadElement.SC1000_X2);
        setAvailablePayload(11, "101", PayloadElement.SC1800_X1, PayloadElement.SC50_X16);
        setAvailablePayload(12, "101", PayloadElement.SC1800_X1, PayloadElement.SC250_X4);
        setAvailablePayload(14, "111", PayloadElement.SC1800_X1, PayloadElement.SC1000_X1);
        setAvailablePayload(15, "1001", PayloadElement.SC2500_X1);
        setAvailablePayload(16, "1001", PayloadElement.SC2500_X1, PayloadElement.SC50_X16);
        setAvailablePayload(17, "1001", PayloadElement.SC2500_X1, PayloadElement.SC250_X4);
        setAvailablePayload(18, "1011", PayloadElement.SC2500_X1, PayloadElement.SC1000_X1);
	}

    @Override
    public IPlanePayload copy()
    {
        He111H16Payload clone = new He111H16Payload(getPlaneType(), getDate());
        
        return super.copy(clone);
    }
    
    protected int createWeaponsPayloadForPlane(IFlight flight)
    {
        int selectedPayloadId = 2;
        if (flight.getTargetDefinition().getTargetCategory() == TargetCategory.TARGET_CATEGORY_SOFT)
        {
            selectedPayloadId = selectSoftTargetPayload();
        }
        else if (flight.getTargetDefinition().getTargetCategory() == TargetCategory.TARGET_CATEGORY_ARMORED)
        {
            selectedPayloadId = selectArmoredTargetPayload();
        }
        else if (flight.getTargetDefinition().getTargetCategory() == TargetCategory.TARGET_CATEGORY_MEDIUM)
        {
            selectedPayloadId = selectMediumTargetPayload();
        }
        else if (flight.getTargetDefinition().getTargetCategory() == TargetCategory.TARGET_CATEGORY_HEAVY)
        {
            selectedPayloadId = selectHeavyTargetPayload();
        }
        else if (flight.getTargetDefinition().getTargetCategory() == TargetCategory.TARGET_CATEGORY_STRUCTURE)
        {
            selectedPayloadId = selectStructureTargetPayload();
        }

        return selectedPayloadId;
    }

    private int selectSoftTargetPayload()
    {
        return 0;
    }    

    private int selectArmoredTargetPayload()
    {
        return 7;
    }

    private int selectMediumTargetPayload()
    {
        return 2;
    }

    private int selectHeavyTargetPayload()
    {
        return 7;
    }

    private int selectStructureTargetPayload()
    {
        return 10;
    }

    @Override
    public boolean isOrdnance()
    {
        if (isOrdnanceDroppedPayload())
        {
            return false;
        }

        return true;
    }
}
