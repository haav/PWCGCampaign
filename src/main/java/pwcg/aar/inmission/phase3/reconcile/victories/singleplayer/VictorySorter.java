package pwcg.aar.inmission.phase3.reconcile.victories.singleplayer;

import java.util.ArrayList;
import java.util.List;

import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogAIEntity;
import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogBalloon;
import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogUnknown;
import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogVictory;
import pwcg.campaign.plane.Role;
import pwcg.core.exception.PWCGException;

public class VictorySorter
{
    private List<LogVictory> firmAirVictories = new ArrayList <LogVictory>();
    private List<LogVictory> firmGroundVictories = new ArrayList <LogVictory>();
    private List<LogVictory> firmBalloonVictories = new ArrayList <LogVictory>();

    private List<LogVictory> fuzzyAirVictories = new ArrayList <LogVictory>();
    private List<LogVictory> fuzzyBalloonVictories = new ArrayList <LogVictory>();
    
    private enum VictoryState
    {
        FIRM,
        FUZZY
    }

    VictorySorter ()
    {
    }

    void sortVictories(List<LogVictory> logVictories) throws PWCGException 
    {                
        for (LogVictory logVictory : logVictories)
        {
            VictoryState victoryState = isFuzzy(logVictory);            
            if (victoryState == VictoryState.FUZZY)
            {
                sortFuzzyVictory(logVictory);
            }
            else if (victoryState == VictoryState.FIRM)
            {
                sortFirmVictory(logVictory);                
            }
        }
        
        confirmFirmGroundVicories() ;
    }

    private void sortFirmVictory(LogVictory logVictory) throws PWCGException
    {
        if (isVictimBalloon(logVictory))
        {
            firmBalloonVictories.add(logVictory);
        }
        else if (logVictory.getVictim().getRole() == Role.ROLE_GROUND_UNIT)
        {
            firmGroundVictories.add(logVictory);
        }
        else
        {
            firmAirVictories.add(logVictory);
        }
    }

    private void sortFuzzyVictory(LogVictory logVictory) throws PWCGException
    {
        if (isVictimBalloon(logVictory))
        {
            fuzzyBalloonVictories.add(logVictory);
        }
        else if (logVictory.getVictim().getRole() == Role.ROLE_GROUND_UNIT)
        {
            // We don't do anything with fuzzy ground victories
        }
        else
        {
            fuzzyAirVictories.add(logVictory);
        }
    }

    private void confirmFirmGroundVicories() throws PWCGException 
    {
        for (LogVictory logVictory : firmGroundVictories)
        {
            logVictory.setConfirmed(true);
        }
    }

    private VictoryState isFuzzy(LogVictory logVictor)
    {
        if (logVictor.getVictor() instanceof LogUnknown)
        {
            return VictoryState.FUZZY;
        }
        
        return VictoryState.FIRM;
    }

    private boolean isVictimBalloon(LogVictory logVictory) throws PWCGException
    {
        LogAIEntity logVictim = logVictory.getVictim();               
        if (logVictim != null)
        {
            if (logVictim instanceof LogBalloon)
            {
                return true;
            }
        }
        
        return false;
    }

    List<LogVictory> getFirmAirVictories()
    {
        return firmAirVictories;
    }

    List<LogVictory> getFirmGroundVictories()
    {
        return firmGroundVictories;
    }

    List<LogVictory> getFirmBalloonVictories()
    {
        return firmBalloonVictories;
    }

    List<LogVictory> getFuzzyAirVictories()
    {
        return fuzzyAirVictories;
    }

    List<LogVictory> getFuzzyBalloonVictories()
    {
        return fuzzyBalloonVictories;
    }

    List<LogVictory> getAllUnconfirmed()
    {
        List<LogVictory> unconfirmed = new ArrayList<LogVictory>();
        extractUnconfirmed(firmAirVictories, unconfirmed);
        extractUnconfirmed(firmBalloonVictories, unconfirmed);
        extractUnconfirmed(fuzzyAirVictories, unconfirmed);
        extractUnconfirmed(fuzzyBalloonVictories, unconfirmed);
        
        return unconfirmed;
    }


    private void extractUnconfirmed(List<LogVictory> victoryList, List<LogVictory> unconfirmed)
    {
        for (LogVictory resultVictory : victoryList )
        {
            if (!resultVictory.isConfirmed())
            {
                unconfirmed.add(resultVictory);
            }
        }
    }
 
}
