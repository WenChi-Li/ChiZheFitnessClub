package tw.mygym.model.bean;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainingrecords")
@Component
public class TrainingRecordsBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="trainingid")
	private int trainingID; 

	@ManyToOne
	@JoinColumn(name = "trainingtitleid", nullable = false)
	private TrainingRecordTitleBean trainingRecordTitle;
	
	@Column(name="exercisetype")
	private String exerciseType;
	
	@Column(name="trainingweight")
	private BigDecimal trainingWeight;
	
	@Column(name="repetitions")
	private Integer repetitions;
	
	@Column(name="trainingsets")
	private Integer trainingSets;
	
	@Column(name="totaltrainingvolume", insertable = false, updatable = false)
	private Integer totalTrainingVolume;

	public int getTrainingID() {
		return trainingID;
	}

	public void setTrainingID(int trainingID) {
		this.trainingID = trainingID;
	}

	public TrainingRecordTitleBean getTrainingRecordTitle() {
		return trainingRecordTitle;
	}

	public void setTrainingRecordTitle(TrainingRecordTitleBean trainingRecordTitle) {
		this.trainingRecordTitle = trainingRecordTitle;
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

	@Override
	public String toString() {
		return "TrainingRecordsBean [trainingID=" + trainingID + ", exerciseType=" + exerciseType + ", trainingWeight="
				+ trainingWeight + ", repetitions=" + repetitions + ", trainingSets=" + trainingSets
				+ ", totalTrainingVolume=" + totalTrainingVolume + "]";
	}

	public TrainingRecordsBean(int trainingID, TrainingRecordTitleBean trainingRecordTitle, String exerciseType,
			BigDecimal trainingWeight, Integer repetitions, Integer trainingSets, Integer totalTrainingVolume) {
		super();
		this.trainingID = trainingID;
		this.trainingRecordTitle = trainingRecordTitle;
		this.exerciseType = exerciseType;
		this.trainingWeight = trainingWeight;
		this.repetitions = repetitions;
		this.trainingSets = trainingSets;
		this.totalTrainingVolume = totalTrainingVolume;
	}

	public TrainingRecordsBean(TrainingRecordTitleBean trainingRecordTitle, String exerciseType,
			BigDecimal trainingWeight, Integer repetitions, Integer trainingSets, Integer totalTrainingVolume) {
		super();
		this.trainingRecordTitle = trainingRecordTitle;
		this.exerciseType = exerciseType;
		this.trainingWeight = trainingWeight;
		this.repetitions = repetitions;
		this.trainingSets = trainingSets;
		this.totalTrainingVolume = totalTrainingVolume;
	}

	public TrainingRecordsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
