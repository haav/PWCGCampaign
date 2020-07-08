package pwcg.gui.rofmap.brief;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

import pwcg.core.exception.PWCGException;
import pwcg.gui.dialogs.PWCGMonitorFonts;
import pwcg.gui.rofmap.brief.model.BriefingMapPoint;

public class WaypointEditor
{
    private long associatedWaypointID;
    private JTextField descTextField;
    private JTextField altitudeTextField;
    private JTextField distanceTextField;
    private JTextField headingTextField;
    private String actionCommandKey = "";

    public WaypointEditor(long associatedWaypointID)
    {
        this.associatedWaypointID = associatedWaypointID;
    }
    
    public void initializeWPEdit(BriefingMapPoint previousMapPoint, BriefingMapPoint thisMapPoint) throws PWCGException
    {
        descTextField = makeTextField();
        descTextField.setText(thisMapPoint.getDesc());

        altitudeTextField = makeTextField();
        distanceTextField = makeTextField();
        headingTextField = makeTextField();

        descTextField.setEditable(false);
        distanceTextField.setEditable(false);
        headingTextField.setEditable(false);
        
        if (thisMapPoint.isEditable())
        {
            altitudeTextField.setEditable(true);
        }
        else
        {
            altitudeTextField.setEditable(false);
        }

        calculateWPParameters(previousMapPoint, thisMapPoint);
    }

    public JTextField getDescriptionField()
    {
        return descTextField;
    }

    public JTextField getAltitudeTextField()
    {
        return altitudeTextField;
    }

    public int getAltitudeValue()
    {
        return new Integer(altitudeTextField.getText());
    }

    public JTextField getDistanceTextField()
    {
        return distanceTextField;
    }

    public JTextField getHeadingtextField()
    {
        return headingTextField;
    }

    public void setEnabled(boolean enabled)
    {
        descTextField.setEditable(false);
        altitudeTextField.setEditable(enabled);
        distanceTextField.setEditable(false);
    }

    private void calculateWPParameters(BriefingMapPoint previousMapPoint, BriefingMapPoint briefingMapPoint) throws PWCGException
    {
        int distance = 0;
        int heading = 0;

        if (previousMapPoint != null)
        {
            distance = BriefingMapPointDistanceCalculator.calculateDistanceAsInteger(previousMapPoint.getPosition(), briefingMapPoint.getPosition());
            heading = BriefingMapPointDistanceCalculator.calculateHeading(previousMapPoint.getPosition(), briefingMapPoint.getPosition());
        }

        altitudeTextField.setText("" + Double.valueOf(briefingMapPoint.getAltitude()).intValue());
        distanceTextField.setText(Integer.valueOf(distance / 1000).toString());
        headingTextField.setText(Integer.valueOf(heading).toString());
    }
    
    public void refreshTextFields()
    {
        refreshTextField(altitudeTextField);
        refreshTextField(distanceTextField);
        refreshTextField(headingTextField);
    }
    
    public void setAltitude(int altitude)
    {
        altitudeTextField.setText("" + altitude);
    }
    
    private void refreshTextField(JTextField textField)
    {
        if (textField.getGraphics() != null)
        {
            textField.update(textField.getGraphics());
        }
    }

    private JTextField makeTextField() throws PWCGException
    {
        Font font = PWCGMonitorFonts.getTypewriterFont();

        JTextField field = new JTextField();
        field.setOpaque(false);
        field.setFont(font);
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setForeground(Color.BLACK);

        return field;
    }

    public String getActionCommandKey()
    {
        return actionCommandKey;
    }

    public long getAssociatedWaypointID()
    {
        return associatedWaypointID;
    }
}
