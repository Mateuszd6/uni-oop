// Mateusz Dudziński PO 2018L, Task 3.

public class Garden {
	
    // If vegetable[i] is null, there is nothing planeted there yet.
    private Vegetable[] vegetable_slots;

    // Returns the number of slots for vegetables.
    public int getSize() {
        return vegetable_slots.length;
    }

    public Garden(int k) {
        vegetable_slots = new Vegetable[k];
    }

    public boolean vegetableAtSlotExists(int slot_id) {
    	return (vegetable_slots[slot_id] != null);
    }
    
    // Harvest the vegetable from the slot. Assumes that there is a
    // vegetable in the slot.
    public Vegetable harvestFromSlot(int slot_id) {
    	assert (vegetable_slots[slot_id] != null);
    	
        Vegetable res = vegetable_slots[slot_id];
        vegetable_slots[slot_id] = null;
        return res;
    }

    public void addVegetableAtSlot(Vegetable vegetable, int slot_id) {
        // TODO: Inform user that is is not possible to plant a new vegetable 
    	// on already taken slot.
        assert vegetable_slots[slot_id] == null;

        vegetable_slots[slot_id] = vegetable;
    }

    // Assumes that the vegetable at slot exists!
    public float evaluateVegetableAt(int slot_id) {
    	// If there is no vegetable, the value is 0.
    	assert vegetable_slots[slot_id] != null;
    	
        return vegetable_slots[slot_id].evaluate();
    }

}
