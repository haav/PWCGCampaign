package pwcg.product.bos.plane.payload.aircraft;

import java.util.Date;

import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.plane.payload.IPlanePayload;
import pwcg.campaign.plane.payload.PayloadElement;
import pwcg.campaign.plane.payload.PlanePayload;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.target.TargetCategory;

public class Bf110E2Payload extends PlanePayload
{
    public Bf110E2Payload(PlaneType planeType, Date date)
    {
        super(planeType, date);
        setNoOrdnancePayloadId(0);
    }

    protected void initialize()
	{
        setAvailablePayload(-2, "10", PayloadElement.ARMORED_WINDSCREEN);
		setAvailablePayload(-1, "100", PayloadElement.EXTRA_ARMOR);
        setAvailablePayload(0, "1", PayloadElement.STANDARD);
        setAvailablePayload(1, "1", PayloadElement.SC250_X2);
        setAvailablePayload(2, "1", PayloadElement.SC250_X2, PayloadElement.SC50_X4);
        setAvailablePayload(3, "1001", PayloadElement.SC50_X12);
        setAvailablePayload(4, "10001", PayloadElement.SC500_X2);
		setAvailablePayload(5, "10001", PayloadElement.SC500_X2, PayloadElement.SC50_X4);
		setAvailablePayload(6, "100001", PayloadElement.SC1000_X1);
		setAvailablePayload(7, "100001", PayloadElement.SC1000_X1, PayloadElement.SC250_X2);
		setAvailablePayload(8, "10000", PayloadElement.SC1000_X1, PayloadElement.SC50_X4);
	}

    @Override
    public IPlanePayload copy()
    {
        Bf110E2Payload clone = new Bf110E2Payload(getPlaneType(), getDate());
        
        return super.copy(clone);
    }

    protected int createWeaponsPayloadForPlane(IFlight flight)
    {
        int selectedPayloadId = createStandardPayload();
        if (FlightTypes.isGroundAttackFlight(flight.getFlightType()))
        {
            selectedPayloadId = selectGroundAttackPayload(flight);
        }

        return selectedPayloadId;
    }

    private int selectGroundAttackPayload (IFlight flight)
    {
        int selectedPayloadId = 3;
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
    
    private int createStandardPayload()
    {
        return  getPayloadIdByDescription(PayloadElement.STANDARD.getDescription());
    }

    private int selectSoftTargetPayload()
    {
        return  1;
    }    

    private int selectArmoredTargetPayload()
    {
        return  4;
    }

    private int selectMediumTargetPayload()
    {
        return  4;
    }

    private int selectHeavyTargetPayload()
    {
        return  4;
    }

    private int selectStructureTargetPayload()
    {
        return  4;
    }

    @Override
    public boolean isOrdnance()
    {
        if (isOrdnanceDroppedPayload())
        {
            return false;
        }
        
        if (this.getSelectedPayload() == 0) 
        {
            return false;
        }

        return true;
    }
}
