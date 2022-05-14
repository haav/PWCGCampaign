package pwcg.product.fc.plane.payload;

import java.util.Date;

import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.plane.payload.IPlanePayload;
import pwcg.campaign.plane.payload.PayloadElement;
import pwcg.campaign.plane.payload.PlanePayload;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;

public class SopwithTriplanePayload extends PlanePayload implements IPlanePayload
{
    public SopwithTriplanePayload(PlaneType planeType, Date date)
    {
        super(planeType, date);
        setNoOrdnancePayloadId(0);
    }

    protected void initialize()
    {
        setAvailablePayload(-2, "1000", PayloadElement.COCKPIT_LIGHT);
        setAvailablePayload(-1, "100", PayloadElement.ALDIS_SIGHT);

        setAvailablePayload(0, "1", PayloadElement.STANDARD);
        setAvailablePayload(1, "1", PayloadElement.LESS_AMMO);
        setAvailablePayload(2, "11", PayloadElement.TWIN_FRONT);
        setAvailablePayload(3, "11", PayloadElement.TWIN_FRONT, PayloadElement.LESS_AMMO);
    }

    @Override
    public IPlanePayload copy()
    {
        SopwithTriplanePayload clone = new SopwithTriplanePayload(getPlaneType(), getDate());
        return super.copy(clone);
    }

    protected int createWeaponsPayloadForPlane(IFlight flight)
    {
        int selectedPayloadId = 0;
        if (FlightTypes.isGroundAttackFlight(flight.getFlightType()))
        {
            selectedPayloadId = selectGroundAttackPayload(flight);
        }
        return selectedPayloadId;
    }

    protected int selectGroundAttackPayload(IFlight flight)
    {
        return 2;
    }

    @Override
    public boolean isOrdnance()
    {
        int selectedPayloadId = this.getSelectedPayload();
        if (selectedPayloadId == 2)
        {
            return true;
        }

        return false;
    }
}
