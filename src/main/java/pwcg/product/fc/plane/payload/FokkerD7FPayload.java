package pwcg.product.fc.plane.payload;

import java.util.Date;

import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.plane.payload.IPlanePayload;
import pwcg.campaign.plane.payload.PayloadElement;
import pwcg.campaign.plane.payload.PlanePayload;
import pwcg.mission.flight.IFlight;

public class FokkerD7FPayload extends PlanePayload implements IPlanePayload
{
    public FokkerD7FPayload(PlaneType planeType, Date date)
    {
        super(planeType, date);
        setNoOrdnancePayloadId(0);
    }

    protected void initialize()
    {
        setAvailablePayload(-7, "10000000", PayloadElement.COCKPIT_LIGHT);
        setAvailablePayload(-6, "1000000", PayloadElement.TEMPERATURE_GUAGE);
        setAvailablePayload(-5, "100000", PayloadElement.ALTITUDE_GUAGE);
        setAvailablePayload(-4, "10000", PayloadElement.SPEED_GUAGE);
        
        setAvailablePayload(-3, "1000", PayloadElement.IRON_SIGHT);
        setAvailablePayload(-2, "100", PayloadElement.NIGHT_SIGHT);
        setAvailablePayload(-1, "10", PayloadElement.DAY_SIGHT);
        
        setAvailablePayload(0, "1", PayloadElement.STANDARD);
    }

    @Override
    public IPlanePayload copy()
    {
        FokkerD7FPayload clone = new FokkerD7FPayload(getPlaneType(), getDate());
        return super.copy(clone);
    }
    
    protected int createWeaponsPayloadForPlane(IFlight flight)
    {
        int selectedPayloadId = 0;
        return selectedPayloadId;
    }

    @Override
    public boolean isOrdnance()
    {
        return false;
    }
}
