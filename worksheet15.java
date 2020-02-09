import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class worksheet15 
{


	public static void main (String args[])
	{
		double[][] distance = TSP.ReadArrayFile("C:\\Year 2\\CS2004- Algorithms and their Applications - Copy\\coursework\\Worksheet 15\\CS2004 TSP Data (2017-2018)\\TSP_48.txt"," ");
		//double array list. This dictates numbers of cities being travelled to. text within brackets represents cities, e.g. TSP_48.txt is 48 cities.
		
	
		//shows best possible tour
		double MSTLength =0;
		double MSTFitness [][] = MST.PrimsMST(distance);
		
		
		for(int i = 0; i < distance.length; i++) {
			for(int j =0; j<i; j++) {
				MSTLength += MSTFitness[i][j];
			}
		}
		System.out.println("MST Fitness: " + MSTLength);//MST is lower than optimal, this is because it is not achievable
		System.out.println("-----------");
		//Cities for Optimum Tour/Solution and fitness 
		ArrayList<Integer> optimumSol = TSP.ReadIntegerFile("C:\\Year 2\\CS2004- Algorithms and their Applications - Copy\\coursework\\Worksheet 15\\CS2004 TSP Data (2017-2018)\\TSP_48_OPT.txt");
		TSPSolutions optimumSolution = new TSPSolutions(optimumSol,distance.length); //start of an object being created
		double optimumFitness = optimumSolution.fitnessFunction(distance);
		System.out.println("Optimum Tour: " + optimumSolution.getTour());
		System.out.println("Optimum Fitness = " + optimumFitness);
		double percentage;// this is to check the percentage efficiency of travelling to different cities
		
		System.out.println("-----------");//Makes it easier to read
		System.out.println("Simple Hill Climbing (Random Mutation HC):");//Algorithm being run
		
		
		
		    for(int i = 1; i <= 10; i++) { // Change number in for loop, to change number of iterations 
			TSPSolutions bestRRHC = RRHC(100000,10,distance,false);//100000 is numbers of iterations, 10 is the number of times the journey will restart, Restart only for RRCH
			TSPSolutions bestSHC = SHC(10000,distance,false);
			TSPSolutions bestRMHC = RMHC(10,distance,false);
			TSPSolutions bestSA = SA(100000,distance,false,0.99,10000);
			System.out.println(bestSHC.getFitness());
		}
	
		TSPSolutions bestRMHC = RMHC(10000,distance,false);
	    double fitness = bestRMHC.fitnessFunction(distance);

		System.out.println("Best Solution: " + bestRMHC.getTour());
		System.out.println("TOP Fitness = " + bestRMHC.getFitness());
		percentage = (optimumFitness/ bestRMHC.fitnessFunction(distance))*100; //Outputs percentage efficiency for OptimalFitness
		System.out.println("Percentage efficiency OPT: " + percentage +"%");
		percentage = (MSTLength/ bestRMHC.fitnessFunction(distance))*100; //Displays Percentage efficiency for Minimum spanning tree
		System.out.println("Percentage efficiency MST: " + percentage +"%");
		
		System.out.println("-----------");
		System.out.println("Stochastic HC:");
		
		TSPSolutions bestSHC = SHC(10000,distance,false);
		System.out.println("Best Solution: " + bestSHC.getTour());
		System.out.println("TOP Fitness = " + bestSHC.getFitness());
		percentage = (optimumFitness/ bestSHC.fitnessFunction(distance))*100;//Outputs percentage efficiency for OptimalFitness
		System.out.println("Percentage efficiency OPT: " + percentage +"%");
		percentage = (MSTLength/ bestRMHC.fitnessFunction(distance))*100; //Displays Percentage efficiency for Minimum spanning tree
		System.out.println("Percentage efficiency MST: " + percentage +"%");
		
		System.out.println("-----------");
		System.out.println("Random Restart HC:");
		TSPSolutions bestRRHC = RRHC(10000,10,distance,false);
		System.out.println("Best Solution: " + bestRRHC.getTour());
		System.out.println("TOP Fitness = " + bestRRHC.getFitness());
		percentage = (optimumFitness/ bestRRHC.fitnessFunction(distance))*100;//Outputs percentage efficiency for OptimalFitness
		System.out.println("Percentage efficiency OPT: " + percentage +"%");
		percentage = (MSTLength/ bestRMHC.fitnessFunction(distance))*100; //Displays Percentage efficiency for Minimum spanning tree
		System.out.println("Percentage efficiency MST: " + percentage +"%");
		
		System.out.println("-----------");
		System.out.println("Simulated Annealing:");
		TSPSolutions bestSA = SA(10000,distance,false,0.99,10000);
		System.out.println("Best Solution: " + bestSA.getTour());
		System.out.println("TOP Fitness = " + bestSA.getFitness());
		percentage = (optimumFitness/ bestSA.fitnessFunction(distance))*100;//Outputs percentage efficiency for OptimalFitness
		System.out.println("Percentage efficiency OPT: " + percentage +"%");
		percentage = (MSTLength/ bestRMHC.fitnessFunction(distance))*100; //Displays Percentage efficiency for Minimum spanning tree
		System.out.println("Percentage efficiency MST: " + percentage +"%");

	}
	
	/*
	 * Random Mutation Hill Climbing
	 * @param iter = Number of Iterations e.g 10, 100, 1000 ect
	 * @param distances = The distances of the cites 
	 * @param print = print method 
	 * @return Best Solution
	 */
	private static TSPSolutions RMHC(int iter, double[][] distances, boolean print)
	{
		int n = distances.length;
		TSPSolutions solution = new TSPSolutions(n);//create random solution
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			
			
			TSPSolutions oldsol = new TSPSolutions(solution.getTour(),n); //This will copy current solution into old solution
			double f1 = oldsol.fitnessFunction(distances); //evaluate first fitness within the loop
			
			solution.smallChange(); //makes a small change in solution
			
			TSPSolutions newsol = new TSPSolutions(solution.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//if the new fitness is worse than the old, then keep the old solution
			if (f2 > f1)
			{
				solution=oldsol;
			}
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
		}
		
		return(solution);//This returns the current solution in its best form
	}
	
	/**
	 * Stochastic Hill Climbing 
	 * @param iter = No of Iteration 10,100,1000, ect.
	 * @param distances = distances for the cities 
	 * @param print = print out information to see process
	 * @return Best solution
	 */
	private static TSPSolutions SHC(int iter, double[][] distances, boolean print)
	{
		int n = distances.length;
		TSPSolutions solution = new TSPSolutions(n);//create random solution
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			TSPSolutions oldsol = new TSPSolutions(solution.getTour(),n); //copies sol into oldsol
			double f1 = oldsol.fitnessFunction(distances); //evaluate first fitness within the loop
			
			solution.smallChange(); //makes a small change in sol
			
			TSPSolutions newsol = new TSPSolutions(solution.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//if the new fitness is worse than the old, then keep the old solution
			
            /*
            if a random number is > than the probability acceptance then the probability is
            not good enough and we keep the oldFitness
            However probabilty acceptance is 0.6 and random is 0.7 which would mean we would go back to
            old solution and therefore accept the worse solution
            */
			if(CS2004.UR(0, 1) > PRAccept(f2,f1,95)) {
				solution=oldsol;
			}
			
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
		}
	
		return(solution);//This returns the current solution that is being computed
	}
	
	
      // We accept a new solution according to the equation below, which allows us to find the T value:
	  //Pr(accept) = 1/1+e^(f'-f)/T*
	  
	 //f' = new fitness
	  //f = old fitness
	 //T is parameter ( Set to 25)   
	
	
	private static double PRAccept(double newFitness, double oldFitness, int t) {
		
		return 1.0/(1.0+Math.exp((newFitness-oldFitness)/t));
	}
	
	/**
	 * Random Restart Hill Climbing
	 * @param iter = number of iterations
	 * @param restart = restart from a different section of the search space
	 * @param distances = distances for the cities
	 * @param print = print out information to see process
	 * @return Best Solution
	 */
	
	private static TSPSolutions RRHC (int iter, int restart, double[][] distances, boolean print) {
		ArrayList<TSPSolutions> Sol = new ArrayList<>();
		for (int i=1; i<=restart; i++){
			Sol.add(RMHC(iter,distances,print));
		}
		
		return bestSol(Sol);
	}
	
	private static TSPSolutions bestSol(ArrayList<TSPSolutions> solution) {
		ArrayList<Double> fitness = new ArrayList<>();
		
		for(int i = 0; i < solution.size(); i++) {
			fitness.add(solution.get(i).getFitness());
		}
		
		int min = fitness.indexOf(Collections.min(fitness));
		
		return solution.get(min);
	}
	
	/**
	 * Simulated Annealing 
	 * @param iter
	 * @param distances
	 * @param print
	 * @param coolingrate- cooling values down slowly to get best solution
	 * @param temp
	 * @return Best solution 
	 */
	private static TSPSolutions SA(int iter, double[][] distances, boolean print, double coolingrate, double temp)
	{
		int n = distances.length;
		TSPSolutions solution = new TSPSolutions(n);//create random solution
		
		double currentTemp = temp;
		for (int i = 1; i <= iter; i++) //creating a for loop, iterates for specified number of iterations
		{
			
			
			TSPSolutions oldsol = new TSPSolutions(solution.getTour(),n); //copies sol into oldsol
			double f1 = oldsol.fitnessFunction(distances); //evaluate first fitness within the loop
			
			solution.smallChange(); //makes a small change in sol
			
			TSPSolutions newsol = new TSPSolutions(solution.getTour(),n);
			
			double f2 = newsol.fitnessFunction(distances); //evaluates new fitness to another variable

			//if the new fitness is worse than the old, then keep the old solution
			if (f2 > f1)
			{
				double p = PR(f2,f1,currentTemp);
				
				if(p<CS2004.UR(0, 1)) {
					solution=oldsol;
				}
				else
					solution=newsol;
			}
			if(print) {
				System.out.println("Iteration " + i + ": " +" Fitness: " + f1);
			}
			
			currentTemp *= coolRate(temp, i);
		}
		
		return(solution);//This returns best current solution
	}
	
	private static double coolRate(double initialTemp, int iter) {
	    return Math.exp((Math.log(Math.pow(10,-100))-Math.log(initialTemp))/iter); //figure out the landar 
    }
	
	
	/*Input:	T0	Starting temperature
	Iter	Number of iterations
	cl 		The cooling rate
	1)	Let x = a random solution
	2)	For i = 0 to Iter-1
	3)		Let f = fitness of x
	4)		Make a small change to x to make x’
	5)		Let f’ = fitness of new point x’
	6)		If f’ is worse than f Then
	7)			Let p = PR(f’,f,Ti)
	8)			If p < UR(0,1) Then
	9)				Reject change (keep x and f)
	10)			Else
	11)				Accept change (keep x’ and f’)
	12)			End If
	13)		Else
	14)			Let x = x’
	15)		End If
	16)		Let Ti+1 = clTi
	17)	End For
	Output:	The solution x*/
	
	
	
	
	
	/**
	 * 
	 * @param newFitness
	 * @param oldFitness
	 * @param currentTemperature
	 * @return PR- probability 
	 * 	 */
	
	private static double PR(double newFitness, double oldFitness, double currentTemperature) {
		
		double changeOfFitness = newFitness - oldFitness;
		
		return Math.exp(-changeOfFitness/currentTemperature);
		
		
	}
}
	


	


