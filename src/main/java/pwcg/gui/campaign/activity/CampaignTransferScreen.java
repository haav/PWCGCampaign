package pwcg.gui.campaign.activity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pwcg.aar.AARCoordinator;
import pwcg.aar.ui.events.model.TransferEvent;
import pwcg.campaign.ArmedService;
import pwcg.campaign.Campaign;
import pwcg.campaign.CampaignAces;
import pwcg.campaign.TransferHandler;
import pwcg.campaign.api.Side;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.factory.ArmedServiceFactory;
import pwcg.campaign.plane.PwcgRoleCategory;
import pwcg.campaign.squadmember.Ace;
import pwcg.campaign.squadmember.SquadronMember;
import pwcg.campaign.squadron.Squadron;
import pwcg.campaign.squadron.SquadronManager;
import pwcg.campaign.squadron.SquadronRoleFinder;
import pwcg.core.config.InternationalizationManager;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.PWCGLogger;
import pwcg.gui.CampaignGuiContextManager;
import pwcg.gui.IRefreshableParentUI;
import pwcg.gui.ScreenIdentifier;
import pwcg.gui.UiImageResolver;
import pwcg.gui.colors.ColorMap;
import pwcg.gui.dialogs.ErrorDialog;
import pwcg.gui.dialogs.PWCGMonitorFonts;
import pwcg.gui.rofmap.event.AARReportMainPanel;
import pwcg.gui.rofmap.event.AARReportMainPanel.EventPanelReason;
import pwcg.gui.sound.SoundManager;
import pwcg.gui.utils.ImageResizingPanel;
import pwcg.gui.utils.ImageToDisplaySizer;
import pwcg.gui.utils.PWCGButtonFactory;
import pwcg.gui.utils.PWCGLabelFactory;
import pwcg.gui.utils.PwcgBorderFactory;
import pwcg.gui.utils.SpacerPanelFactory;

