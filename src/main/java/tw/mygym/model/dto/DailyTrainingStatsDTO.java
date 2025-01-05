package tw.mygym.model.dto;

import java.util.Date;

public class DailyTrainingStatsDTO {

	private Date trainingDate;
    private double totalVolume;
	public Date getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	public double getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}
	public DailyTrainingStatsDTO(Date trainingDate, double totalVolume) {
		super();
		this.trainingDate = trainingDate;
		this.totalVolume = totalVolume;
	}
	public DailyTrainingStatsDTO() {
		super();
	}
    
    
 
	
}
