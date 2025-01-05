package tw.mygym.model.dto;

public class MonthlyTrainingStatsDTO {

	private Long trainingDays;
    private Double totalVolume;
    
    
	public Long getTrainingDays() {
		return trainingDays;
	}
	public void setTrainingDays(Long trainingDays) {
		this.trainingDays = trainingDays;
	}
	public Double getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}
	public MonthlyTrainingStatsDTO(Long trainingDays, Double totalVolume) {
		super();
		this.trainingDays = trainingDays;
		this.totalVolume = totalVolume;
	}
	public MonthlyTrainingStatsDTO() {
		super();
	}
    
	
    
    
}
