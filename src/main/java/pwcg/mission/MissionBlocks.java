package pwcg.mission;

import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PwcgMapGroundUnitLimitation;
import pwcg.campaign.group.BlockDefinition;
import pwcg.campaign.group.BlockDefinitionManager;
import pwcg.campaign.group.NonScriptedBlock;
import pwcg.campaign.group.ScriptedFixedPosition;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.core.utils.MathUtils;

public class MissionBlocks
{
    private Mission mission;
    private Campaign campaign;
    private List<ScriptedFixedPosition> structuresForMission = new ArrayList<>();
    private List<NonScriptedBlock> nonScriptedStructuresForMission = new ArrayList<>();

    public MissionBlocks(Mission mission, List<ScriptedFixedPosition> structuresForMission, List<NonScriptedBlock> nonScriptedStructuresForMission)
    {
        this.mission = mission;
        this.campaign = mission.getCampaign();
        this.structuresForMission = structuresForMission;
        this.nonScriptedStructuresForMission = nonScriptedStructuresForMission;
    }

    public List<ScriptedFixedPosition> getStructuresWithinMissionBorders()
    {
        List<ScriptedFixedPosition> structuresWithinMissionBorders = new ArrayList<>();
        for (ScriptedFixedPosition structure : structuresForMission)
        {
            if (mission.getStructureBorders().isInBox(structure.getPosition()))
            {
                structuresWithinMissionBorders.add(structure);
            }
        }
        return structuresWithinMissionBorders;
    }

    public List<NonScriptedBlock> getNonScriptedStructuresForMission()
    {
        return nonScriptedStructuresForMission;
    }

    public List<ScriptedFixedPosition> getAllStructuresForMission()
    {
        return structuresForMission;
    }

    public void adjustBlockDamageAndSmoke() throws PWCGException
    {
        if (PWCGContext.getInstance().getMap(campaign.getCampaignMap()).isLimited(mission.getCampaign().getDate(), PwcgMapGroundUnitLimitation.LIMITATION_BATTLE))
        {
            return;
        }

        if (mission.isSeaSkirmish())
        {
            return;
        }

        adjustBlockDurability();
        List<ScriptedFixedPosition> damagedStructures = adjustBlockDamage();
        createBlockSmoke(damagedStructures);
    }
    
    private void createBlockSmoke(List<ScriptedFixedPosition> damagedStructures) throws PWCGException
    {
        MissionBlockSmoke missionBlockSmoke = new MissionBlockSmoke(mission);      
        missionBlockSmoke.addSmokeToDamagedAreas(damagedStructures);
    }

    private List<ScriptedFixedPosition> adjustBlockDamage() throws PWCGException
    {
        MissionBlockDamageDecorator missionBlockDamage = new MissionBlockDamageDecorator(campaign);      
        return missionBlockDamage.setDamageToFixedPositions(structuresForMission, mission.getCampaign().getDate());
    }

    private void adjustBlockDurability()
    {
        BlockDefinitionManager blockDefinitionManager = BlockDefinitionManager.getInstance();
        for (ScriptedFixedPosition fixedPosition : structuresForMission)
        {
            BlockDefinition blockDefinition = blockDefinitionManager.getBlockDefinition(fixedPosition);
            if (blockDefinition != null)
            {
                fixedPosition.setDurability(blockDefinition.getDurability());
            }
        }
    }

    public void removeExtraStructures(Coordinate truckCoordinates, int keepRadius)
    {
        List<ScriptedFixedPosition> structuresToKeep = new ArrayList<>();
        for (ScriptedFixedPosition structureForMission : structuresForMission)
        {
            double distance = MathUtils.calcDist(truckCoordinates, structureForMission.getPosition());
            if (distance < keepRadius)
            {
                structuresToKeep.add(structureForMission);
            }
        }
        structuresForMission = structuresToKeep;    
    }
}
