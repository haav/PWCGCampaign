package pwcg.mission.mcu.group;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.api.IProductSpecificConfiguration;
import pwcg.campaign.factory.ProductSpecificConfigurationFactory;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.core.location.Orientation;
import pwcg.mission.MissionBeginUnit;
import pwcg.mission.flight.FlightInformation;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.plane.PlaneMcu;
import pwcg.mission.flight.waypoint.WaypointPriority;
import pwcg.mission.ground.vehicle.IVehicle;
import pwcg.mission.mcu.BaseFlightMcu;
import pwcg.mission.mcu.McuAttackTarget;
import pwcg.mission.mcu.McuCounter;
import pwcg.mission.mcu.McuDeactivate;
import pwcg.mission.mcu.McuEvent;
import pwcg.mission.mcu.McuForceComplete;
import pwcg.mission.mcu.McuProximity;
import pwcg.mission.mcu.McuSubtitle;
import pwcg.mission.mcu.McuTimer;
import pwcg.mission.target.TargetDefinition;

public class AirGroundAttackTargetMcuSequence implements IAirGroundAttackAreaMcuSequence
{    
    private FlightInformation flightInformation;
    private TargetDefinition targetDefinition;

    private MissionBeginUnit missionBeginUnit;
    private McuTimer proximityStartTimer = new McuTimer();
    private McuProximity mcuProximity = new McuProximity();
    private McuTimer attackActivateTimer = new McuTimer();
    private McuTimer attackTimeoutTimer = new McuTimer();
    private McuDeactivate attackDeactivateEntity = new McuDeactivate();
    private McuAttackTarget attackTarget = new McuAttackTarget();
    private McuCounter bingoBombsCounter;
    private McuTimer exitAttackTimer = new McuTimer();
    private McuDeactivate killTimeoutTimerEntity = new McuDeactivate();
    private McuForceComplete forceCompleteDropOrdnance;
    private List<McuSubtitle> subTitleList = new ArrayList<McuSubtitle>();

    public AirGroundAttackTargetMcuSequence(IFlight flight)
    {
        this.flightInformation = flight.getFlightInformation();
        this.targetDefinition = flight.getTargetDefinition();
        missionBeginUnit = new MissionBeginUnit(targetDefinition.getPosition());
    }
        
    
    public void createAttackSequence(int maxAttackTimeSeconds, int bingoLoiterTimeSeconds) throws PWCGException 
    {
        buildBingoCounter();
        buildAttackAreaTrigger();
        buildAttackAreaElements(maxAttackTimeSeconds, bingoLoiterTimeSeconds);
        createTargetAssociations();
        makeSubtitles();
    }


    private void buildBingoCounter()
    {
        if (flightInformation.getMission().isAAATruckMission())
        {
            bingoBombsCounter = new McuCounter(50, 0);        
        }
        else
        {
            int bingoCount = (flightInformation.getPlanes().size() / 2) + 1;
            if (bingoCount < 2)
            {
                bingoCount = 2;
            }
            
            bingoBombsCounter = new McuCounter(bingoCount, 0);
        }
    }


    private void buildAttackAreaTrigger()
    {
        IProductSpecificConfiguration productSpecificConfiguration = ProductSpecificConfigurationFactory.createProductSpecificConfiguration();
        int attackMcuTriggerDistance = productSpecificConfiguration.getAttackAreaSelectTargetRadius();
        
        mcuProximity.setDistance(attackMcuTriggerDistance);
    }


    @Override
    public void setAttackToTriggerOnPlane(List<PlaneMcu> planes) throws PWCGException 
    {
        mcuProximity.setObject(planes.get(0).getLinkTrId());
        
        for (PlaneMcu plane : planes)
        {
            forceCompleteDropOrdnance.setObject(plane.getLinkTrId());
            setBingoBombsEventsForPlane(plane);
        }
    }

    @Override
    public void setVehiclesToAttack(List<IVehicle> vehicles) throws PWCGException
    {
        for (IVehicle vehicle : vehicles)
        {
            attackTarget.setTarget(vehicle.getLinkTrId());
            attackTarget.setTarget(vehicle.getLinkTrId());
        }
        
    }

    @Override
    public void write(BufferedWriter writer) throws PWCGException 
    {
        missionBeginUnit.write(writer);
        proximityStartTimer.write(writer);
        mcuProximity.write(writer);
        attackActivateTimer.write(writer);
        attackTarget.write(writer);
        attackTimeoutTimer.write(writer);
        attackDeactivateEntity.write(writer);
        bingoBombsCounter.write(writer);
        exitAttackTimer.write(writer);
        killTimeoutTimerEntity.write(writer);
        forceCompleteDropOrdnance.write(writer);
        
        McuSubtitle.writeSubtitles(subTitleList, writer);
    }

    @Override
    public void setLinkToNextTarget(int targetIndex)
    {
        exitAttackTimer.setTarget(targetIndex);
    }

    @Override
    public BaseFlightMcu getAttackAreaMcu()
    {
        return attackTarget;
    }

    @Override
    public Coordinate getPosition()
    {
        return attackTarget.getPosition();
    }

