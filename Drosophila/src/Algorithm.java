import java.util.concurrent.ThreadLocalRandom;

public class Algorithm {
	// Initializing variables
	private static final int GENSIZE = 100;

	// make a new population
	public static Population parentSelection(Population pop1, Population pop2) {

		Population parentPop = new Population();
		return parentPop;
	}

	// makes a new population using crossover function
	public static Population makeBabies(Population pop) {
		Population newPop = new Population();

		Member mem1;
		Member mem2;

		for (int i = 0; i < GENSIZE; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0, pop.getSize());
			int rand2 = ThreadLocalRandom.current().nextInt(0, pop.getSize());

			mem1 = pop.getMember(rand);
			mem2 = pop.getMember(rand2);

			// make the babies! and add them to the new population
			Member newMem = null;
			newMem = crossover(mem1, mem2);

			if (newMem != null) {
				newPop.saveMember(newMem);
			} else {
				System.out.println("crossover didn't create a new member");
				System.exit(0);
			}
		}
		return newPop;
	}

	// Randomly combine genes of two members to make a new member
	private static Member crossover(Member mem1, Member mem2) {
		Member newMem = mem1;

		// do the punnet square thing
		for (int i = 0; i < mem1.getSize()-1; i = i + 2) {
			int rand = ThreadLocalRandom.current().nextInt(0, 4);
			switch (rand) {
			case 0:
				newMem.setGene(i, mem1.getGene(i));
				newMem.setGene(i+1, mem2.getGene(i));
				break;
			case 1:
				newMem.setGene(i, mem1.getGene(i));
				newMem.setGene(i+1, mem2.getGene(i+1));
				break;
			case 2:
				newMem.setGene(i, mem1.getGene(i+1));
				newMem.setGene(i+1, mem2.getGene(i));
				break;
			case 3:
				newMem.setGene(i, mem1.getGene(i+1));
				newMem.setGene(i+1, mem2.getGene(i+1));
				break;
			default:
				System.out.println("out of range");
				break;
			}
		}
		return newMem;

	}

	private static Member mutate(Member mem) {
		Member mutMem = new Member();

		return mutMem;
	}

	// Getters and setters
	public static int getGenSize() {
		return GENSIZE;
	}
}
