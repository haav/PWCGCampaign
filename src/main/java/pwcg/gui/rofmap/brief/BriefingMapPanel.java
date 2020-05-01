package pwcg.gui.rofmap.brief;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pwcg.campaign.api.Side;
import pwcg.campaign.context.PWCGContext;
import pwcg.core.exception.PWCGException;
import pwcg.core.location.Coordinate;
import pwcg.core.location.CoordinateBox;
import pwcg.core.utils.MathUtils;
import pwcg.core.utils.PWCGLogger;
import pwcg.gui.colors.ColorMap;
import pwcg.gui.dialogs.ImageCache;
import pwcg.gui.dialogs.MonitorSupport;
import pwcg.gui.rofmap.MapPanelBase;
import pwcg.gui.utils.MapPointInfoPopup;
import pwcg.mission.Mission;
import pwcg.mission.flight.IFlight;
import pwcg.mission.flight.waypoint.missionpoint.MissionPoint;
import pwcg.mission.target.AssaultDefinition;

public class BriefingMapPanel extends MapPanelBase implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static int NO_MAP_POINT_SELECTED = -1;
	
	private BriefingFlightParameters briefingFlightParameters;
    private List <FlightMap> alliedVirtualPoints = new ArrayList<FlightMap>();
    private List <FlightMap> axisVirtualPoints = new ArrayList<FlightMap>();
    private CoordinateBox missionBorders = new CoordinateBox();
    private Mission mission;
    private Map<Integer, String> selectedSquadrons;

	private BriefingMapGUI parent = null;

	public BriefingMapPanel(BriefingMapGUI parent, BriefingFlightParameters briefingFlightParameters, Mission mission, Map<Integer, String> selectedSquadrons) throws PWCGException 
	{
		super(parent);
		
		this.parent = parent;
		this.briefingFlightParameters = briefingFlightParameters;
        this.mission = mission;
        this.selectedSquadrons = selectedSquadrons;
	}

	public void paintComponent(Graphics g)
	{
		try
		{
			super.paintBaseMapWithMajorGroups(g);
            			                
            g.setColor(Color.black);
            drawFlightWaypoints(g);
            
            g.setColor(ColorMap.RUSSIAN_RED);
            drawAiFlightWaypoints(g, alliedVirtualPoints);            
            
            g.setColor(ColorMap.FRENCH_NAVY_BLUE);
            drawAiFlightWaypoints(g, axisVirtualPoints);            
            
            drawAssaults(g);            
		}
		catch (Exception e)
		{
			PWCGLogger.logException(e);
		}
	}

    private void drawAssaults(Graphics g) throws PWCGException
    {                
        for (AssaultDefinition assaultDefinition : mission.getMissionBattleManager().getMissionAssaultDefinitions())
        {
            BufferedImage arrowImage = null;
            String imagePath = PWCGContext.getInstance().getDirectoryManager().getPwcgImagesDir() + "Overlay\\";
            if (assaultDefinition.getAssaultingCountry().getSide() == Side.ALLIED)
            {
                imagePath += "RedArrow.png";
            }
            else
            {
                imagePath += "BlueArrow.png";
            }

            double assaultToDefenseDirection = MathUtils.calcAngle(assaultDefinition.getAssaultPosition(), assaultDefinition.getDefensePosition());
            arrowImage = ImageCache.getInstance().getRotatedImage(imagePath, Double.valueOf(assaultToDefenseDirection).intValue());

            Point mapAssaultPointRectangleFrontPoint = coordinateToPoint(assaultDefinition.getDefensePosition());
            mapAssaultPointRectangleFrontPoint.x -= (arrowImage.getHeight() / 2);
            mapAssaultPointRectangleFrontPoint.y -= (arrowImage.getHeight() / 2);            
            
            g.drawImage(arrowImage, mapAssaultPointRectangleFrontPoint.x, mapAssaultPointRectangleFrontPoint.y, null);

        }
    }

    private void drawFlightWaypoints(Graphics g) throws PWCGException 
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
                
        List<BriefingMapPoint> drawPoints = sortWaypoints();
        
        Color requestedColor = g.getColor();
 
        BriefingMapPoint prev = null;
        for (int i = 0; i < drawPoints.size(); ++i)
        {
            BriefingMapPoint mapPoint = drawPoints.get(i);
            
            g.setColor(requestedColor);
            
            if (prev != null)
            {
                Point prevPoint = super.coordinateToPoint(prev.coord);
                Point point = super.coordinateToPoint(mapPoint.coord);
                
                g2.draw(new Line2D.Float(prevPoint.x, prevPoint.y, point.x, point.y));
            }

            Font font = MonitorSupport.getPrimaryFont();
            g.setFont(font);
            Point point = super.coordinateToPoint(mapPoint.coord);

            // Grey out uneditable mission WPs
            if (mapPoint.editable == false && g.getColor() == Color.BLACK)
            {
                g.setColor(Color.GRAY);
            }

            // Mark targets as red
            if (mapPoint.desc.equals("Target"))
            {
                g.setColor(Color.RED);
            }

            Ellipse2D.Double circle = new Ellipse2D.Double(point.x - 4, point.y - 4, 8, 8);
            g2.fill(circle);

            g.drawString(mapPoint.desc, point.x + 4, point.y);

            prev = mapPoint;
        }
    }

    private List<BriefingMapPoint> sortWaypoints()
    {
        List <BriefingMapPoint> drawPoints = new ArrayList <BriefingMapPoint>();

        for (EditorWaypointGroup editorGroup : briefingFlightParameters.getWaypointEditorGroups())
        {
            drawPoints.add(editorGroup.getBriefingMapPoint());
        }
        
        return drawPoints;
    }

    private void drawAiFlightWaypoints(Graphics g, List<FlightMap> flightMaps) throws PWCGException 
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
                
        Color requestedColor = g.getColor();
 
        for (FlightMap flightMap : flightMaps)
        {
            if (selectedSquadrons.containsKey(flightMap.squadronId))
            {
                paintWaypointLines(g, g2, requestedColor, flightMap);
            }
        }
        
        for (FlightMap flightMap : flightMaps)
        {
            if (selectedSquadrons.containsKey(flightMap.squadronId))
            {
                paintWaypoints(g, g2, flightMap);
            }
        }
        
        drawMissionBorders(g, g2);
    }

    private void paintWaypoints(Graphics g, Graphics2D g2, FlightMap flightMap) throws PWCGException
    {
        for (int i = 0; i < flightMap.mapPoints.size(); ++i)
        {
            BriefingMapPoint mapPoint = flightMap.mapPoints.get(i);
            if (i == 0)
            {
                g.setColor(Color.GREEN);
                Point point = super.coordinateToPoint(mapPoint.coord);
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x - 8, point.y - 8, 16, 16);
                g2.fill(circle);
                
                g.setColor(Color.BLACK);
                Font font = MonitorSupport.getPrimaryFont();
                g.setFont(font);
                g.drawString((flightMap.flightType + "/" + flightMap.planeType), point.x + 4, point.y);
            }
            if (i == (flightMap.mapPoints.size()-1) )
            {
                g.setColor(Color.RED);
                Point point = super.coordinateToPoint(mapPoint.coord);
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x - 8, point.y - 8, 16, 16);
                g2.fill(circle);
            }
            if (mapPoint.isTarget)
            {
                g.setColor(Color.RED);
                Point point = super.coordinateToPoint(mapPoint.coord);
                Ellipse2D.Double circle = new Ellipse2D.Double(point.x - 8, point.y - 8, 16, 16);
                g2.fill(circle);
            }
        }
    }

    private void paintWaypointLines(Graphics g, Graphics2D g2, Color requestedColor, FlightMap flightMap)
    {
        BriefingMapPoint prevMapPoint = null;
        
         for (int i = 0; i < flightMap.mapPoints.size(); ++i)
         {
            BriefingMapPoint mapPoint = flightMap.mapPoints.get(i);
            g.setColor(requestedColor);

            Point point = super.coordinateToPoint(mapPoint.coord);
            Ellipse2D.Double circle = new Ellipse2D.Double(point.x - 4, point.y - 4, 8, 8);
            g2.fill(circle);

            g2.drawString(mapPoint.desc, point.x + 4, point.y);

            if (prevMapPoint != null)
            {
                Point prevPoint = super.coordinateToPoint(prevMapPoint.coord);
                
                g2.draw(new Line2D.Float(prevPoint.x, prevPoint.y, point.x, point.y));
            }

            prevMapPoint = mapPoint;
        }
    }

    private void drawMissionBorders(Graphics g, Graphics2D g2)
    {
        Coordinate sw = missionBorders.getSW().copy();
        Coordinate se = new Coordinate(missionBorders.getNE().getXPos(), 0.0, missionBorders.getSW().getZPos());
        Coordinate ne = missionBorders.getNE().copy();
        Coordinate nw = new Coordinate(missionBorders.getSW().getXPos(), 0.0, missionBorders.getNE().getZPos());

        Point swPoint = super.coordinateToPoint(sw);
        Point sePoint = super.coordinateToPoint(se);
        Point nePoint = super.coordinateToPoint(ne);
        Point nwPoint = super.coordinateToPoint(nw);
        
        g.setColor(Color.YELLOW);
        g2.draw(new Line2D.Float(swPoint.x, swPoint.y, sePoint.x, sePoint.y));
        g2.draw(new Line2D.Float(sePoint.x, sePoint.y, nePoint.x, nePoint.y));
        g2.draw(new Line2D.Float(nePoint.x, nePoint.y, nwPoint.x, nwPoint.y));
        g2.draw(new Line2D.Float(nwPoint.x, nwPoint.y, swPoint.x, swPoint.y));
    }

    public void  clearVirtualPoints()
    {
        alliedVirtualPoints.clear();
        axisVirtualPoints.clear();        
    }

    public void makeMapPanelVirtualPoints(IFlight flight) throws PWCGException
    {       
        FlightMap flightMap = buildFlightMap(flight);
        if (flight.getFlightInformation().getCountry().getSideNoNeutral() == Side.ALLIED)
        {
            alliedVirtualPoints.add(flightMap);
        }
        else
        {
            axisVirtualPoints.add(flightMap);
        }
    }

    private FlightMap buildFlightMap(IFlight flight) throws PWCGException
    {
        FlightMap flightMap = new FlightMap();
        flightMap.flightType = flight.getFlightType().toString();
        flightMap.planeType = flight.getFlightPlanes().getFlightLeader().getDisplayName();
        flightMap.squadronId = flight.getSquadron().getSquadronId();
        
        for (MissionPoint waypoint : flight.getWaypointPackage().getFlightMissionPoints())
        {
            BriefingMapPoint mapPoint = BriefingMapPointFactory.missionPointToMapPoint(waypoint);
            flightMap.mapPoints.add(mapPoint);
        }
        
        return flightMap;
    }

	public void mouseMovedCallback(MouseEvent e) 
	{
		if (mission.isFinalized())
		{
			return;			
		}
		
		int movedIndex = determineSelectedWaypointIndex(e.getX(), e.getY());
		if (movedIndex == NO_MAP_POINT_SELECTED)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		else
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
        super.mouseDraggedCallback(e);

	}

	public void mouseDraggedCallback(MouseEvent mouseEvent)
	{
	    if (!movementEnabled)
	    {
	        if (!mission.isFinalized())
	        {
    	    	BriefingMapPoint selectedMapPoint = briefingFlightParameters.getSelectedMapPoint();
    	    	if (selectedMapPoint != null)
    	    	{
    		        Point point = new Point();
    		        point.x = mouseEvent.getX();
    		        point.y = mouseEvent.getY();
    
    		        try
    		        {
    		            Coordinate newCoord = this.pointToCoordinate(point);
    		            selectedMapPoint.coord.setXPos(newCoord.getXPos());
    		            selectedMapPoint.coord.setZPos(newCoord.getZPos());
    		        }
    		        catch (Exception e)
    		        {
    		        }
    
    		        refresh();
    	    	}
		    }
		}
		else
		{
			super.mouseDraggedCallback(mouseEvent);
		}
	}

	public void leftClickCallback(MouseEvent mouseEvent)
	{
		if (mission.isFinalized())
		{
            super.leftClickCallback(mouseEvent);
		}
		else
		{
			int selectedMapPointIndex = determineSelectedWaypointIndex(mouseEvent.getX(), mouseEvent.getY());
			briefingFlightParameters.setSelectedMapPointIndex(selectedMapPointIndex);    			
    		if (selectedMapPointIndex == NO_MAP_POINT_SELECTED)
    		{
    			super.leftClickCallback(mouseEvent);
    		}
		}
	}

	public void rightClickCallback(MouseEvent e)
	{
		if (!mission.isFinalized())
		{
    		int selectedMapPointIndex = determineSelectedWaypointIndex(e.getX(), e.getY());
			briefingFlightParameters.setActionMapPointIndex(selectedMapPointIndex);    			
    		if (selectedMapPointIndex >= 0)
    		{
    			WaypointActionPopup menu = new WaypointActionPopup(this);
    			menu.show(e.getComponent(), e.getX(), e.getY());
    		}
		}
		else
		{
    		try
            {
                Point point = new Point();
                point.x = e.getX();
                point.y = e.getY();
                Coordinate coordinate = pointToCoordinate(point);
                MapPointInfoPopup menu = new MapPointInfoPopup(this, coordinate);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
            catch (Exception exp)
            {
                PWCGLogger.logException(exp);
            }
		}

	}	

	public void leftClickReleasedCallback(MouseEvent mouseEvent) throws PWCGException 
	{		
		super.leftClickReleasedCallback(mouseEvent);

		if (mission.isFinalized())
		{
			return;			
		}

		if (briefingFlightParameters.getSelectedMapPointIndex() != NO_MAP_POINT_SELECTED)
	    {
	        Point point = new Point();
	        point.x = mouseEvent.getX();
	        point.y = mouseEvent.getY();
        	Coordinate newCoord = this.pointToCoordinate(point);
        	briefingFlightParameters.updatePosition(newCoord);

			parent.waypointChangedNotification();

	        refresh();
	    }


		briefingFlightParameters.setSelectedMapPointIndex(NO_MAP_POINT_SELECTED);
	}

	public void rightClickReleasedCallback(MouseEvent e) 
	{
		if (mission.isFinalized())
		{
			return;			
		}

	}

	private int determineSelectedWaypointIndex(int x, int y)
	{
		int selectedMapPointIndex = NO_MAP_POINT_SELECTED;
		int lastValidIndex = NO_MAP_POINT_SELECTED;
		for (EditorWaypointGroup editorWaypointGroup :  briefingFlightParameters.getWaypointEditorGroups())
		{
			BriefingMapPoint mapPoint = editorWaypointGroup.getBriefingMapPoint();			
			++lastValidIndex;
			
			Point point = super.coordinateToPoint(mapPoint.coord);

			if ((Math.abs(point.x - x) < 10) && 
				(Math.abs(point.y - y) < 10))
			{
				if(mapPoint.editable)
				{
					selectedMapPointIndex = lastValidIndex;
				}
				break;
			}
		}
		
		return selectedMapPointIndex;
	}

	public Point upperLeft()
	{
		Point upperLeft = new Point();
		
		upperLeft.x = 10000000;
		upperLeft.y = 10000000;
		
		for (EditorWaypointGroup editorWaypointGroup: briefingFlightParameters.getWaypointEditorGroups())
		{
			BriefingMapPoint mapPoint= editorWaypointGroup.getBriefingMapPoint();
			Point point = super.coordinateToPoint(mapPoint.coord);
			
			if (point.x < upperLeft.x)
			{
				upperLeft.x = point.x;
			}
			if (point.y < upperLeft.y)
			{
				upperLeft.y = point.y;
			}
		}
		
		return upperLeft;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		try
		{
			String action = arg0.getActionCommand();
			if (action.contains("Add"))
			{
				if (briefingFlightParameters.getActionMapPointIndex() >= 0)
				{
					//briefParametersContext.addWayPoint(briefParametersContext.getActionMapPointIndex());
					//parent.waypointChangedNotification();
				}
			}
			else if (action.contains("Remove"))
			{
				if (briefingFlightParameters.getActionMapPointIndex() >= 0)
				{
					briefingFlightParameters.removeWayPoint(briefingFlightParameters.getActionMapPointIndex());
					parent.waypointChangedNotification();
				}
			}
			else if (action.contains("Cancel"))
			{
			}
		}
		catch (Exception e)
		{
			PWCGLogger.logException(e);
		}
		
	}
	
	private class FlightMap
	{
        String flightType;
        String planeType;
        int squadronId;
        List<BriefingMapPoint> mapPoints = new ArrayList<BriefingMapPoint>();
	}

    public void setMissionBorders(CoordinateBox missionBorders)
    {
        this.missionBorders = missionBorders;
    }
	
	
}
