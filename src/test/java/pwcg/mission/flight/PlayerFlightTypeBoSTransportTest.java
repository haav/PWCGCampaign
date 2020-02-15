package pwcg.mission.flight;

import org.junit.Before;
import org.junit.Test;

import pwcg.campaign.Campaign;
import pwcg.campaign.context.PWCGContext;
import pwcg.campaign.context.PWCGProduct;
import pwcg.core.exception.PWCGException;
import pwcg.mission.Mission;
import pwcg.mission.MissionGenerator;
import pwcg.mission.flight.bomb.BombingFlight;
import pwcg.mission.flight.paradrop.ParaDropFlight;
import pwcg.mission.flight.transport.TransportFlight;
import pwcg.mission.flight.validate.GroundAttackFlightValidator;
import pwcg.mission.flight.validate.VirtualWaypointPackageValidator;
import pwcg.mission.flight.validate.EscortForPlayerValidator;
import pwcg.mission.flight.validate.FlightActivateValidator;
import pwcg.mission.target.TargetType;
import pwcg.mission.target.TargetCategory;
import pwcg.mission.target.TargetDefinition;
import pwcg.testutils.CampaignCache;
import pwcg.testutils.SquadronTestProfile;
import pwcg.testutils.TestParticipatingHumanBuilder;

public class PlayerFlightTypeBoSTransportTest
{
    Campaign campaign;

    @Before
    public void fighterFlightTests() throws PWCGException
    {
        PWCGContext.setProduct(PWCGProduct.BOS);
        campaign = CampaignCache.makeCampaign(SquadronTestProfile.TG2_PROFILE);
    }

    @Test
    public void paraDropFlightTest() throws PWCGException
    {
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMissionFromFlightType(TestParticipatingHumanBuilder.buildTestParticipatingHumans(campaign), FlightTypes.PARATROOP_DROP);
        ParaDropFlight flight = (ParaDropFlight) mission.getMissionFlightBuilder().getPlayerFlights().get(0);
         mission.finalizeMission();

        FlightActivateValidator.validate(flight);

        GroundAttackFlightValidator groundAttackFlightValidator = new GroundAttackFlightValidator();
        groundAttackFlightValidator.validateGroundAttackFlight(flight);
        validateTargetDefinition(flight.getFlightInformation().getTargetDefinition());
        assert (flight.getFlightType() == FlightTypes.PARATROOP_DROP);
        EscortForPlayerValidator playerEscortedFlightValidator = new EscortForPlayerValidator(flight);
        playerEscortedFlightValidator.validateEscortForPlayer();
        
        VirtualWaypointPackageValidator virtualWaypointPackageValidator = new VirtualWaypointPackageValidator(mission);
        virtualWaypointPackageValidator.validate();
    }

    @Test
    public void cargoDropFlightTest() throws PWCGException
    {        
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMissionFromFlightType(TestParticipatingHumanBuilder.buildTestParticipatingHumans(campaign), FlightTypes.CARGO_DROP);
        ParaDropFlight flight = (ParaDropFlight) mission.getMissionFlightBuilder().getPlayerFlights().get(0);
         mission.finalizeMission();

        FlightActivateValidator.validate(flight);

        GroundAttackFlightValidator groundAttackFlightValidator = new GroundAttackFlightValidator();
        groundAttackFlightValidator.validateGroundAttackFlight(flight);
        validateTargetDefinition(flight.getFlightInformation().getTargetDefinition());
        assert (flight.getFlightType() == FlightTypes.CARGO_DROP);
        EscortForPlayerValidator playerEscortedFlightValidator = new EscortForPlayerValidator(flight);
        playerEscortedFlightValidator.validateEscortForPlayer();
        
        VirtualWaypointPackageValidator virtualWaypointPackageValidator = new VirtualWaypointPackageValidator(mission);
        virtualWaypointPackageValidator.validate();
    }

    @Test
    public void transportFlightTest() throws PWCGException
    {
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMissionFromFlightType(TestParticipatingHumanBuilder.buildTestParticipatingHumans(campaign), FlightTypes.TRANSPORT);
        TransportFlight flight = (TransportFlight) mission.getMissionFlightBuilder().getPlayerFlights().get(0);
         mission.finalizeMission();

        FlightActivateValidator.validate(flight);

        assert (flight.getFlightType() == FlightTypes.TRANSPORT);
        EscortForPlayerValidator playerEscortedFlightValidator = new EscortForPlayerValidator(flight);
        playerEscortedFlightValidator.validateNoEscortForPlayer();
        
        VirtualWaypointPackageValidator virtualWaypointPackageValidator = new VirtualWaypointPackageValidator(mission);
        virtualWaypointPackageValidator.validate();
    }


    @Test
    public void bombFlightTest() throws PWCGException
    {
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMissionFromFlightType(TestParticipatingHumanBuilder.buildTestParticipatingHumans(campaign), FlightTypes.BOMB);
        BombingFlight flight = (BombingFlight) mission.getMissionFlightBuilder().getPlayerFlights().get(0);
         mission.finalizeMission();

        FlightActivateValidator.validate(flight);

        GroundAttackFlightValidator groundAttackFlightValidator = new GroundAttackFlightValidator();
        groundAttackFlightValidator.validateGroundAttackFlight(flight);
        validateTargetDefinition(flight.getFlightInformation().getTargetDefinition());
        assert (flight.getFlightType() == FlightTypes.BOMB);
        EscortForPlayerValidator playerEscortedFlightValidator = new EscortForPlayerValidator(flight);
        playerEscortedFlightValidator.validateEscortForPlayer();
        
        VirtualWaypointPackageValidator virtualWaypointPackageValidator = new VirtualWaypointPackageValidator(mission);
        virtualWaypointPackageValidator.validate();
    }

    @Test
    public void lowAltBombFlightTest() throws PWCGException
    {
        MissionGenerator missionGenerator = new MissionGenerator(campaign);
        Mission mission = missionGenerator.makeMissionFromFlightType(TestParticipatingHumanBuilder.buildTestParticipatingHumans(campaign), FlightTypes.LOW_ALT_BOMB);
        BombingFlight flight = (BombingFlight) mission.getMissionFlightBuilder().getPlayerFlights().get(0);
         mission.finalizeMission();

        FlightActivateValidator.validate(flight);

        GroundAttackFlightValidator groundAttackFlightValidator = new GroundAttackFlightValidator();
        groundAttackFlightValidator.validateGroundAttackFlight(flight);
        validateTargetDefinition(flight.getFlightInformation().getTargetDefinition());
        assert (flight.getFlightType() == FlightTypes.LOW_ALT_BOMB);
        EscortForPlayerValidator playerEscortedFlightValidator = new EscortForPlayerValidator(flight);
        playerEscortedFlightValidator.validateEscortForPlayer();
        
        VirtualWaypointPackageValidator virtualWaypointPackageValidator = new VirtualWaypointPackageValidator(mission);
        virtualWaypointPackageValidator.validate();
    }

    public void validateTargetDefinition(TargetDefinition targetDefinition)
    {
        assert (targetDefinition.getAttackingCountry() != null);
        assert (targetDefinition.getTargetCountry() != null);
        assert (targetDefinition.getTargetCategory() != TargetCategory.TARGET_CATEGORY_NONE);
        assert (targetDefinition.getTargetType() != TargetType.TARGET_NONE);
    }
}
