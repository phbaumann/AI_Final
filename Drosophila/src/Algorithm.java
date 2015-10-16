import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// TEST TEST
public class Algorithm {
	// Initializing variables
	public enum MutationType {
		BODY, LIFE;
	}

	// make a new population
	public static Population parentSelection(Population pop1, Population pop2) {

		Population parentPop = new Population(pop1.members);
		System.out.println(parentPop.toString());
		parentPop.members.addAll(pop2.members);
		System.out.println(parentPop.toString());
		Population best = new Population();
		int popSize = parentPop.getSize();
		int minDad = ((popSize/2)-1);
		int maxDad = ((popSize/2)+1);
		System.out.println("min: " + minDad + "max: " + maxDad + "popSize: " + popSize);
		int totDads = 5;
		//int totDads = popsize;
		int totMoms = 5;
		int numDads = 0;
		int numMoms = 0;
		int i = 0;
		int index;
		while(i < 10){
			if (parentPop.getSize() < 1) {
				System.out.println("Empty parent Population\n");
				break;
			}
			Member bestMember = parentPop.getFittest();
			if (numDads > totDads && bestMember.getSex().equals("male")) {
				parentPop.removeMember(parentPop.indexOf(bestMember));
				continue;
			}
			if (numMoms > totMoms && bestMember.getSex().equals("female")) {
				parentPop.removeMember(parentPop.indexOf(bestMember));
				continue;
			}
			
			else {
				if(bestMember.getSex().equals("male")){
				numDads++;
				}
			else{
				numMoms++;
			}
			//TODO WHen running, will save female as female, but then when a male is added 
			//will change sexes of all in array to male. How to fix??????????
			
//			if(bestMember.getSex().equals("male") && numDads > totDads){
//				bestMember.setGene(7, 0);
//				bestMember.setGene(6, 0);
//				best.saveMember(bestMember);
//				index = parentPop.indexOf(bestMember);
//				parentPop.removeMember(index);
//				i++;
//				System.out.println("is now male trans");
//				System.out.println("bestsssssss: " + best.toString());
//			}
			if (bestMember.getSex().equals("male") && numDads <= totDads){
				best.saveMember(bestMember);
				index = parentPop.indexOf(bestMember);
				parentPop.removeMember(index);
				i++;
				System.out.println("good dads");
				System.out.println("bestsssssss: " + best.toString());
			}
			if(bestMember.getSex().equals("female") && numMoms <= totMoms){
				best.saveMember(bestMember);
				index = parentPop.indexOf(bestMember);
				parentPop.removeMember(index);
				i++;
				System.out.println("good moms");
				System.out.println("bestsssssss: " + best.toString());
			}
//			if(bestMember.getSex().equals("female") && numMoms > totMoms){
//				bestMember.setGene(7, 1);
//				best.saveMember(bestMember);
//				index = parentPop.indexOf(bestMember);
//				parentPop.removeMember(index);
//				i++;
//				System.out.println("is now trans");
//				System.out.println("bestsssssss: " + best.toString());
//			}
		}
	}
		System.out.println("bestsssssss: " + best.toString());
		return best;
		
	}

	// grab some random children to indicate which children breed before being
	// isolated
	public static Population naturalSelection(Population children) {
		Random chance = new Random();
		int inbred = (int) ((chance.nextGaussian() + 1) * children.getSize() / 6);
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
	public static Population makeBabies(Population males, Population females) {
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
				Member newMem = crossover(male, females.getMember(i));
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
	public static void mutate(Member mem, int type) {
		int rand = ThreadLocalRandom.current().nextInt(0, 2);
		switch (type) {
		case 0:   //body mutation
			int rand2 = ThreadLocalRandom.current().nextInt(1, 3);
			if (rand2 == mem.getGene(rand+2)) {
				rand2 = ThreadLocalRandom.current().nextInt(1, 3);
			}
			mem.setGene(rand+2, rand2);
			break;
		case 1:   //eye mutation
			mem.setGene(rand, Math.abs(mem.getGene(rand)-1));
			break;
		case 2:   //wing mutation
			mem.setGene(rand+4, Math.abs(mem.getGene(rand+4)-1));
			break;
		case 3:   //life mutation
			mem.useLife();
			break;
		default:
			break;
		}

	}

}
