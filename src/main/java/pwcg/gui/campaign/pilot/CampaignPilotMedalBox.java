package pwcg.gui.campaign.pilot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import pwcg.campaign.Campaign;
import pwcg.campaign.api.ICountry;
import pwcg.campaign.api.Side;
import pwcg.campaign.factory.CountryFactory;
import pwcg.campaign.factory.MedalManagerFactory;
import pwcg.campaign.medals.IMedalManager;
import pwcg.campaign.medals.Medal;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.PWCGLogger;
import pwcg.gui.ScreenIdentifier;
import pwcg.gui.UiImageResolver;
import pwcg.gui.colors.ColorMap;
import pwcg.gui.dialogs.ErrorDialog;
import pwcg.gui.dialogs.PWCGMonitorSupport;
import pwcg.gui.dialogs.PWCGMonitorSupport.MonitorSize;
import pwcg.gui.image.ImageIconCache;
import pwcg.gui.sound.SoundManager;
import pwcg.gui.utils.ContextSpecificImages;
import pwcg.gui.utils.ImageResizingPanel;
import pwcg.gui.utils.PWCGLabelFactory;
import pwcg.gui.utils.PwcgBorderFactory;

public class CampaignPilotMedalBox extends ImageResizingPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private CampaignMedalScreen campaignMedalScreen;
    private int medalsPerRow = 5;
    private Campaign campaign;
	private SquadronMember pilot;
	private Map<String, Medal> medals = new HashMap<>();

	public CampaignPilotMedalBox(CampaignMedalScreen campaignMedalScreen, Campaign campaign, SquadronMember pilot)
	{
        super();
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        this.campaignMedalScreen = campaignMedalScreen;
        this.campaign = campaign;
        this.pilot = pilot;
        this.medalsPerRow = calcMedalsPerRow();
	}

	public void makePanels() throws PWCGException  
	{
        SoundManager.getInstance().playSound("MedalCaseOpen.WAV");

        String imagePath = UiImageResolver.getImage(ScreenIdentifier.OpenMedalBox);
        this.setThemedImageFromName(campaign, imagePath);        
        this.setBorder(PwcgBorderFactory.createMedalBoxBorder());

	    this.add(BorderLayout.CENTER, makeCenterPanel());
	}

	private JPanel makeCenterPanel() throws PWCGException 
	{
        JPanel campaignPilotMedalPanel = new JPanel(new BorderLayout());
        campaignPilotMedalPanel.setOpaque(false);

		JPanel pilotMedalPanel = makePilotMedalPanel();
		campaignPilotMedalPanel.add(pilotMedalPanel, BorderLayout.NORTH);
		
		return campaignPilotMedalPanel;
	}	
    
    private int calcMedalsPerRow()
    {
        MonitorSize monitorSize = PWCGMonitorSupport.getFrameWidth();
        if (monitorSize == MonitorSize.FRAME_VERY_SMALL)
        {
            return 1;
        }
        else if (monitorSize == MonitorSize.FRAME_SMALL)
        {
            return 2;
        }
        if (monitorSize == MonitorSize.FRAME_MEDIUM)
        {
            return 3;
        }
        else
        {
            return 4;
        }
    }

	private JPanel makePilotMedalPanel() throws PWCGException 
	{
        JPanel medalPanel = new JPanel(new GridLayout(0, medalsPerRow));
		medalPanel.setOpaque(false);
		
		Color bg = ColorMap.PAPER_BACKGROUND;

        ICountry country = CountryFactory.makeCountryByCountry(pilot.getCountry());
        IMedalManager medalManager = MedalManagerFactory.createMedalManager(country, campaign);
        List<Medal> highestOrderMedals = medalManager.getMedalsWithHighestOrderOnly(pilot.getMedals());
        for (Medal medal : highestOrderMedals)
		{
			if (medal != null)
			{
				String medalSide = "Axis";
				if (country.getSide() == Side.ALLIED)
				{
					medalSide = "Allied";
				}
					
		        String medalPath = ContextSpecificImages.imagesMedals() + medalSide + "\\" + medal.getMedalImage();
				ImageIcon medalIcon = null;  
				try 
				{
					medalIcon = ImageIconCache.getInstance().getImageIcon(medalPath);
				}
				catch (Exception ex) 
				{
		            PWCGLogger.logException(ex);
				}
				
				JButton medalButton = makeMedalButton(bg, medal, medalIcon);

				medals.put(medal.getMedalName(), medal);

				medalPanel.add(medalButton);
			}
		}

		int remainder = medalsPerRow - (pilot.getMedals().size() % medalsPerRow);
		if (remainder != medalsPerRow)
		{
			for (int i = 0; i < remainder; ++i)
			{
				medalPanel.add(PWCGLabelFactory.makeDummyLabel());
			}
		}
		
		return medalPanel;
	}

    private JButton makeMedalButton(Color bg, Medal medal, ImageIcon medalIcon)
    {
        JButton medalButton = new JButton(medalIcon);
        medalButton.setPressedIcon(medalIcon);
        medalButton.setBackground(bg);
        medalButton.setOpaque(false);
        medalButton.setBorderPainted(false);
        medalButton.setFocusPainted(false);
        medalButton.setActionCommand(medal.getMedalName());
        medalButton.addActionListener(this);
        return medalButton;
    }
	
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String medalName = ae.getActionCommand();
            Medal medal = medals.get(medalName);
            campaignMedalScreen.setMedalText(medal);
        }
        catch (Exception e)
        {
            PWCGLogger.logException(e);
            ErrorDialog.internalError(e.getMessage());
        }
    }
}
