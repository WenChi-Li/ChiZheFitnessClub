package tw.mygym.model.dto;

import java.math.BigDecimal;

public class CreateTrainingRecordDTO {

	private int trainingID;
	private String exerciseType;
	private BigDecimal trainingWeight;
	private Integer repetitions;
	private Integer trainingSets;
	private Integer totalTrainingVolume;
	
	
	public int getTrainingID() {
		return trainingID;
	}
	public void setTrainingID(int trainingID) {
		this.trainingID = trainingID;
	}
	public String getExerciseType() {
		return exerciseType;
	}
	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}
	public BigDecimal getTrainingWeight() {
		return trainingWeight;
	}
	public void setTrainingWeight(BigDecimal trainingWeight) {
		this.trainingWeight = trainingWeight;
	}
	public Integer getRepetitions() {
		return repetitions;
	}
	public void setRepetitions(Integer repetitions) {
		this.repetitions = repetitions;
	}
	public Integer getTrainingSets() {
		return trainingSets;
	}
	public void setTrainingSets(Integer trainingSets) {
		this.trainingSets = trainingSets;
	}
	public Integer getTotalTrainingVolume() {
		return totalTrainingVolume;
	}
	public void setTotalTrainingVolume(Integer totalTrainingVolume) {
		this.totalTrainingVolume = totalTrainingVolume;
	}
	
	
}
