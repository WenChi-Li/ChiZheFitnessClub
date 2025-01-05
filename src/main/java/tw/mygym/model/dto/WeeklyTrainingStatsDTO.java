package tw.mygym.model.dto;

import java.util.Date;

public class WeeklyTrainingStatsDTO {
	private int weekOfYear; // 第幾周
    private Date startDate; // 周的開始日期
    private Date endDate;   // 周的結束日期
    private long trainingDays; // 每周訓練天數
    private double totalVolume; // 每周訓練總量
	public int getWeekOfYear() {
		return weekOfYear;
	}
	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getTrainingDays() {
		return trainingDays;
	}
	public void setTrainingDays(long trainingDays) {
		this.trainingDays = trainingDays;
	}
	public double getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}
	public WeeklyTrainingStatsDTO(int weekOfYear, Date startDate, Date endDate, long trainingDays, double totalVolume) {
		super();
		this.weekOfYear = weekOfYear;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainingDays = trainingDays;
		this.totalVolume = totalVolume;
	}
	public WeeklyTrainingStatsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
    

    
    
}
