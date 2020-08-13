package pwcg.product.bos.map.east1945;

import java.util.Calendar;
import java.util.Date;

import pwcg.campaign.context.PWCGMap.FrontMapIdentifier;
import pwcg.core.utils.RandomNumberGenerator;
import pwcg.mission.options.MapSeasonalParameters.Season;
import pwcg.product.bos.map.BoSMapWeatherBase;


public class East1945MapWeather extends BoSMapWeatherBase
{
	public East1945MapWeather()
	{
	    super();
	}	
	
    @Override
    public Season getSeason(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;

        Season season = Season.SUMMER;
        if (month == 12 || month == 1 || month == 2)
        {
            season = Season.WINTER;
        }
        else if (month == 3 || month == 4 | month == 5)
        {
            season = Season.SPRING;
        }
        else if (month == 6 || month == 7 || month == 8)
        {
            season = Season.SUMMER;
        }
        else if (month == 9 || month == 10 || month == 11)
        {
            season = Season.AUTUMN;
        }

        return season;
    }
    
    
    @Override
    protected void setTemperature(Date date, FrontMapIdentifier frontMap)
    {
        temperature = 25;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;

        if (month == 1)
        {
            temperature = -5 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 2)
        {
            temperature = -3 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 3)
        {
            temperature = 0 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 4)
        {
            temperature = 5 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 5)
        {
            temperature = 10 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 6)
        {
            temperature = 18 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 7)
        {
            temperature = 22 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 8)
        {
            temperature = 18 +  RandomNumberGenerator.getRandom(15);
        }
        else if (month == 9)
        {
            temperature = 10 +  RandomNumberGenerator.getRandom(10);
        }
        else if (month == 10)
        {
            temperature = 6 +  RandomNumberGenerator.getRandom(10);
        }       
        else if (month == 11)
        {
            temperature = 0 +  RandomNumberGenerator.getRandom(10);
        }       
        else if (month == 12)
        {
            temperature = -5 +  RandomNumberGenerator.getRandom(10);
        }
    }

}
