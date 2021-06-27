package pwcg.mission.mcu;

import java.io.BufferedWriter;
import java.io.IOException;

import pwcg.core.exception.PWCGException;
import pwcg.core.utils.PWCGLogger;

public class McuDelete extends BaseFlightMcu
{
	public McuDelete()
	{
		super();
	}

	public void write(BufferedWriter writer) throws PWCGException
	{
		try
        {
            writer.write("MCU_Delete");
            writer.newLine();
            writer.write("{");
            writer.newLine();
            
            super.write(writer);
            
            writer.write("}");
            writer.newLine();
            writer.newLine();
            writer.newLine();
        }
        catch (IOException e)
        {
            PWCGLogger.logException(e);
            throw new PWCGException(e.getMessage());
        }
	}
	
	public String toString()
	{
		StringBuffer output = new StringBuffer("");
		output.append("MCU_Delete\n");
		output.append("{\n");
		
		output.append(super.toString());

		output.append("}\n");
		output.append("\n");
		output.append("\n");
		
		return output.toString();

	}
}
