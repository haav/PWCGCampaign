package pwcg.campaign.medals;

import java.util.Date;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pwcg.campaign.api.ICountry;
import pwcg.campaign.context.Country;
import pwcg.campaign.context.PWCGContextManager;
import pwcg.campaign.factory.ArmedServiceFactory;
import pwcg.campaign.factory.CountryFactory;
import pwcg.campaign.factory.MedalManagerFactory;
import pwcg.campaign.ww2.country.BoSServiceManager;
import pwcg.campaign.ww2.medals.ItalianMedalManager;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;
import pwcg.gui.dialogs.ImageCache;
import pwcg.gui.utils.ContextSpecificImages;

@RunWith(MockitoJUnitRunner.class)
public class BoSItalianMedalManagerTest extends MedalManagerTestBase
{
    
    @Before
    public void setup() throws PWCGException
    {
        PWCGContextManager.setRoF(false);
        super.setup();
        Mockito.when(country.isCountry(Country.ITALY)).thenReturn(true);
        ICountry country = CountryFactory.makeCountryByCountry(Country.ITALY);
        medalManager = MedalManagerFactory.createMedalManager(country, campaign);
        medals.clear();
    }

    @Test
    public void testItalianMedals () throws PWCGException
    {            	
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19420801"));
	    service = ArmedServiceFactory.createServiceManager().getArmedServiceById(BoSServiceManager.REGIA_AERONAUTICA, campaign.getDate());
        Mockito.when(player.determineService(Matchers.<Date>any())).thenReturn(service);

        awardMedal(ItalianMedalManager.PILOTS_BADGE, 0, 0);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_BRONZE, 1, 1);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_SILVER, 5, 1);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_GOLD, 13, 1);
		awardMedal(ItalianMedalManager.CROSS_WAR_MERIT, 18, 1);
		awardMedal(ItalianMedalManager.CROSS_MILITARY_VALOR, 25, 1);
    }

    @Test
    public void testMedalFail () throws PWCGException
    {            
        Mockito.when(campaign.getDate()).thenReturn(DateUtils.getDateYYYYMMDD("19420801"));
	    service = ArmedServiceFactory.createServiceManager().getArmedServiceById(BoSServiceManager.REGIA_AERONAUTICA, campaign.getDate());
        Mockito.when(player.determineService(Matchers.<Date>any())).thenReturn(service);

        awardMedal(ItalianMedalManager.PILOTS_BADGE, 0, 0);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_BRONZE, 1, 1);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_SILVER, 5, 1);
		awardMedal(ItalianMedalManager.MEDAL_MILITARY_VALOR_GOLD, 13, 1);
		awardMedal(ItalianMedalManager.CROSS_WAR_MERIT, 18, 1);

    	makeVictories(24);
        Medal medal = medalManager.award(campaign, player, service, 1);
        assert (medal == null);
    }

    @Test
    public void testImages () throws PWCGException
    {            
    	for (Medal medal : medalManager.getAllAwardsForService())
    	{
	        String medalPath = ContextSpecificImages.imagesMedals() + "Axis\\" + medal.getMedalImage();
	        ImageIcon medalIcon = ImageCache.getInstance().getImageIcon(medalPath);
	        assert (medalIcon != null);
    	}
    }
}
