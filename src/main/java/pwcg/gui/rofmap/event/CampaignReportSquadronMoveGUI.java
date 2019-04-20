package pwcg.gui.rofmap.event;

import pwcg.aar.ui.events.model.SquadronMoveEvent;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;

public class CampaignReportSquadronMoveGUI extends CampaignDocumentGUI
{
	private static final long serialVersionUID = 1L;
	private SquadronMoveEvent squadronMoveEvent = null;

	public CampaignReportSquadronMoveGUI(SquadronMoveEvent squadronMoveEvent)
	{
		super();

        this.squadronMoveEvent = squadronMoveEvent;
		makePanel();		
	}

    protected String getHeaderText() throws PWCGException
    {
        String transferHeaderText = "Notification of Squadron Relocation \n\n";
        return transferHeaderText;
    }

    protected String getBodyText() throws PWCGException
    {
        String squadronMoveText = "Squadron: " + squadronMoveEvent.getSquadron() + "\n";
        squadronMoveText += "Date: " + DateUtils.getDateStringPretty(squadronMoveEvent.getDate()) + "\n";
        squadronMoveText += squadronMoveEvent.getSquadron() + 
                        " has been moved to " + squadronMoveEvent.getNewAirfield() + ".\n";   
        
        
        return squadronMoveText;
	}


    @Override
    public void finished()
    {
    }
}
