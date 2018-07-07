package pwcg.campaign.io.json;

import pwcg.campaign.context.PWCGContextManager;
import pwcg.campaign.group.GroundStructureGroup;
import pwcg.core.exception.PWCGException;
import pwcg.core.exception.PWCGIOException;

public class GroundObjectIOJson 
{
	public static GroundStructureGroup readJson(String mapName) throws PWCGException, PWCGIOException
	{
		JsonObjectReader<GroundStructureGroup> jsonReader = new JsonObjectReader<>(GroundStructureGroup.class);
		GroundStructureGroup groundStructureGroup = jsonReader.readJsonFile(PWCGContextManager.getInstance().getDirectoryManager().getPwcgInputDir() + mapName + "\\", "GroundStructures.json");
		return groundStructureGroup;
	}
}
