package pwcg.aar.ui.events.model;

import pwcg.campaign.squadmember.Victory;


public class VictoryEvent extends AARPilotEvent
{
    private Victory victory;

    public VictoryEvent(int squadronId)
    {
        super(squadronId);
    }

    public Victory getVictory()
    {
        return victory;
    }

    public void setVictory(Victory victory)
    {
        this.victory = victory;
    }

}
