
import java.util.LinkedList;

public class Population {

	private LinkedList<Member> members;

	public Population(int size) {
		members = new LinkedList<Member>();

		// make the list of members
		for (int i = 0; i < size; i++) {
			Member newMember = new Member();
			members.add(newMember);
		}
	}
	
	// second constructor for non-initial populations
	public Population() {
		this.members = new LinkedList<Member>();
	}
	
	// returns the member at the given index
	public Member getMember(int i) {
		return members.get(i);
	}

	// removes the member at the given index
	public void removeMember(int i) {
		members.remove(i);
	}
	
	// returns the size of population
	public int getSize() {
		return members.size();
	}

	// replaces the member at index i with given member
	public void saveMember(Member m) {
		members.add(m);
	}
	
	// Gets fittest member
	public Member getFittest() {

		int bestInd = 0;
		int best = members.get(0).getFitness();

		// Compares fitness of each member, grabs the member with the highest fitness
		for (int i = 0; i < getSize(); i++) {
			if (members.get(i).getFitness() > best) {
				bestInd = i;
				best = members.get(bestInd).getFitness();
			}
		}
		return members.get(bestInd);
	}


}