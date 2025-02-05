package pwcg.campaign.group.airfield.staticobject;

import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.io.json.StaticObjectIOJson;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.IWeight;
import pwcg.core.utils.WeightCalculator;
import pwcg.mission.ground.vehicle.VehicleDefinition;
import pwcg.mission.ground.vehicle.VehicleRequestDefinition;

public class StaticObjectDefinitionManager
{
    private List<VehicleDefinition> allStaticObjectsDefinitions = new ArrayList<>();

    public StaticObjectDefinitionManager()
    {
    }
    
    public void initialize() throws PWCGException
    {
        allStaticObjectsDefinitions = StaticObjectIOJson.readJson();
    }

    public List<VehicleDefinition> getAllVehicleDefinitions()
    {
        return allStaticObjectsDefinitions;
    }
    
    
    public VehicleDefinition getVehicleDefinitionForRequest(VehicleRequestDefinition requestDefinition) throws PWCGException
    {
        List<VehicleDefinition> matchingDefinitions = new ArrayList<>();
        for (VehicleDefinition definition : allStaticObjectsDefinitions)
        {
            if (definition.shouldUse(requestDefinition))
            {
                matchingDefinitions.add(definition);
            }
        }
        
        if (matchingDefinitions.size() == 0)
        {
            throw new PWCGException ("No definition found for request " + requestDefinition.toString());
        }
        
        return chooseMatchingVehicle(matchingDefinitions);
    }

    
    public VehicleDefinition findStaticVehicle(String vehicleType) throws PWCGException
    {
        for (VehicleDefinition definition : allStaticObjectsDefinitions)
        {
            if (definition.getVehicleType().toLowerCase().contains(vehicleType.toLowerCase()))
            {
                return definition;
            }
            
            if (vehicleType.toLowerCase().contains(definition.getVehicleType().toLowerCase()))
            {
                return definition;
            }
        }
        
        return null;
    }

    private VehicleDefinition chooseMatchingVehicle(List<VehicleDefinition> matchingDefinitions)
    {
        List<IWeight> vehiclesByWeight = new ArrayList<>();
        for (VehicleDefinition definition : matchingDefinitions)
        {
            vehiclesByWeight.add(definition);
        }
        
        WeightCalculator weightCalculator = new WeightCalculator(vehiclesByWeight);
        int index = weightCalculator.getItemFromWeight();
        return matchingDefinitions.get(index);
    }

    public VehicleDefinition getVehicleDefinitionByType(String name)
    {
        for (VehicleDefinition definition : allStaticObjectsDefinitions)
        {
            if (definition.getVehicleType().equals(name))
                return definition;
        }
        return null;
    }
}
