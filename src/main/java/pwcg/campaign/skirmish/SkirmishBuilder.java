package pwcg.campaign.skirmish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.core.utils.MathUtils;
import pwcg.mission.MissionHumanParticipants;

public class SkirmishBuilder
{
    private Campaign campaign;
    private MissionHumanParticipants participatingPlayers;

    public SkirmishBuilder(Campaign campaign, MissionHumanParticipants participatingPlayers)
    {
        this.campaign = campaign;
        this.participatingPlayers = participatingPlayers;
    }

    public Skirmish chooseBestSkirmish() throws PWCGException
    {
        SkirmishManager skirmishManager = PWCGContext.getInstance().getCurrentMap().getSkirmishManager();
        List<Skirmish> skirmishes = skirmishManager.getSkirmishesForDate(campaign, participatingPlayers);
        List<Skirmish> candidateSkirmishes = new ArrayList<>();
        
        for (Skirmish skirmish : skirmishes)
        {
            Coordinate skirmishCenter = skirmish.getCenter();
            if (isWithinRange(skirmishCenter))
            {
                candidateSkirmishes.add(skirmish);
            }
        }
        
        if (!candidateSkirmishes.isEmpty())
        {
            Collections.shuffle(candidateSkirmishes);
            return candidateSkirmishes.get(0);
        }
        
        return null;
    }

    private boolean isWithinRange(Coordinate skirmishCenter) throws PWCGException
    {
        for (SquadronMember player : participatingPlayers.getAllParticipatingPlayers())
        {
            Squadron squadron = PWCGContext.getInstance().getSquadronManager().getSquadron(player.getSquadronId());
            Coordinate squadronPosition = squadron.determineCurrentPosition(campaign.getDate());
            double distance = MathUtils.calcDist(skirmishCenter, squadronPosition);
            if (distance > SkirmishDistance.findMaxSkirmishDistance())
            {
                return false;
            }            
        }
        return true;
    }
}
