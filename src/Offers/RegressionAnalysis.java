package Offers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegressionAnalysis {

	// Date currentTime;
	Date buyerDeadline;
	double buyerInitPrice;
	double buyerReservePrice;
	double sellerInitPrice;

	public String formatDate(Date date) {
		date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdfDate.format(date);
	}

	

	// random reservation point

	double randomPrice;
	Date randomDate;

	// real reservation point

	double realPrice;
	Date realDate;

	ArrayList<Offer> hisOffers = new ArrayList<Offer>();

	public double findAvg(ArrayList<Offer> allOffres) {
		double sum = 0;
		double avg = 0;

		for (int i = 0; i < allOffres.size(); i++) {
			sum += allOffres.get(i).offerPrice;
		}

		avg = sum / allOffres.size();

		return avg;
	}
// call findGamma at each round of negotiation
	public Cell[] FindGamma(int noOfCells, int rounds, Date currentTime,
			double sellerInitPrice, double currentPrice) {
		
		Cell allCells[] = new Cell[noOfCells];
		float tpSum = 0;
		float t2Sum = 1;
		double avgHistory = findAvg(hisOffers);

		for (int i = 0; i < allCells.length; i++) {
			
				for (int j = 1; j <= rounds; j++) {

					float tStar = (float) Math.log(currentTime.getTime()
							/ allCells[i].cellDeadlineDate.getTime());
					float pStar = (float) Math
							.log((sellerInitPrice - currentPrice)
									/ (sellerInitPrice - allCells[i].cellReservePrice));

					tpSum += tStar * pStar;
					t2Sum += Math.pow(tStar, 2);

				}
				
				
				////hadhgdfhdmcsw
				float b = tpSum / t2Sum;

				float offer = (float) (sellerInitPrice + (allCells[i].cellReservePrice - sellerInitPrice)
						* Math.pow(
								(currentTime.getTime() / allCells[i].cellDeadlineDate
										.getTime()), b));
				Offer offerObj = new Offer();
				offerObj.roundNo = rounds;   //current round no
				offerObj.offerPrice = offer;
				allCells[i].fittedOffers.add(offerObj);// array of fitted offers in each cell
			
				
				double avgFitted = findAvg(allCells[i].fittedOffers);
				double sumUP=0.0;
				double sumDown1=0.0;
				double sumDown2=0.0;
				for (int round = 1; round <= rounds; round++) {
				 sumUP += (currentPrice - avgHistory)
							* (allCells[i].cellReservePrice - avgFitted);
				 sumDown1 += Math.pow((currentPrice - avgHistory), 2);

				}
				for (int cells = 0; cells < allCells.length; cells++) {
					sumDown2 += Math.pow(allCells[i].cellReservePrice - avgFitted, 2); 
							
				}
				double foundGamma= sumUP/Math.sqrt(sumDown1*sumDown2);
				allCells[i].gamma=foundGamma; // ?? get set methods
				
				System.out.println(" Cell "+i+" "+foundGamma);
			}
	
	return allCells;

	}

	class Region {
		// estimations for sellers private information
		double lowerPrice; // lower and upper boundary of opponent's reserve
							// price
		double upperPrice;
		Date lowerDate; // lower and upper boundary of opponent's deadline
		Date upperDate;

		public Region() {

		}

		public Region(double lowerPrice, double upperPrice, Date lowerDate,
				Date upperDate) {
			this.lowerPrice = lowerPrice;
			this.upperPrice = upperPrice;
			this.lowerDate = lowerDate;
			this.upperDate = upperDate;
		}
	}

	class Cell {
		// detecting cell
		double cellLowerPrice; // lower and upper boundary of price in cell
		double cellUpperPrice;
		Date cellLowerDate; // lower and upper boundary of time in cell
		Date cellUpperDate;
		double cellReservePrice;
		Date cellDeadlineDate;
		float probability;
		double gamma;

		ArrayList<Offer> fittedOffers = new ArrayList<Offer>();

		public Cell() {

		}

		public Cell(double cellLowerPrice, double cellUpperPrice,
				Date cellLowerDate, Date cellUpperDate) {

			this.cellLowerPrice = cellLowerPrice;
			this.cellUpperPrice = cellUpperPrice;
			this.cellLowerDate = cellLowerDate;
			this.cellUpperDate = cellUpperDate;
		}

	}

	class Offer {
		int roundNo;
		double offerPrice;

		public Offer() {

		}

		public Offer(int roundNo, double offerPrice) {
			this.roundNo = roundNo;
			this.offerPrice = offerPrice;
		}
	}

}
