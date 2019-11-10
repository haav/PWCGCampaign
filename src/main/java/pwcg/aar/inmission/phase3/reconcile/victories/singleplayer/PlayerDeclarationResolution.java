package pwcg.aar.inmission.phase3.reconcile.victories.singleplayer;

import java.util.Map;

import pwcg.aar.inmission.phase2.logeval.AARMissionEvaluationData;
import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogPlane;
import pwcg.aar.inmission.phase2.logeval.missionresultentity.LogVictory;
import pwcg.aar.inmission.phase3.reconcile.victories.common.ConfirmedVictories;
import pwcg.aar.inmission.phase3.reconcile.victories.common.VictorySorter;
import pwcg.campaign.Campaign;
import pwcg.campaign.plane.PlaneType;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.core.exception.PWCGException;

public class PlayerDeclarationResolution
{
    private ConfirmedVictories confirmedPlayerVictories = new ConfirmedVictories();
    private AARMissionEvaluationData evaluationData;
    private Campaign campaign;
    private VictorySorter victorySorter;
    private Map<Integer, PlayerDeclarations> playerDeclarations;
    
    private PlayerClaimResolverFirm claimResolverFirm = new PlayerClaimResolverFirm();
    private PlayerClaimResolverFuzzy claimResolverFuzzy = new PlayerClaimResolverFuzzy();
    
    PlayerDeclarationResolution (Campaign campaign, AARMissionEvaluationData evaluationData, VictorySorter victorySorter, Map<Integer, PlayerDeclarations> playerDeclarations)
    {
        this.campaign = campaign;
        this.evaluationData = evaluationData;
        this.victorySorter = victorySorter;
        this.playerDeclarations = playerDeclarations;
    }

    ConfirmedVictories determinePlayerAirResultsWithClaims  () throws PWCGException 
    {
        for (Integer playerSerialNumber : playerDeclarations.keySet())
        {
            PlayerDeclarations playerDeclaration = playerDeclarations.get(playerSerialNumber);
            for (PlayerVictoryDeclaration victoryDeclaration : playerDeclaration.getDeclarations())
            {
                boolean resolved = false;
                
                if (!victoryDeclaration.getAircraftType().equals(PlaneType.BALLOON))
                {
                    resolvePlayerAircraftClaim(playerSerialNumber, victoryDeclaration);
                }
                else
                {
                    resolvePlayerBalloonClaim(playerSerialNumber, victoryDeclaration, resolved);
                }
            }
        }
        
        PlayerVictoryReassigner playerVictoryReassigner = new PlayerVictoryReassigner(campaign);
        playerVictoryReassigner.resetUnclamedPlayerVictoriesForAssignmentToOthers(victorySorter);
        
        return confirmedPlayerVictories;
    }

    private void resolvePlayerAircraftClaim(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {        
        boolean hasBeenConfirmed = resolveAsFirmVictory(playerSerialNumber, victoryDeclaration);
   
        if (!hasBeenConfirmed)
        {
            hasBeenConfirmed = resolveAsFirmVictoryNotExact(playerSerialNumber, victoryDeclaration);
        }
        
        if (!hasBeenConfirmed)
        {
            hasBeenConfirmed = resolveAsFuzzyVictory(playerSerialNumber, victoryDeclaration);
        }
        
        if (!hasBeenConfirmed)
        {
            hasBeenConfirmed = resolveAsFuzzyVictoryNotExact(playerSerialNumber, victoryDeclaration);
        }
    }

    private boolean resolveAsFirmVictory(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFirmAirVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                if (!resultVictory.isConfirmed())
                {
                    String shotDownPlaneDisplayName = claimResolverFirm.getShotDownPlaneDisplayNameAsFirm(player, victoryDeclaration, resultVictory);
                    if (!shotDownPlaneDisplayName.isEmpty())
                    {
                        generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, shotDownPlaneDisplayName);
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    private boolean resolveAsFirmVictoryNotExact(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFirmAirVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                String shotDownPlaneDisplayName = claimResolverFirm.getShotDownPlaneDisplayNameAsFirmNotExact(player, victoryDeclaration, resultVictory);
                if (!shotDownPlaneDisplayName.isEmpty())
                {
                    generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, shotDownPlaneDisplayName);
                    return true;
                }
            }
        }
        
        return false;
    }

    private boolean resolveAsFuzzyVictory(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFuzzyAirVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                String shotDownPlaneDisplayName = claimResolverFuzzy.getShotDownPlaneDisplayNameAsFuzzy(victoryDeclaration, resultVictory);
                if (!shotDownPlaneDisplayName.isEmpty())
                {
                    generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, shotDownPlaneDisplayName);
                    return true;
                }
            }
        }
        
        return false;
    }

    private boolean resolveAsFuzzyVictoryNotExact(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFuzzyAirVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                String shotDownPlaneDisplayName = claimResolverFuzzy.getShotDownPlaneDisplayNameAsFuzzyNotExact(player, victoryDeclaration, resultVictory);
                if (!shotDownPlaneDisplayName.isEmpty())
                {
                    generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, shotDownPlaneDisplayName);
                    return true;
                }
            }
        }
        
        return false;
    }

    private void resolvePlayerBalloonClaim(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration, boolean resolvedByFirmMatch) throws PWCGException
    {
        resolvedByFirmMatch = resolveAsFirmBalloonVictory(playerSerialNumber, victoryDeclaration);
        if (!resolvedByFirmMatch)
        {
            resolvedByFirmMatch = resolveAsFuzzyBalloonVictory(playerSerialNumber, victoryDeclaration);
        }
    }

    private boolean resolveAsFirmBalloonVictory(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFirmBalloonVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                if (resultVictory.getVictor() instanceof LogPlane)
                {
                    SquadronMember squadronMember = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
                    LogPlane victorPlanePlane = (LogPlane)resultVictory.getVictor();
                    if (PlayerVictoryResolver.isPlayerVictory(squadronMember, victorPlanePlane.getPilotSerialNumber()))
                    {
                        generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, resultVictory.getVictim().getVehicleType());
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    private boolean resolveAsFuzzyBalloonVictory(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration) throws PWCGException
    {
        for (LogVictory resultVictory : victorySorter.getFuzzyBalloonVictories())
        {
            SquadronMember player = campaign.getPersonnelManager().getAnyCampaignMember(playerSerialNumber);
            if (!VictoryResolverSameSideDetector.isSameSide(player, resultVictory))
            {
                generatePlayerVictoryIfNotAlreadyConfirmed(playerSerialNumber, victoryDeclaration, resultVictory, resultVictory.getVictim().getVehicleType());
                return true;
            }
        }
        
        return false;
    }

    private void generatePlayerVictoryIfNotAlreadyConfirmed(Integer playerSerialNumber, PlayerVictoryDeclaration victoryDeclaration, LogVictory resultVictory, String shotDownPlaneName) throws PWCGException
    {
        if (!resultVictory.isConfirmed())
        {
            victoryDeclaration.confirmDeclaration(true, shotDownPlaneName);
    
            LogPlane playerPlane = evaluationData.getPlaneInMissionBySerialNumber(playerSerialNumber);
            if (playerPlane != null)
            {
                resultVictory.setVictor(playerPlane);
                resultVictory.setConfirmed(true);
        
                confirmedPlayerVictories.addVictory(resultVictory);
            }
        }
    }

}
