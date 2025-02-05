package pwcg.dev.jsonconvert.orig.io;

import pwcg.campaign.context.FrontMapIdentifier;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.group.airfield.AirfieldDescriptor;
import pwcg.campaign.group.airfield.AirfieldDescriptorSet;
import pwcg.campaign.group.airfield.Runway;
import pwcg.campaign.io.json.AirfieldDescriptorIOJson;
import pwcg.core.location.Coordinate;
import pwcg.core.utils.MathUtils;

public class AirfieldRunwayEndBuilder
{
    public static void main(String[] args)
    {
        try 
        {
            PWCGContext.setProduct(PWCGProduct.BOS);
            String mapName = PWCGContext.getInstance().getMap(FrontMapIdentifier.NORMANDY_MAP).getMapName();
            AirfieldDescriptorSet airfieldDescriptorSet = AirfieldDescriptorIOJson.readJson(PWCGContext.getInstance().getDirectoryManager().getPwcgInputDir() + 
                    mapName + "\\", "AirfieldLocations");
            for (AirfieldDescriptor airfieldDescriptor : airfieldDescriptorSet.getLocations())
            {
                for (Runway runway : airfieldDescriptor.getRunways())
                {
                    double angle = airfieldDescriptor.getOrientation().getyOri();
                    Coordinate startPos = runway.getStartPos().copy();
                    Coordinate endPos = MathUtils.calcNextCoord(FrontMapIdentifier.NORMANDY_MAP, startPos, angle, 1200);
                    runway.setEndPos(endPos);
                }
            }

            AirfieldDescriptorIOJson.writeJson(PWCGContext.getInstance().getDirectoryManager().getPwcgInputDir() + mapName + "\\", "AirfieldLocations.json", airfieldDescriptorSet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
