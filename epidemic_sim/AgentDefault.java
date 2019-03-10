public class AgentDefault extends Agent {

    public AgentDefault(SimulationInstance currentSimulation, int id) {
        super(currentSimulation, id);
    }

    @Override
    protected float getChancesToScheduleMeeting() {
        float superResult = super.getChancesToScheduleMeeting();
        return isInfected() ? superResult / 2 : superResult;
    }

    @Override
    public String toString() {
        return super.toString() + " zwykły";
    }
}
