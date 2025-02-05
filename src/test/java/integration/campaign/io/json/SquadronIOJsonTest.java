package integration.campaign.io.json;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import pwcg.campaign.SquadHistory;
import pwcg.campaign.SquadHistoryEntry;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.campaign.io.json.PwcgJsonWriter;
import pwcg.campaign.io.json.SquadronIOJson;
import pwcg.campaign.squadron.Squadron;
import pwcg.core.exception.PWCGException;
import pwcg.core.utils.DateUtils;

@ExtendWith(MockitoExtension.class)
public class SquadronIOJsonTest
{
    @Test
    public void readJsonRoFSquadronsTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.FC);
        List<Squadron> squadrons = SquadronIOJson.readJson();
        Assertions.assertTrue (squadrons.size() > 0);

        for (Squadron squadron : squadrons)
        {
            Assertions.assertTrue (squadron.getSquadronRoles().getSquadronRolePeriods().size() > 0);
            Assertions.assertTrue (squadron.getService() > 0);
            
            verifyLafayetteEsc(squadron);
            verifyRFCToRAF(squadron);
            verifyRNASToRAF(squadron);
        }
    }

    private void verifyLafayetteEsc(Squadron squadron) throws PWCGException
    {
        if (squadron.getSquadronId() == 101124)
        {
            SquadHistory squadronHistory = squadron.getSquadHistory();
            Assertions.assertTrue (squadronHistory != null);
            
            SquadHistoryEntry  squadHistoryEntry = squadronHistory.getSquadHistoryEntry(DateUtils.getDateYYYYMMDD("19180219"));
            Assertions.assertTrue (squadHistoryEntry != null);
            Assertions.assertTrue (squadHistoryEntry.getArmedServiceName().equals("United States Air Service"));
            Assertions.assertTrue (squadHistoryEntry.getSkill() == SquadHistoryEntry.NO_SQUADRON_SKILL_CHANGE);
        }
    }

    private void verifyRFCToRAF(Squadron squadron) throws PWCGException
    {
        if (squadron.getSquadronId() == 102020)
        {
            SquadHistory squadronHistory = squadron.getSquadHistory();
            Assertions.assertTrue (squadronHistory != null);
            
            SquadHistoryEntry  squadHistoryEntry = squadronHistory.getSquadHistoryEntry(DateUtils.getDateYYYYMMDD("19180401"));
            Assertions.assertTrue (squadHistoryEntry != null);
            Assertions.assertTrue (squadHistoryEntry.getArmedServiceName().equals("Royal Air Force"));
            Assertions.assertTrue (squadHistoryEntry.getSquadName().equals("No 20 Squadron RAF"));
            Assertions.assertTrue (squadHistoryEntry.getSkill() == SquadHistoryEntry.NO_SQUADRON_SKILL_CHANGE);
        }
    }

    private void verifyRNASToRAF(Squadron squadron) throws PWCGException
    {
        if (squadron.getSquadronId() == 102209)
        {
            SquadHistory squadronHistory = squadron.getSquadHistory();
            Assertions.assertTrue (squadronHistory != null);
            
            SquadHistoryEntry  squadHistoryEntry = squadronHistory.getSquadHistoryEntry(DateUtils.getDateYYYYMMDD("19180401"));
            Assertions.assertTrue (squadHistoryEntry != null);
            Assertions.assertTrue (squadHistoryEntry.getArmedServiceName().equals("Royal Air Force"));
            Assertions.assertTrue (squadHistoryEntry.getSquadName().equals("No 209 Squadron RAF"));
            Assertions.assertTrue (squadHistoryEntry.getSkill() == SquadHistoryEntry.NO_SQUADRON_SKILL_CHANGE);
        }
    }

    @Test
    public void readJsonBoSSquadronsTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        List<Squadron> squadrons = SquadronIOJson.readJson();
        Assertions.assertTrue (squadrons.size() > 0);
        
        boolean success = true;
        for (Squadron squadron : squadrons)
        {
            Assertions.assertTrue (squadron.getSquadronRoles().getSquadronRolePeriods().size() > 0);
            Assertions.assertTrue (squadron.getService() > 0);
        }
        
        assert(success);
    }

    @Test
    public void writeJsonBoSSquadronsTest() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        List<Squadron> squadrons = SquadronIOJson.readJson();
        
        PwcgJsonWriter<Squadron> jsonWriter = new PwcgJsonWriter<>();
        String squadronDir = PWCGContext.getInstance().getDirectoryManager().getPwcgSquadronDir();
        jsonWriter.writeAsJson(squadrons.get(0), squadronDir, "TestSquadron");
    }
}
