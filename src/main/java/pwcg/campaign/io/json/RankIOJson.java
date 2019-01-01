package pwcg.campaign.io.json;

import pwcg.campaign.context.PWCGContextManager;
import pwcg.campaign.personnel.Ranks;
import pwcg.core.exception.PWCGException;
import pwcg.core.exception.PWCGIOException;

public class RankIOJson 
{
	public static Ranks readJson() throws PWCGException, PWCGIOException
	{
		JsonObjectReader<Ranks> jsonReader = new JsonObjectReader<>(Ranks.class);
		Ranks ranks = jsonReader.readJsonFile(PWCGContextManager.getInstance().getDirectoryManager().getPwcgInputDir() + "Ranks\\", "Ranks.json");
		return ranks;
	}
}
