package Offers;

import java.util.Date;




import Offers.RegressionAnalysis.Cell;
import Offers.RegressionAnalysis.Region;

public class main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
			RegressionAnalysis bestGamma= new RegressionAnalysis();
			BayesianLearning updatedProbability= new BayesianLearning();
			// assgn reserved price for each cell?????
			double buyerBeliefOnItemPrice = 80.00;
			int rounds=0;
			double currentprice=100.00;
			Date currentTime= new Date();
			
			// detection region
			RegressionAnalysis.Region region= bestGamma.new Region();	
			region.lowerDate= currentTime;			
			//add 10 hours 36000000
			region.upperDate = new Date(region.lowerDate.getTime()+36000000);
			region.lowerPrice=buyerBeliefOnItemPrice/2;
			region.upperPrice=buyerBeliefOnItemPrice*2;
			
			// No of cells
			int columns=3;
			int rows=3;
			int allCells = columns * rows; // N
			
			Cell detReg [][]= new Cell [rows][columns];
			for(int i=0;i<rows;i++){
				for(int j=0;j<columns;j++){
					
			//		
					Date tempLowerDate= new Date(region.lowerDate.getTime()+ ((region.upperDate.getTime()-region.lowerDate.getTime())/columns)*i);
					Date tempUpperDate= new Date(region.lowerDate.getTime()+ ((region.upperDate.getTime()-region.lowerDate.getTime())/columns)*(i+1));
					detReg[i][j].cellLowerDate=tempLowerDate;
					detReg[i][j].cellUpperDate=tempUpperDate;
					
					detReg[i][j].cellLowerPrice=region.lowerPrice+((region.upperPrice-region.lowerPrice)/rows)*j; 
					detReg[i][j].cellUpperPrice=region.lowerPrice+((region.upperPrice-region.lowerPrice)/rows)*(j+1); 
					detReg[i][j].cellDeadlineDate= new Date((detReg[i][j].cellUpperDate.getTime() +detReg[i][j].cellLowerDate.getTime())/2);
					detReg[i][j].cellReservePrice=( detReg[i][j].cellLowerPrice + detReg[i][j].cellUpperPrice)/2;
					detReg[i][j].probability= 1/allCells;
				}
			}
			
			
			while(rounds<10){
			Cell [] cells= bestGamma.FindGamma(allCells, rounds,currentTime , 100.00, currentprice);
			
			for(int i=0;i<rows;i++){
				for(int j=0;j<columns;j++){
					detReg[i][j].fittedOffers= cells[i*rows+j].fittedOffers;
					detReg[i][j].gamma=cells[i*rows+j].gamma;
				}
			}
			currentprice =currentprice-10;
			rounds++;
			System.out.println("Round No: "+rounds);
			updatedProbability.updateprobability(rows, columns, detReg);
			
			}
	}

}
