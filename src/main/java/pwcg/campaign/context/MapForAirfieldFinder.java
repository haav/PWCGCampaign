package pwcg.campaign.context;

import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.api.IAirfield;
import pwcg.campaign.context.PWCGMap.FrontMapIdentifier;
import pwcg.campaign.group.AirfieldManager;

public class MapForAirfieldFinder
{

    public static List<FrontMapIdentifier> getMapForAirfield(String airfieldName)
    {
        List<FrontMapIdentifier> mapsForAirfield = new ArrayList<>();
        for (PWCGMap map : PWCGContextManager.getInstance().getMaps())
        {
            AirfieldManager airfieldManager = map.getAirfieldManager();
            if (airfieldManager != null)
            {
                IAirfield airfield = airfieldManager.getAirfield(airfieldName);
                
                if (airfield != null)
                {
                    mapsForAirfield.add(map.getMapIdentifier());
                }
            }
        }

        return mapsForAirfield;
    }

}