    private void createTargetAssociations() 
    {
        missionBeginUnit.linkToMissionBegin(proximityStartTimer.getIndex());
        proximityStartTimer.setTarget(mcuProximity.getIndex());
        mcuProximity.setTarget(attackActivateTimer.getIndex());
        
        attackActivateTimer.setTarget(attackTarget.getIndex());
        attackActivateTimer.setTarget(attackTimeoutTimer.getIndex());
        attackActivateTimer.setTarget(bingoBombsCounter.getIndex());
        
        bingoBombsCounter.setTarget(exitAttackTimer.getIndex());
        attackTimeoutTimer.setTarget(exitAttackTimer.getIndex());

        exitAttackTimer.setTarget(killTimeoutTimerEntity.getIndex());
        exitAttackTimer.setTarget(attackDeactivateEntity.getIndex());
        exitAttackTimer.setTarget(forceCompleteDropOrdnance.getIndex());
        
        attackDeactivateEntity.setTarget(attackTarget.getIndex());
        killTimeoutTimerEntity.setTarget(attackTimeoutTimer.getIndex());
    }

    private void makeSubtitles() throws PWCGException
    {
        Coordinate subtitlePosition = targetDefinition.getPosition();
        
        McuSubtitle proximitySubtitle = McuSubtitle.makeActivatedSubtitle("Proximity triggered ", subtitlePosition);
        mcuProximity.setTarget(proximitySubtitle.getIndex());
        subTitleList.add(proximitySubtitle);

        McuSubtitle bingoSubtitle = McuSubtitle.makeActivatedSubtitle("Stop attack due to bingo count ", subtitlePosition);
        bingoBombsCounter.setTarget(bingoSubtitle.getIndex());
        subTitleList.add(bingoSubtitle);
        
        McuSubtitle timeoutSubtitle = McuSubtitle.makeActivatedSubtitle("Stop attack due to attack timeout: ", subtitlePosition);
        attackTimeoutTimer.setTarget(timeoutSubtitle.getIndex());
        subTitleList.add(timeoutSubtitle);
    }

    private void buildAttackAreaElements(int attackTime, int bingoLoiterTimeSeconds) 
    {        
        proximityStartTimer.setName("Attack Target Proximity Timer");      
        proximityStartTimer.setDesc("Attack Target Proximity Timer");
        proximityStartTimer.setPosition(targetDefinition.getPosition());
        proximityStartTimer.setTime(1);              
        
        mcuProximity.setName("Attack Target Proximity");      
        mcuProximity.setDesc("Attack Target Proximity");
        mcuProximity.setPosition(targetDefinition.getPosition());

        attackActivateTimer.setName("Attack Target Activate Timer");      
        attackActivateTimer.setDesc("Attack Target Activate Timer");
        attackActivateTimer.setPosition(targetDefinition.getPosition());        
        
        attackTimeoutTimer.setName("Attack Target Timeout Timer");
        attackTimeoutTimer.setDesc("Attack Target Timeout Timer");
        attackTimeoutTimer.setOrientation(new Orientation());
        attackTimeoutTimer.setPosition(targetDefinition.getPosition());              
        attackTimeoutTimer.setTime(attackTime);              

        attackTarget.setName("Attack Target");
        attackTarget.setDesc("Attack Target");
        attackTarget.setOrientation(new Orientation());
        attackTarget.setPosition(targetDefinition.getPosition());              

        attackDeactivateEntity.setName("Attack Target Deactivate");
        attackDeactivateEntity.setDesc("Attack Target Deactivate");
        attackDeactivateEntity.setOrientation(new Orientation());
        attackDeactivateEntity.setPosition(targetDefinition.getPosition());             
        
        exitAttackTimer.setName("Attack Target Exit Timer");
        exitAttackTimer.setDesc("Attack Target Exit Timer");
        exitAttackTimer.setOrientation(new Orientation());
        exitAttackTimer.setPosition(targetDefinition.getPosition());              
        exitAttackTimer.setTime(bingoLoiterTimeSeconds);              

        bingoBombsCounter.setName("Bingo bombs counter");      
        bingoBombsCounter.setDesc("Bingo bombs counter");
        bingoBombsCounter.setPosition(targetDefinition.getPosition());

        killTimeoutTimerEntity.setName("Timeout Timer Deactivate");
        killTimeoutTimerEntity.setDesc("Timeout Time Deactivate");
        killTimeoutTimerEntity.setOrientation(new Orientation());
        killTimeoutTimerEntity.setPosition(targetDefinition.getPosition());

        int emergencyDropOrdnance = 1;
        forceCompleteDropOrdnance = new McuForceComplete(WaypointPriority.PRIORITY_HIGH, emergencyDropOrdnance);
        forceCompleteDropOrdnance.setName("Escort Cover Force Complete");
        forceCompleteDropOrdnance.setDesc("Escort Cover Force Complete");
        forceCompleteDropOrdnance.setOrientation(new Orientation());
        forceCompleteDropOrdnance.setPosition(targetDefinition.getPosition());
    }
    
    private void setBingoBombsEventsForPlane(PlaneMcu plane) throws PWCGException
    {
        int bingoElement = McuEvent.ONPLANEBINGOBOMBS;
        if (!plane.getPlanePayload().isOrdnance())
        {
            bingoElement = McuEvent.ONPLANEBINGOAMMO;
        }
        
        McuEvent planeEvent = new McuEvent(bingoElement, bingoBombsCounter.getIndex());
        plane.addEvent(planeEvent);
    }    
}
