import java.util.ArrayList;
import java.util.HashSet;

public class AgentFriendly extends Agent {
    public AgentFriendly(SimulationInstance currentSimulation, int id) {
        super(currentSimulation, id);
    }

    @Override
    protected ArrayList<Agent> getAgentsToMeetPossibly() {
        if (isInfected())
            return friends;
        else {
            HashSet<Agent> agentsToMeet = new HashSet<>();

            for (Agent friend : friends)
                if (!friend.isDead()) {
                    agentsToMeet.add(friend);

                    for (Agent friendsFriend : friend.friends)
                        if (!friendsFriend.isDead() && friendsFriend != this)
                            agentsToMeet.add(friendsFriend);
                }

            // Make sure we didn't add dead agent, or this current agent to the list.
            assert !agentsToMeet.contains(this);
            for (Agent a : agentsToMeet)
                assert !a.isDead();

            return new ArrayList<>(agentsToMeet);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " towarzyski";
    }
}