public class CampaignTransferScreen extends ImageResizingPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private SquadronMember squadronMemberToTransfer = null; 
    private IRefreshableParentUI parentScreen = null;
    private Campaign campaign = null;   
    private boolean passTime = false;   
    
	private JComboBox<String> cbService;
	private JComboBox<String> cbSquadron;
    private JComboBox<String> cbRole;
    private JTextArea tSquadronInfo;
	private JButton acceptButton = null;
    private ArmedService service = null;

	public CampaignTransferScreen  (Campaign campaign, SquadronMember squadronMemberToTransfer, IRefreshableParentUI parentScreen, boolean passTime)
	{
        super();
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);

		this.parentScreen = parentScreen;
        this.squadronMemberToTransfer = squadronMemberToTransfer;
        this.campaign = campaign;
        this.passTime = passTime;
	}

	public void makePanels() throws PWCGException  
	{
        String imagePath = UiImageResolver.getImage(ScreenIdentifier.CampaignTransferScreen);
        this.setThemedImageFromName(campaign, imagePath);

        service = this.squadronMemberToTransfer.determineService(campaign.getDate());

        GridBagConstraints constraints = initializeGridbagConstraints();

        constraints.weightx = 0.1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(makeTransferNavPanel(), constraints);

        constraints.weightx = 0.1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(makeTransferCenterPanel(), constraints);
        
        constraints.weightx = 0.5;
        constraints.gridx = 2;
        constraints.gridy = 0;
        this.add(SpacerPanelFactory.makeDocumentSpacerPanel(1400), constraints);
	}

	private JPanel makeTransferNavPanel() throws PWCGException  
	{
	    JPanel transferrPanel  = new JPanel(new BorderLayout());
		transferrPanel.setOpaque(false);
		
		JPanel transferButtonPanel = new JPanel(new GridLayout(0,1));
		transferButtonPanel.setOpaque(false);
		
        acceptButton = PWCGButtonFactory.makeTranslucentMenuButton("Accept Transfer", "Accept Transfer", "Transfer to a new unit", this);
        transferButtonPanel.add(acceptButton);
        
        transferButtonPanel.add(PWCGLabelFactory.makeDummyLabel());

        JButton rejectButton = PWCGButtonFactory.makeTranslucentMenuButton("Reject Transfer", "Reject Transfer", "Do not transfer", this);
        transferButtonPanel.add(rejectButton);

		transferrPanel.add(transferButtonPanel, BorderLayout.NORTH);
		
		return transferrPanel;
	}

    private JPanel makeTransferCenterPanel() throws PWCGException  
    {
        JPanel transferCenterPanel = new JPanel();
        transferCenterPanel.setOpaque(false);
        transferCenterPanel.setLayout(new BorderLayout());
        transferCenterPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,100));

        JPanel transferRequest = makeTransferDocumentPanel();
        transferCenterPanel.add(transferRequest, BorderLayout.CENTER);
        
        ImageToDisplaySizer.setDocumentSize(transferCenterPanel);

        return transferCenterPanel;
    }

	private JPanel makeTransferDocumentPanel() throws PWCGException  
	{
        String imagePath = UiImageResolver.getImage(ScreenIdentifier.Document);
        ImageResizingPanel transferCenterPanel = new ImageResizingPanel(campaign, imagePath);
        transferCenterPanel.setLayout(new BorderLayout());
        transferCenterPanel.setBorder(PwcgBorderFactory.createStandardDocumentBorder());

        Font font = PWCGMonitorFonts.getPrimaryFont();

        GridBagLayout transferLayout = new GridBagLayout();
        
        JPanel transferPanel = new JPanel(transferLayout);
        transferPanel.setOpaque(false);

        List<Component> components = new ArrayList<Component>();
        int rowNum = 0;

        JLabel lName = PWCGLabelFactory.makeTransparentLabel(squadronMemberToTransfer.getNameAndRank(), ColorMap.PAPER_FOREGROUND, font, SwingConstants.LEFT);
        
        components.clear();
        components.add(lName);
        rowNum = addRow(transferPanel, components, rowNum);

        components.clear();
        components.add(PWCGLabelFactory.makeDummyLabel());
        rowNum = addRow(transferPanel, components, rowNum);

        Color buttonBG = ColorMap.PAPER_BACKGROUND;
        rowNum = makeServiceChooser(buttonBG, font, transferPanel, components, rowNum);
        
        components.clear();
        components.add(PWCGLabelFactory.makeDummyLabel());
        rowNum = addRow(transferPanel, components, rowNum);

        rowNum = makeRoleChooser(buttonBG, font, transferPanel, components, rowNum);
        
        transferCenterPanel.add(transferPanel, BorderLayout.NORTH);
        
           
        JPanel squadronPanel = createSquadronInfoPanel ();
        transferCenterPanel.add(squadronPanel, BorderLayout.SOUTH);

        evaluate();
        
        initializeValues();
		
		return transferCenterPanel;
	}

    private void initializeValues() throws PWCGException 
    {
        ArmedService playerService = squadronMemberToTransfer.determineService(campaign.getDate());
        cbService.setSelectedItem(playerService.getName());

        Squadron playerSquadron = squadronMemberToTransfer.determineSquadron();        
        PwcgRoleCategory roleCategory = playerSquadron.determineSquadronPrimaryRoleCategory(campaign.getDate());
        cbRole.setSelectedItem(roleCategory.getRoleCategoryDescription());
    }

    private GridBagConstraints initializeGridbagConstraints()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 3;
        constraints.ipady = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        Insets margins = new Insets(0, 50, 50, 0);
        constraints.insets = margins;
        return constraints;
    }

    private int makeRoleChooser(Color buttonBG, Font font, JPanel transferPanel, List<Component> components, int rowNum) throws PWCGException
    {
        String roleText = InternationalizationManager.getTranslation("Role");
        roleText += ": ";
        JLabel lRole = PWCGLabelFactory.makeTransparentLabel(roleText, ColorMap.PAPER_FOREGROUND, font, SwingConstants.LEFT);

        cbRole = new JComboBox<String>();
        for  (PwcgRoleCategory roleCategoryForService : SquadronRoleFinder.getRoleCategoriesForService(service, campaign.getDate()))
        {
            cbRole.addItem(roleCategoryForService.getRoleCategoryDescription());
        }
        
        
        cbRole.setOpaque(false);
        cbRole.setBackground(buttonBG);
        cbRole.setSelectedIndex(0);
        cbRole.addActionListener(this);
        cbRole.setActionCommand("Role Changed");
        components.clear();
        components.add(lRole);
        components.add(cbRole);
        rowNum = addRow(transferPanel, components, rowNum);

        components.clear();
        components.add(PWCGLabelFactory.makeDummyLabel());
        rowNum = addRow(transferPanel, components, rowNum);

        String transferText = InternationalizationManager.getTranslation("Requests a transfer to");
        transferText += ": ";
        JLabel lTransfer = PWCGLabelFactory.makeTransparentLabel(transferText, ColorMap.PAPER_FOREGROUND, font,SwingConstants.LEFT);

        cbSquadron = new JComboBox<String>();
        cbSquadron.setBackground(buttonBG);
        cbSquadron.setActionCommand("SquadronChanged");
        cbSquadron.addActionListener(this);
        components.clear();
        components.add(lTransfer);
        components.add(cbSquadron);
        rowNum = addRow(transferPanel, components, rowNum);
        return rowNum;
    }

    private int makeServiceChooser(Color buttonBG, Font font, JPanel transferPanel, List<Component> components, int rowNum) throws PWCGException
                    
    {
        String serviceText = InternationalizationManager.getTranslation("Service");
        serviceText += ": ";
        JLabel lService = PWCGLabelFactory.makeTransparentLabel(serviceText, ColorMap.PAPER_FOREGROUND, font, SwingConstants.LEFT);        

        cbService = new JComboBox<String>();
        
        List<ArmedService> services = null;
        if (squadronMemberToTransfer.determineCountry(campaign.getDate()).getSideNoNeutral() == Side.ALLIED)
        {
            services = ArmedServiceFactory.createServiceManager().getAlliedServices(campaign.getDate());
        }
        else
        {
            services = ArmedServiceFactory.createServiceManager().getAxisServices(campaign.getDate());
        }
        
        for (ArmedService service : services)
        {
            addServiceName(service);
        }

        cbService.setOpaque(false);
        cbService.setBackground(buttonBG);
        cbService.setSelectedIndex(0);
        cbService.addActionListener(this);
        cbService.setActionCommand("Service Changed");
        components.clear();
        components.add(lService);
        components.add(cbService);
        rowNum =  addRow(transferPanel, components, rowNum);
        return rowNum;
    }

	private void addServiceName (ArmedService service)
	{
        cbService.addItem(service.getName());
	}

    private JPanel createSquadronInfoPanel () throws PWCGException 
    {
        Font font = PWCGMonitorFonts.getTypewriterFont();
        Color bgColor = ColorMap.PAPER_BACKGROUND;
        Color fgColor = ColorMap.PAPER_FOREGROUND;
        
        JPanel squadronPanel = new JPanel(new GridLayout(0,3));
        squadronPanel.setOpaque(false);

        squadronPanel.add(PWCGLabelFactory.makeDummyLabel());
        
        // Squadron info
        tSquadronInfo = new JTextArea();
        tSquadronInfo.setBackground(bgColor);
        tSquadronInfo.setForeground(fgColor);
        tSquadronInfo.setFont(font);
        tSquadronInfo.setEditable(false);
        tSquadronInfo.setLineWrap(true);
        tSquadronInfo.setWrapStyleWord(true);
        tSquadronInfo.setText("");
        tSquadronInfo.setOpaque(false);
        squadronPanel.add(tSquadronInfo);

        squadronPanel.add(PWCGLabelFactory.makeDummyLabel());
        
        return squadronPanel;
    }

	private int addRow(JPanel panel, List<Component> components, int rowNum)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipadx = 3;
		constraints.ipady = 3;
		
		int numDummyCols = 3;
		int columnNum = 0;
		
		for (int i = 0; i < numDummyCols; ++i)
		{
		    constraints.weightx = 0.15;
			constraints.gridx = columnNum;
			constraints.gridy = rowNum;
			panel.add(PWCGLabelFactory.makeDummyLabel(), constraints);
			
			++columnNum;
		}
		
		for (Component component : components)
		{
		    constraints.weightx = 0.2;
			constraints.gridx = columnNum;
			constraints.gridy = rowNum;
			panel.add(component, constraints);
			
			++columnNum;
		}
		
		
		for (int i = 0; i < numDummyCols; ++i)
		{
		    constraints.weightx = 0.15;
			constraints.gridx = columnNum;
			constraints.gridy = rowNum;
			panel.add(PWCGLabelFactory.makeDummyLabel(), constraints);
			
			++columnNum;
		}
		
		++rowNum;
		
		return rowNum;
	}

	private void evaluate() throws PWCGException 
	{		
		cbSquadron.removeAllItems();
		SquadronManager squadManager = PWCGContext.getInstance().getSquadronManager();
				
		List<Squadron> squadronList = squadManager.getPlayerFlyableSquadronsByService(service, campaign.getDate());
		
        String roleCategoryDesc = (String)cbRole.getSelectedItem();
		for (int i = 0; i < squadronList.size(); ++i)
		{
			Squadron squad = squadronList.get(i);
			Date campaignDate = campaign.getDate();
			if(squad.getSquadronId() != squadronMemberToTransfer.getSquadronId())
			{
			    if (squad.determineSquadronPrimaryRoleCategory(campaignDate).getRoleCategoryDescription().equals(roleCategoryDesc))
			    {
			        String display = squad.determineDisplayName(campaign.getDate());
			        CampaignAces aces =  PWCGContext.getInstance().getAceManager().loadFromHistoricalAces(campaignDate);
			        List<Ace> squadronAces =  PWCGContext.getInstance().getAceManager().getActiveAcesForSquadron(aces, campaignDate, squad.getSquadronId());
			        if (!squadronMemberToTransfer.isCommander(campaignDate) || !squad.isCommandedByAce(squadronAces, campaignDate))
			        {
			            cbSquadron.addItem(display);
			        }
			    }
			}
		}
		
        String squadronName = (String)cbSquadron.getSelectedItem();
		setSquadronInfo(squadronName);
		
		if (cbSquadron.getItemCount() == 0)
		{
			acceptButton.setEnabled(false);
		}
		else
		{
			acceptButton.setEnabled(true);
		}
	}

    private void setSquadronInfo(String squadronName) throws PWCGException 
    {
        String squadronInfo = "";
        if (squadronName != null)
        {
            Squadron squad = PWCGContext.getInstance().getSquadronManager().getSquadronByName(squadronName, campaign.getDate());
            squadronInfo = squad.determineSquadronInfo(campaign.getDate());
        }
        tSquadronInfo.setText(squadronInfo);
    }

	public String getSelectedSquad()
	{
		String squadName = (String)cbSquadron.getSelectedItem();
		return squadName;
	}

	public void actionPerformed(ActionEvent ae)
	{
        String action = ae.getActionCommand();

        try
		{
            if (action.equalsIgnoreCase("Service Changed"))
            {
                String serviceName = (String)cbService.getSelectedItem();
                if (serviceName != null)
                {
                    service = ArmedServiceFactory.createServiceManager().getArmedServiceByName(serviceName, campaign.getDate());
                }
                evaluate();
                return;
            }
            else if (action.equalsIgnoreCase("Role Changed"))
            {
                evaluate();
                return;
            }
            else if (action.equalsIgnoreCase("SquadronChanged"))
            {
                String squadronName = (String)cbSquadron.getSelectedItem();
                setSquadronInfo(squadronName);
            }
            else if (action.equalsIgnoreCase("Accept Transfer"))
            {
                SoundManager.getInstance().playSound("Stapling.WAV");
                if (passTime)
                {
                    acceptPlayerTransferWithTimePassed();
                }
                else
                {
                    acceptPlayerTransferWithNoTimePassed();
                }
            }
            else if (action.equalsIgnoreCase("Reject Transfer"))
            {
                SoundManager.getInstance().playSound("Stapling.WAV");
                CampaignGuiContextManager.getInstance().popFromContextStack();
            }
		}
		catch (Exception e)
		{
			PWCGLogger.logException(e);
			ErrorDialog.internalError(e.getMessage());
		}
	}

    private void acceptPlayerTransferWithTimePassed() throws PWCGException 
    {        
        SoundManager.getInstance().playSound("Stapling.WAV");
        TransferEvent transferEvent = transferPlayer();        

        campaign.setCurrentMission(null);
        AARCoordinator.getInstance().submitTransfer(campaign, transferEvent.getLeaveTime());
        AARReportMainPanel eventDisplay = new AARReportMainPanel(campaign, parentScreen, EventPanelReason.EVENT_PANEL_REASON_TRANSFER, transferEvent);
        eventDisplay.makePanels();
        CampaignGuiContextManager.getInstance().pushToContextStack(eventDisplay);
    }

    private void acceptPlayerTransferWithNoTimePassed() throws PWCGException
    {
        transferPlayer();
        campaign.write();
        parentScreen.refreshInformation();
        CampaignGuiContextManager.getInstance().popFromContextStack();
    }

    private TransferEvent transferPlayer() throws PWCGException
    {
        String newSquadName = getSelectedSquad();
        TransferHandler transferHandler = new TransferHandler(campaign, squadronMemberToTransfer);
        Squadron newSquadron = PWCGContext.getInstance().getSquadronManager().getSquadronByName(newSquadName, campaign.getDate());
        TransferEvent transferEvent = transferHandler.transferPlayer(squadronMemberToTransfer.determineSquadron(), newSquadron);
        return transferEvent;
    }
}
