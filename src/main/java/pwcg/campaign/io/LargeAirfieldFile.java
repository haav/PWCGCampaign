package pwcg.campaign.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pwcg.campaign.context.PWCGContextManager;
import pwcg.core.exception.PWCGException;
import pwcg.core.exception.PWCGIOException;
import pwcg.core.utils.Logger;

public class LargeAirfieldFile 
{

	public static List<String> readLargeFields (String mapName) throws PWCGException
    {
	    List<String> largeAirfields = new ArrayList<String>();

        try 
        {
            String iconFileLocation = PWCGContextManager.getInstance().getDirectoryManager().getPwcgInputDir() + mapName + "\\" + "AirfieldILarge.config";           
            largeAirfields = readLargeFieldFile(iconFileLocation);            
        } 
        catch (IOException e) 
        {
            Logger.logException(e);
            throw new PWCGException(e.getMessage());
        }
        
        return largeAirfields;
    }

    private static List<String> readLargeFieldFile(String fileLocation) throws FileNotFoundException, IOException,
                    PWCGIOException
    {
        List<String> largeAirfields = new ArrayList<String>();
        
        BufferedReader reader = null;
        
        try 
        {

            reader = new BufferedReader(new FileReader(fileLocation));
            String line;
            while ((line = reader.readLine()) != null) 
            {
                line = line.trim();
                if (!line.isEmpty())
                {
                    largeAirfields.add(line);
                }
            }
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return largeAirfields;
    }
 }
