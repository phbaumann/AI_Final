import java.util.concurrent.ThreadLocalRandom;

// TEST TEST
public class Algorithm {
	// Initializing variables
	public enum MutationType {
		BODY, LIFE;
	}

	// make a new population
	public static Population parentSelection(Population pop1, Population pop2) {

		Population parentPop = new Population();
		return parentPop;
	}

	// grab some random children to indicate which children breed before being
	// isolated
	public static Population naturalSelection(Population children) {
		Random.chance = new Random();
		int inbred = int((chance.nextGaussian() + 1) * children.getSize / 6);
		Population heathens = children;
		
		//sets a number of flies to have bred early
		int i = 0;
		while(i < inbred){
			int rand = ThreadLocalRandom.current().nextInt(0,children.getSize());
			if(!heathens.getMember(rand).bredEarly()){
				heathens.getMember(rand).setBredEarly();
				i++;
			}
		}
		return heathens;
	}

	// make babies using all selected flies
	// accounts for females that bred early
	public static Population makeNewVial(Population earlyMales, Population parents) {
		Population earlyFemales = new Population();
		Population selectedMales = new Population();
		Population lateFemales = new Population();

		for (int i = 0; i < parents.getSize(); i++) {
			if (parents.getMember(i).bredEarly()) {
				// seperate the selected females that bred early
				earlyFemales.saveMember(parents.getMember(i));
			} else {
				// seperate selected males and females for breeding
				if (parents.getMember(i).getSex().equals("female")) {
					lateFemales.saveMember(parents.getMember(i));
				} else {
					selectedMales.saveMember(parents.getMember(i));
				}
			}
		}

		// makes babies with the early flies
		Population newPop = makeBabies(earlyMales, earlyFemales);

		// make babies with the not early flies
		Population newPop2 = makeBabies(selectedMales, lateFemales);
		for (int i = 0; i < newPop2.getSize(); i++) {
			newPop.saveMember(newPop2.getMember(i));
		}

		return newPop;
	}

	// makes a new population using crossover function
	private static Population makeBabies(Population males, Population females) {
		Population newPop = new Population();

		Member male;

		// make 40 to 50 babies
		for (int i = 0; i < females.getSize(); i++) {

			// pick random male to breed with deh fehmales
			int rand = ThreadLocalRandom.current().nextInt(0, males.getSize());
			male = males.getMember(rand);

			// make the babies! and add them to the new population
			int size = ThreadLocalRandom.current().nextInt(7, 11);
			for (int j = 0; j < size; j++) {
				Member newMem = crossover(mem1, mem2);
				newPop.saveMember(newMem);
			}
		}

		return newPop;
	}

	// Randomly combine genes of two members to make a new member
	private static Member crossover(Member mem1, Member mem2) {
		Member newMem = mem1;

		int eyeColorMem = 0;
		// do the punnet square thing
		for (int i = 0; i < mem1.getSize() - 1; i = i + 2) {
			int rand = ThreadLocalRandom.current().nextInt(0, 4);

			// determine which of four possible children was chosen for eye
			// color
			if (i == 0) {
				eyeColorMem = i;
			}
			// use the same child for sex as used for eye color
			if (i == 6) {
				rand = eyeColorMem;
			}
			switch (rand) {
			case 0:
				newMem.setGene(i, mem1.getGene(i));
				newMem.setGene(i + 1, mem2.getGene(i));
				break;
			case 1:
				newMem.setGene(i, mem1.getGene(i));
				newMem.setGene(i + 1, mem2.getGene(i + 1));
				break;
			case 2:
				newMem.setGene(i, mem1.getGene(i + 1));
				newMem.setGene(i + 1, mem2.getGene(i));
				break;
			case 3:
				newMem.setGene(i, mem1.getGene(i + 1));
				newMem.setGene(i + 1, mem2.getGene(i + 1));
				break;
			default:
				System.out.println("out of range");
				break;
			}
		}
		return newMem;

	}

	// mutates the given fly, mutates the genotype associated with the input int
	private static Member mutate(Member mem, int type) {
		Member mutMem = mem;
		switch (type) {
		case BODY:
			break;
		case LIFE:
			mutMem.loseLife();
			break;
		default:
			break;
		}

		return mutMem;
	}

}
