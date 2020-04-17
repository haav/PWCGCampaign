package pwcg.gui.rofmap.brief;

import java.util.List;

import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.mission.flight.FlightTypes;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.waypoint.WaypointAction;
import pwcg.mission.mcu.McuWaypoint;

public class BriefParametersContextBuilder
{
	private IFlight playerFlight;
	private BriefingFlightParameters briefParametersContext = new BriefingFlightParameters();

	public BriefParametersContextBuilder (IFlight playerFlight)
	{
        this.playerFlight = playerFlight;
	}
	
	public BriefingFlightParameters buildBriefParametersContext() throws PWCGException
	{
		addTakeoff();
		setWaypoints();
		addLanding();
		
		return briefParametersContext;
	}
	
	private void setWaypoints() throws PWCGException
	{
		McuWaypoint prevWaypoint = null;
		for (McuWaypoint waypoint :  playerFlight.getWaypointPackage().getAllWaypoints())
		{				
		     addPlayerFlightWaypoint(prevWaypoint, waypoint);

		     if (playerFlight.getFlightType() == FlightTypes.ESCORT)
		     {
		         if (waypoint.getWpAction() == WaypointAction.WP_ACTION_RENDEZVOUS)
		         {
		             updateEscortWaypointsOnMap();
		         }
		     }

		     if (waypoint.getWpAction() == WaypointAction.WP_ACTION_TARGET_FINAL)
		     {
		    	 addAttackPoint(playerFlight.getTargetDefinition().getTargetPosition());
		     }
		     
		     prevWaypoint = waypoint;
		}
	}

	private void addPlayerFlightWaypoint(McuWaypoint prevWaypoint, McuWaypoint waypoint) throws PWCGException
	{
	    BriefingMapPoint waypointMapPoint = waypointToMapPoint(waypoint);
	    WaypointEditor waypointEditor = createWaypointEditor(prevWaypoint, waypoint);
	    EditorWaypointGroup editorWaypointGroup = new EditorWaypointGroup ();
	    editorWaypointGroup.setWaypointEditor(waypointEditor);
	    editorWaypointGroup.setWaypointInBriefing(waypoint);
	    editorWaypointGroup.setBriefingMapPoint(waypointMapPoint);
	    briefParametersContext.appendEditorGroup(editorWaypointGroup);
	}

    private WaypointEditor createWaypointEditor(McuWaypoint previousWP, McuWaypoint thisWP) throws PWCGException
    {
        WaypointEditor wpEdit = new WaypointEditor();
        wpEdit.initializeWPEdit(previousWP, thisWP);
        return wpEdit;
    }
    

	private BriefingMapPoint waypointToMapPoint(McuWaypoint waypoint)
	{
		BriefingMapPoint mapPoint = BriefingMapPointFactory.waypointToMapPoint(waypoint);
		return mapPoint;
	}

	public void addTakeoff() throws PWCGException 
	{
        BriefingMapPoint briefingMapTakeoff = BriefingMapPointFactory.createTakeoff(playerFlight);
        if (briefingMapTakeoff != null)
		{

			EditorWaypointGroup editorWaypointGroup = new EditorWaypointGroup();
			editorWaypointGroup.setBriefingMapPoint(briefingMapTakeoff);
			editorWaypointGroup.setWaypointEditor(null);
			editorWaypointGroup.setWaypointInBriefing(null);
			
			briefParametersContext.appendEditorGroup(editorWaypointGroup);
		}
	}
	
	public void addLanding() throws PWCGException 
	{
        BriefingMapPoint briefingMapLanding = BriefingMapPointFactory.createLanding(playerFlight);
		if (briefingMapLanding != null)
		{
			EditorWaypointGroup editorWaypointGroup = new EditorWaypointGroup();
			editorWaypointGroup.setBriefingMapPoint(briefingMapLanding);
			editorWaypointGroup.setWaypointEditor(null);
			editorWaypointGroup.setWaypointInBriefing(null);

			briefParametersContext.appendEditorGroup(editorWaypointGroup);
		}
	}
	
	private void addAttackPoint(Coordinate targetLocation) throws PWCGException 
	{
        BriefingMapPoint target = BriefingMapPointFactory.createAttackPoint(targetLocation);

		EditorWaypointGroup editorWaypointGroup = new EditorWaypointGroup();
		editorWaypointGroup.setBriefingMapPoint(target);
		editorWaypointGroup.setWaypointEditor(null);
		editorWaypointGroup.setWaypointInBriefing(null);
		
		briefParametersContext.appendEditorGroup(editorWaypointGroup);
	}
	
    private void updateEscortWaypointsOnMap() throws PWCGException
    {
    	List<McuWaypoint> escortedWaypoints = playerFlight.getLinkedFlights().getLinkedWaypoints().getAllWaypoints();
	    if (escortedWaypoints != null)
	    {
	        for (McuWaypoint waypoint : escortedWaypoints)
	        {
	            if (waypoint.getWpAction() == WaypointAction.WP_ACTION_LANDING_APPROACH)
	            {
	                continue;
	            }
	            
	            addEscortPoint(waypoint);
	        }               
	    }
    }
    
    private void addEscortPoint(McuWaypoint escortWaypoint)
	{
		BriefingMapPoint escort = new BriefingMapPoint();
		escort.desc = "Escort";
		escort.coord = escortWaypoint.getPosition().copy();
		escort.editable = false;

		EditorWaypointGroup editorWaypointGroup = new EditorWaypointGroup();
		editorWaypointGroup.setBriefingMapPoint(escort);
		editorWaypointGroup.setWaypointEditor(null);
		editorWaypointGroup.setWaypointInBriefing(null);
		briefParametersContext.appendEditorGroup(editorWaypointGroup);
	}

}
