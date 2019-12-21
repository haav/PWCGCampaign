package pwcg.core.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.core.exception.PWCGException;

public class DateUtils 
{
	static public String monthAsString(int month)
	{
		String monthAsString = "January";
		if (month == 2)
		{
			monthAsString = "February";
		}
		else if (month == 3)
		{
			monthAsString = "March";
		}
		else if (month == 4)
		{
			monthAsString = "April";
		}
		else if (month == 5)
		{
			monthAsString = "May";
		}
		else if (month == 6)
		{
			monthAsString = "June";
		}
		else if (month == 7)
		{
			monthAsString = "July";
		}
		else if (month == 8)
		{
			monthAsString = "August";
		}
		else if (month == 9)
		{
			monthAsString = "September";
		}
		else if (month == 10)
		{
			monthAsString = "October";
		}
		else if (month == 11)
		{
			monthAsString = "November";
		}
		else if (month == 12)
		{
			monthAsString = "December";
		}
		
		return monthAsString;
	}

    static public String getDateString(Date date) 
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = df.format(date);

        return dateString;
    }

    static public String getDateStringPretty(Date date) 
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        String year = "" + calendar.get(Calendar.YEAR);
        String month = "" + (calendar.get(Calendar.MONTH) + 1);
        String day ="" +  calendar.get(Calendar.DAY_OF_MONTH);
        
        String dateString = DateUtils.getDateStringPretty(year, month, day);

        return dateString;
    }

    static public String getDateStringPretty(String year, String monthNum, String day)
    {
        String month = DateUtils.monthAsString(Integer.valueOf(monthNum));
        
        String dateString = day + " " + month + " " + year;

        return dateString;
    }

    static public String getDateStringDashDelimitedYYYYMMDD(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        Formatter formatter = new Formatter();
        
        String dateStr = formatter.format("%04d-%02d-%02d",
                calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1 ),
                calendar.get(Calendar.DAY_OF_MONTH)).toString();
        
        formatter.close();

        return dateStr;
    }

    static public String getDateAsMissionFileFormat(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        Formatter formatter = new Formatter();
        
        String dateStr = formatter.format("%d.%d.%d",
                calendar.get(Calendar.DAY_OF_MONTH),
                (calendar.get(Calendar.MONTH) + 1 ),
                calendar.get(Calendar.YEAR)).toString();
        
        formatter.close();

        return dateStr;
    }

    static public String getDateStringYYYYMMDD(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateString = df.format(date);

        return dateString;
    }

    public static String getDateStringYYYYMMDDHHMMSS(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHMMSS");
        String dateString = df.format(date);

        return dateString;
    }

    public static Date getDateWithValidityCheck(String dateString) throws PWCGException
    {
        Date date = getDateNoCheck(dateString);        
        date = checkDateValidity(date);
        
        return date;
    }

    public static Date getDateByTime(Long time) throws PWCGException
    {
        Date date = new Date(time);
        date = checkDateValidity(date);
         
        return date;
    }

    private static Date checkDateValidity(Date date) throws PWCGException
    {
        if (date.after(getEndOfWar()))
        {
            return getEndOfWar();
        }
        
        if (date.before(getBeginningOfGame()))
        {
            return getBeginningOfGame();
        }
        
        return date;
    }

    public static Date getDateNoCheck(String dateString) throws PWCGException
    {
        try
        {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date = df.parse(dateString);

            return date;
        }
        catch (ParseException e)
        {
            throw new PWCGException (e.getMessage());
        }
    }

    static public Date getDateYYYYMMDD(String dateString) throws PWCGException
    {
        try
        {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date date = df.parse(dateString);
    
            return date;
        }
        catch (ParseException e)
        {
            throw new PWCGException (e.getMessage());
        }
    }

    public static Date getEndOfWar() throws PWCGException
    {
        if (PWCGContext.getProduct() == PWCGProduct.FC)
        {
            return getDateNoCheck("11/11/1918");            
        }
        else
        {              
            return getDateNoCheck("03/05/1945");
        }
    }

    public static Date getBeginningOfWar() throws PWCGException
    {
        if (PWCGContext.getProduct() == PWCGProduct.FC)
        {
            return getDateNoCheck("01/08/1914");            
        }
        else
        {              
            return getDateNoCheck("03/09/1939");
        }
    }

    public static Date getBeginningOfGame() throws PWCGException
    {
        if (PWCGContext.getProduct() == PWCGProduct.FC)
        {
            return getDateNoCheck("01/08/1917");            
        }
        else
        {              
            return getDateNoCheck("01/10/1941");
        }
    }

    public static Date getEndOfWWIRussia() throws PWCGException
    {
        return getDateNoCheck("01/11/1917");
    }

    public static Date getEndOfWWIIRussia() throws PWCGException
    {
        return getDateNoCheck("01/02/1944");
    }

    public static Date getStartofWWIIItaly() throws PWCGException
    {
        return getDateNoCheck("01/01/1942");
    }

    public static Date getStartofWWIIUSA() throws PWCGException
    {
        return getDateNoCheck("01/08/1944");
    }

    public static Date getStartofWWIIBritain() throws PWCGException
    {
        return getDateNoCheck("01/08/1944");
    }

    public static Date getRAFDate() throws PWCGException
    {
        return DateUtils.getDateYYYYMMDD("19180401");
    }

	public static List<Date> getFrontDates() throws PWCGException
	{
	    try
	    {
    		List<Date> transitionDates = new ArrayList<Date>();
    		String inputDir = PWCGContext.getInstance().getDirectoryManager().getPwcgInputDir();		
    		
    		DirectoryReader directoryReader = new DirectoryReader();
            directoryReader.sortilesInDir(inputDir);
            for (String frontDateDirName : directoryReader.getDirectories()) 
            {
                String frontDateDirs = PWCGContext.getInstance().getDirectoryManager().getPwcgInputDir() + frontDateDirName;
                File frontDateDir = new File(frontDateDirs);
                if (frontDateDir.isDirectory() && frontDateDir.getName().contains("19"))
                {
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");
                    
                    Date date = df.parse(frontDateDir.getName());
                    
                    transitionDates.add(date);
                }
            }

    		return transitionDates;
	    }
	    catch (ParseException e)
	    {
	        Logger.logException(e);
	        throw new PWCGException(e.getMessage());
	    }
	    
	}

    static public Date advanceTimeDays (Date currentDate, int days) throws PWCGException
    {
        Calendar calendar = dateToCalendar(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date newDate = calendar.getTime();

        return newDate;
    }

    static public Date removeTimeDays (Date currentDate, int days) throws PWCGException
    {
        Calendar calendar = dateToCalendar(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, (days * -1));
        Date newDate = calendar.getTime();
        
        return newDate;
    }

    static public int daysDifference (Date previousDate, Date newDate) throws PWCGException
    {
        Calendar beforeCalendar = dateToCalendar(previousDate);
        Calendar afterCalendar = dateToCalendar(newDate);

        long start = beforeCalendar.getTimeInMillis();
        long end = afterCalendar.getTimeInMillis();
        long wholeDays = TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));

        return Long.valueOf(wholeDays).intValue();
    }

    static public boolean isDateInRange (Date date, Date startRange, Date endRange) throws PWCGException
    {
        if (date.before(startRange))
        {
            return false;
        }
        if (date.after(endRange))
        {
            return false;
        }
        return true;
    }
    
    static Calendar dateToCalendar(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}
