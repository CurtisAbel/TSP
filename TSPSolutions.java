import java.util.ArrayList;
import java.util.Collections;

public class TSPSolutions {
	
	private ArrayList<Integer> tour;
	private double fitness;
	
	public TSPSolutions(ArrayList<Integer> t, int noOfCities) {
		if(t == null) { //number of cities, it is null so that it always runs.
			tour = RandomPerm(noOfCities);
		} else {
			tour = (ArrayList<Integer>) t.clone();
		}
	}
	//Random starting point for the Tour

		//Algorithm1. RandomPerm(N)
		/**
		 * Pseudocode 
		 * Input: Number of cities N
		 * 1) Let P = list of length N, (|P|=N) where pi = i
		 * 2) Let T = an empty list
		 * 3) While |P| > 0
		 * 4)	Let i = UI(1,|P|)
		 * 5)	Add pi to the end of T
		 * 6) 	Delete the ith element (pi) from P
		 * 7) End While
		 * Output: Random tour T.
		 **/
	
	
	
	
	public TSPSolutions(int n) {
		tour = RandomPerm(n);
	}

	
	 // n = number of cities
	 
	private ArrayList<Integer> RandomPerm(int n) {
		
		tour = new ArrayList<>();
		
		//minus one because index starts from 0
		for (int i = 0; i <n - 1;i++ )
		{
			tour.add(i);
		}
		Collections.shuffle(tour); //Shuffle -- > Random permutation of a list
		return tour; //New combination of tour
		
		
	}
	

	
	public double fitnessFunction(double[][] distances) {
		double s = 0;
		for (int i = 0; i < tour.size()-1; i++)
		{
			
			int a = tour.get(i);
			int b = tour.get(i+1);
			s = s + distances[a][b]; //calculate route (Distance)
		}		
		Integer end_city = tour.get(tour.size()-1);
		Integer start_city = tour.get(0);
		s = s + distances[end_city][start_city]; //evaluating distance for a tour
		
		fitness = s;
		return s;	
	}
	
	/** TSP Fitness Function (f) Pseudocode

Input:	N The number of cities to visit
		T A tour (list of integers of size N) 
		D An N by N matrix containing each d(i,j)
1)		Let s = 0
2)		For i = 1 to (N-1)
3)			Let a = ti
4)			Let b = ti+1
5)			Let s = s + d(a,b)
6)		End For
7)		Let end_city = tn
8)		Let start_city = t1
9)		Let s = s + d(end_city,start_city)
Output:	The tour length s
	 **/

	
	
	
	public void smallChange() {
		ArrayList<Integer> tour2 = new  ArrayList<Integer>();
		tour2 = (ArrayList<Integer>)tour.clone();
		int b = CS2004.UI(0,tour2.size()-1);
		int c = CS2004.UI(0,tour2.size()-1);

		Collections.swap(tour, b, c);
	}
	
	
	//Algorithm 2. Small Change(T)
	/**
	 * Input: A tour (permutation) of size N				
	 * 1) Let i=j=0								//int i, j; i = 0; j = 0;
	 * 2) While i=j								//while(i == j)
	 * 3)	Let i = UI(1,|T|)					//i = UI(1,t.length());
	 * 4) 	Let j = UI(1,|T|)					//j = UI(1,t.length());
	 * 5)End While
	 * 6) Let j = UI(1,|T|)						//temp = t.get
	 * Output: Change tour T
	 * */
	
	public ArrayList<Integer> getTour() {
		return tour;
	}
	
	public double getFitness() {
		return fitness;
	}

	public double fitnessFunction(int length, double[][] distances) {
		// TODO Auto-generated method stub
		return 0;
	}

}


