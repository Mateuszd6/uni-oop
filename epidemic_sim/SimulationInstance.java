// IMPORTANT TODO: Handle all printStackTrace'es and change them to some
// verbose information about the program failure. Also don't forget to shut
// down the program!

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

// The single instance of simulation. I've designed the code so that user can
// run multiple simulations in a different threads at once. This class is named
// 'SimulationInstance' instead of 'Simulation' because I was forced to name the
// class with main function 'Symulacja' and I wanted to avoid confusion.
public class SimulationInstance {
    // Each 'simulation' has its own random generator, so we can start
    // simultaneously multiple simulations with different seeds.
    public RandomGenerator randomGenerator;

    public SimulationData data; // TODO: make private
    protected int numberOfHealthyAgents;
    protected int numberOfInfectedAgents;
    protected int numberOfResistantAgents;
    private int currentDay;
    private Agent[] agents;

    // meeting day number -> List of pair of agents that meet this day.
    private ArrayList<ArrayList<Agent[]>> meetingsSchedule;

    // TODO: Why we need to put this in a constructor, when we clear them at
    // the beginning of the simulation?
    public SimulationInstance(SimulationData data) {
        this.data = data;
        this.randomGenerator = new RandomGenerator(data.getSeed());
        this.currentDay = 0;

        this.meetingsSchedule = new ArrayList<>(data.getNumberOfDays());
        for (int i = 0; i < data.getNumberOfDays(); ++i)
            meetingsSchedule.add(new ArrayList<Agent[]>());
    }

    private String agentsGraphToString(Agent[] agentsList) {
        StringBuilder sb = new StringBuilder();
        for (Agent agent : agentsList) {
            sb.append((agent.getId() + 1)).append(' ');
            for (Agent friend : agent.friends)
                sb.append((friend.getId() + 1)).append(' ');
            sb.append('\n');
        }

        return sb.toString();
    }

    public void scheduleMeetingAtRandomDay(Agent self, Agent other) {
        // The meeting cannot happen, because there are no more incoming days.
        if (currentDay >= data.getNumberOfDays() - 1)
            return;

        Agent[] m = new Agent[2];
        m[0] = self;
        m[1] = other;

        int meetingDay = randomGenerator.getRandomIntInRange(
                currentDay + 1, data.getNumberOfDays());

        Debug.log("Scheduling meeting: %d - %d (at day: %d)",
                self.getId() + 1, other.getId() + 1,
                meetingDay);

        meetingsSchedule.get(meetingDay).add(m);
    }

    private void meetAgents(Agent self, Agent other) {
        Debug.log("Meeting %d%s and %d%s.", self.getId(),
                self.isInfected() ? "*" : "", other.getId(),
                other.isInfected() ? "*" : "");

        Agent infectedAgent = null;
        Agent notResistantAgent = null;

        if (self.isInfected() && other.isHealthyButNotResistant()) {
            infectedAgent = self;
            notResistantAgent = other;
        } else if (other.isInfected() && self.isHealthyButNotResistant()) {
            infectedAgent = other;
            notResistantAgent = self;
        }

        // If one agent is infected and the second one is not resistant there
        // are chances that second one gets infected.
        if (infectedAgent != null && notResistantAgent != null)
            if (randomGenerator.getEventOccurrence(data.getInfectingChances())) {
                notResistantAgent.infectAgent();
            }
    }

    public void runSimulation() {
        Debug.log("Doing simulation with parameters:\n" + data.toString());

        // TODO: cleanup the values so that it can be called again.
        currentDay = 0;
        numberOfHealthyAgents = data.getNumberOfAgents();
        numberOfInfectedAgents = 0;
        numberOfResistantAgents = 0;

        // Create a graph:
        int numberOfEdges =
                (int) (data.getFriendsAverage() * data.getNumberOfAgents() / 2.0f);
        agents = new Agent[data.getNumberOfAgents()];
        for (int i = 0; i < data.getNumberOfAgents(); ++i)
            agents[i] = randomGenerator.getEventOccurrence(data.getFriendlyAgentChances())
                    ? new AgentDefault(this, i)
                    : new AgentFriendly(this, i);

        int randomIdx = randomGenerator.getRandomIntInRange(0, agents.length);
        // We start with one infected agent.
        agents[randomIdx].infectAgent();

        Debug.log("Infected agent: %s", agents[randomIdx].toString());

        // TODO: What if there is one agent, but edge average is > 0.
        // If there is one agent we cannot add any edges!
        if (data.getNumberOfAgents() > 1) {
            for (int i = 0; i < numberOfEdges; ++i) {
                int rand_1 = randomGenerator.getRandomIntInRange(
                        0, data.getNumberOfAgents());

                // Get random number for range [0-data.liczbaAgentów) excluding
                // rand_1.
                int rand_2 = randomGenerator.getRandomIntInRange(
                        1, data.getNumberOfAgents());
                if (rand_2 == rand_1)
                    rand_2 = 0;

                assert rand_1 != rand_2;

                // TODO: Handle the case, when the edge already exists!
                agents[rand_1].addFriend(agents[rand_2]);
                agents[rand_2].addFriend(agents[rand_1]);
            }
        }

        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(
                    new FileOutputStream(data.getRapportFile()), "UTF-8");

            writer.write(data.toString());
            writer.write("\n# agenci jako: id typ lub id* typ dla chorego\n");

            for (Agent a : agents)
                writer.write(a.toString() + "\n");

            writer.write("\n# graf\n");
            writer.write(agentsGraphToString(agents));

            writer.write("\n# liczność w kolejnych dniach\n");
        } catch (IOException e) {
            System.out.println("IO-internal error. Exittig...");
            System.exit(1);
        }

        while (currentDay < data.getNumberOfDays()) {
            for (Agent agent : agents)
                agent.updateHealthState();

            for (Agent agent : agents)
                agent.scheduleMeetings();

            ArrayList<Agent[]> meetingsForCurrentDay =
                    meetingsSchedule.get(currentDay);

            for (int i = 0; i < meetingsForCurrentDay.size(); ++i) {
                assert meetingsForCurrentDay.size() == 2;
                assert meetingsForCurrentDay.get(i)[0] != null;
                assert meetingsForCurrentDay.get(i)[1] != null;

                meetAgents(meetingsForCurrentDay.get(i)[0],
                        meetingsForCurrentDay.get(i)[1]);
            }

            try {
                writer.write(numberOfHealthyAgents + " " + numberOfInfectedAgents +
                        " " + numberOfResistantAgents + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentDay++;
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
