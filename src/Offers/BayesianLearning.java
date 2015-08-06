package Offers;

import java.util.Date;

import Offers.RegressionAnalysis.Cell;

public class BayesianLearning {

	public void updateprobability(int rows, int columns, Cell[][] detReg) {

		float sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				sum += detReg[i][j].probability * detReg[i][j].gamma;
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				detReg[i][j].probability = (float) ((detReg[i][j].probability * detReg[i][j].gamma) / (sum));
			}

		}
	}

	public void adaptiveConcessionStrategy(int rows, int columns,
			Cell[][] detReg) {
		double buyerInitPrice = 0;
		Date buyerInitTime = null;
		double buyerCurrentOffer = 0; // b0
		double buyerNewOffer;
		double buyerReserveOffer = 0;
		Date buyerDeadline = null;
		double concessionPrice = 0;
		Date concessionTime = null;
		Date currentTime= new Date();
		double beta = 0;
		double betaHat;
		double betaHatSum = 0;
		double betaBar;
		double sumDown = 0;
		// Optimal concession strategy
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				// Scenario 1

				if ((detReg[i][j].cellDeadlineDate.getTime() < buyerDeadline
						.getTime())
						&& (detReg[i][j].cellReservePrice > buyerCurrentOffer)) {
					
				concessionPrice= detReg[i][j].cellReservePrice;
				concessionTime= detReg[i][j].cellDeadlineDate;

				}
				// Scenario 2

				else if ((detReg[i][j].cellDeadlineDate.getTime() >= buyerDeadline
						.getTime())
						&& (detReg[i][j].cellReservePrice >= buyerCurrentOffer)) {
					//case 1: line l1
					//case 2:line l2
				}
				// Scenario 3

				else if ((detReg[i][j].cellDeadlineDate.getTime() < buyerDeadline
						.getTime())
						&& (detReg[i][j].cellReservePrice < buyerCurrentOffer)) {

				}
				// Scenario 4
				else if ((detReg[i][j].cellDeadlineDate.getTime() >= buyerDeadline
						.getTime())
						&& (detReg[i][j].cellReservePrice <= buyerCurrentOffer)) {

				} else {

				}
				// Combining mechanism
				//t>t0
				buyerNewOffer= buyerInitPrice + (buyerReserveOffer-buyerInitPrice)*Math.pow(((currentTime.getTime()-buyerInitTime.getTime())/(buyerDeadline.getTime()-buyerInitTime.getTime())),beta);
				
				float base= (concessionTime.getTime()-buyerInitTime.getTime())/(buyerDeadline.getTime()-buyerInitTime.getTime());
				double num= (buyerInitPrice-concessionPrice)/(buyerInitPrice-buyerReserveOffer); // t0<tp<Tb
				betaHat= logOfBase(base, num);
				
				betaHatSum +=betaHatSum;
				sumDown += (detReg[i][j].probability)/(1+betaHat); 
			}		
					
		}
		betaBar= (1/sumDown)-1;
		beta=betaBar;
	
	}
	public double logOfBase(float base, double num) {
	    return Math.log(num) / Math.log(base);
	}
}
