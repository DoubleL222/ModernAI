import java.util.Vector;
import java.util.Random;

public class UppercaseGA {
	private class Genotype<T> implements Comparable<Genotype<T>>
	{
		public float fitness;
		public T genotype;
		
		public Genotype(float _fitness, T _genotype)
		{
			fitness = _fitness;
			genotype = _genotype;
		}

		@Override
		public int compareTo(Genotype<T> arg0) {
			return (int) (this.fitness - arg0.fitness);
		}
	}
	
	
	private Vector<int[]> GeneratePopulation(int genotypeLength, int populationSize)
	{
		Vector<int[]> pop = new Vector<int[]>();
		for(int i = 0; i < populationSize; i++) 
		{
			pop.add(GenerateIndividualGenotype(genotypeLength));
		}
		return pop;
	}
	
	private int[] GenerateIndividualGenotype(int genotypeLength) 
	{
		int[] genotype = new int[genotypeLength];
		
		for(int i = 0; i<genotypeLength; i++) 
		{
			genotype[i] = randInt(0, 1);
		}
		
		return genotype;
	}
	
	private int randInt(int min, int max) 
	{
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	private void PrintPopulation(Vector<int[]> population) 
	{
		System.out.println();
		System.out.println("-----Population print-------");
		System.out.println();
		for(int i = 0; i < population.size(); i++) 
		{
			System.out.print(i+": ");
			PrintGenotype(population.get(i));
			System.out.println();
		}
	}
	
	private void PrintGenotype(int[] genotype) 
	{
		System.out.print("G: ");
		for(int i = 0; i<genotype.length; i++) 
		{
			System.out.print(genotype[i]);
		}
	}
	
	public static void main(String[] args)
	{
		UppercaseGA uGa = new UppercaseGA();
		Vector<int[]> population = uGa.GeneratePopulation(8, 100);
		uGa.PrintPopulation(population);
	}
}
