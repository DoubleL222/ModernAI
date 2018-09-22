import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Random;

public class UppercaseGA {
	private final static float CHANCETOMUTATE = 0.05f;
	private final static int POPULATION = 100;
	private final static int GENOTYPELENGTH = 50;
	private final static int ELITEPERCENTAGE = 10;
	
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
			return (int) (arg0.fitness*100 - this.fitness*100);
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
	
	private Vector<int[]> getElitePopulation(float percentage, Vector<int[]> population) 
	{
		Vector<Genotype<int[]>> fullPopulation = new Vector<>();
		Vector<int[]> elites = new Vector<int[]>();
		for(int[] gene : population) 
		{
			fullPopulation.add(new Genotype<int[]>(GetFitness(gene), gene));
		}
		Collections.sort(fullPopulation);
		
		/*
		System.out.println();
		System.out.println("Sorted:");

		for(Genotype<int[]> g : fullPopulation) 
		{
			PrintGenotype(g.genotype);
			System.out.print("F: "+ g.fitness +"; ");
		}

		for(int i = 0; i<fullPopulation.size(); i++) 
		{
			PrintGenotype(fullPopulation.get(i).genotype);
			System.out.print("F: "+ fullPopulation.get(i).fitness +"; ");
		}
		*/
		
		int eliteCount =(int) (percentage/100.0f * population.size());
		for(int i = 0; i<eliteCount; i++) 
		{
			elites.add(fullPopulation.get(i).genotype);
		}
		return elites;
	}
	
	private float GetFitness(int[] genotype) 
	{
		float fitness = 0;
		for(int fenotype : genotype) 
		{
			fitness+=fenotype;
		}
		return fitness/genotype.length;
	}
	
	private float GetMaxFitness(Vector<int[]> population) 
	{
		float MaxFitness = Float.MIN_VALUE;
		for(int[] gene : population) 
		{
			float currFitness = GetFitness(gene);
			
			if(currFitness>MaxFitness) 
			{
				MaxFitness = currFitness;
			}
		}
		return MaxFitness;
	}
	
	private int[] MekeMutant(int[] elite) 
	{
		int[] mutant = new int[elite.length];
		
		for(int i =0; i< elite.length; i++) 
		{
			Random r = new Random();
			float random = 0f + r.nextFloat() * (1f - 0f);
			
			int f = elite[i];
			
			if(random < CHANCETOMUTATE) 
			{
				if(f == 0) 
				{
					f = 1;
				}
				else if(f == 1) 
				{
					f = 0;
				}
			}
			mutant[i] = f;
		}
		return mutant;
	}
	
	private Vector<int[]> MutateElite(Vector<int[]> elites, int populationCount)
	{
		Vector<int[]> mutants = new Vector<int[]>();
		int mutantsPerElite = populationCount/elites.size();
		for(int i = 0; i < elites.size(); i++) 
		{
			for(int j = 0; j < mutantsPerElite; j++) 
			{
				mutants.add(MekeMutant(elites.get(i)));
			}
		}
		return mutants;
	}
	
	public static void main(String[] args)
	{
		UppercaseGA uGa = new UppercaseGA();
		Vector<int[]> population = uGa.GeneratePopulation(GENOTYPELENGTH, POPULATION);
		System.out.println("Population size: " + population.size());
		uGa.PrintPopulation(population);
		Vector<int[]> elites = uGa.getElitePopulation(ELITEPERCENTAGE, population);
		int generation = 0;
		System.out.println("ELITES");
		uGa.PrintPopulation(elites);
		System.out.println("Population size: " + population.size());
		while(uGa.GetMaxFitness(elites)<1) 
		{
			System.out.println();
			System.out.println("-----GENERATION "+generation+"-------");
			System.out.println();
			population = uGa.MutateElite(elites, POPULATION);
			System.out.println("Population size: " + population.size());
			elites = uGa.getElitePopulation(ELITEPERCENTAGE, population);
			generation++;
			
			System.out.println("ELITES");
			uGa.PrintPopulation(elites);
		}
	}
}
