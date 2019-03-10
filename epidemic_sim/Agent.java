import java.util.ArrayList;

public abstract class Agent {
    protected enum HealthState { INFECTED, HEALTHY, RESISTANT, DEAD }

    protected ArrayList<Agent> friends;
    // Reference to the current simulation, to gain access to some simulation
    // data, and RandomGenerator instance.
    private SimulationInstance currentSimulation;
    private int id;
    private HealthState healthState;

    public Agent(SimulationInstance currentSimulation, int id) {
        this.healthState = HealthState.HEALTHY;
        this.id = id;
        this.currentSimulation = currentSimulation;
        this.friends = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    protected void addFriend(Agent friend) {
        assert !isDead();
        assert !friend.isDead();

        // TODO: Handle the case, when the edge already exists!
        friends.add(friend);
    }

    public void infectAgent() {
        assert !isDead();

        // The only health state that might change into infected state.
        assert healthState == HealthState.HEALTHY;

        currentSimulation.numberOfHealthyAgents--;
        currentSimulation.numberOfInfectedAgents++;

        Debug.log("%s gets infected!", this.toString());
        healthState = HealthState.INFECTED;
    }

    private void killAgent() {
        assert !isDead();
        assert healthState == HealthState.INFECTED;

        Debug.log("%s dies.", this.toString());
        currentSimulation.numberOfInfectedAgents--;

        // Dead agent does not have any friends.
        healthState = HealthState.DEAD;
        friends = null;
    }

    public boolean isHealthyButNotResistant() {
        return healthState == HealthState.HEALTHY;
    }

    public boolean isDead() {
        return healthState == HealthState.DEAD;
    }

    public boolean isInfected() {
        return healthState == HealthState.INFECTED;
    }

    private void makeAgentResistant() {
        assert !isDead();
        assert healthState == HealthState.INFECTED;

        currentSimulation.numberOfResistantAgents++;
        currentSimulation.numberOfInfectedAgents--;

        Debug.log("%s cures infections and becomes resistant.", this.toString());

        healthState = HealthState.RESISTANT;
    }

    // This can be overridden, because some agents have lower chances to
    // schedule meetings, when they are infected.
    protected float getChancesToScheduleMeeting() {
        return currentSimulation.data.getMeetingChances();
    }

    // This happens at the beginning of each day. If agent is infected, he may
    // die or cure the infection.
    public void updateHealthState() {
        // Dead agents do not interact!
        if (isDead())
            return;

        if (isInfected()) {
            if (currentSimulation.randomGenerator.getEventOccurrence(
                    currentSimulation.data.getDyingChances())) {
                killAgent();
            } else if (currentSimulation.randomGenerator.getEventOccurrence(
                    currentSimulation.data.getCureChances())) {
                makeAgentResistant();
            }
        }
    }

    // Find agents that the agents will possibly meet. This list may contain
    // duplicates, increasing chances for the meeting to happen. Its also
    // possible to make more than one meeting with the same guy, in one day.
    protected ArrayList<Agent> getAgentsToMeetPossibly() {
        ArrayList<Agent> result = new ArrayList<>(friends.size());
        for (Agent agent : friends)
            if (!agent.isDead() && agent != this)
                result.add(agent);

        return result;
    }

    public void scheduleMeetings() {
        // Dead agents do not interact!
        if (isDead())
            return;

        // This might contain duplicates, and we are OK with it.
        ArrayList<Agent> agentsToMeet = getAgentsToMeetPossibly();

        if (!agentsToMeet.isEmpty()) {
            boolean meeting = currentSimulation.randomGenerator.
                    getEventOccurrence(getChancesToScheduleMeeting());

            while (meeting) {
                int meetingAgentIdx = currentSimulation.randomGenerator.
                        getRandomIntInRange(0, agentsToMeet.size());

                currentSimulation.scheduleMeetingAtRandomDay(this,
                        agentsToMeet.get(meetingAgentIdx));

                meeting = currentSimulation.randomGenerator.
                        getEventOccurrence(getChancesToScheduleMeeting());
            }
        }
    }

    @Override
    public String toString() {

        return (id + 1) + (healthState == HealthState.HEALTHY ||
                healthState == HealthState.RESISTANT ? "" : "*");
    }

}
