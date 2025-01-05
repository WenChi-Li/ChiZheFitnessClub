package tw.mygym.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateTrainingRecordTitleDTO {

	 private int trainingTitleID;
	 private String trainingTitle;
	 
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 private Date trainingDate;
	 
	 private List<CreateTrainingRecordDTO> trainingRecords; 
	 

	public int getTrainingTitleID() {
		return trainingTitleID;
	}
	public void setTrainingTitleID(int trainingTitleID) {
		this.trainingTitleID = trainingTitleID;
	}
	public Date getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	public String getTrainingTitle() {
		return trainingTitle;
	}
	public void setTrainingTitle(String trainingTitle) {
		this.trainingTitle = trainingTitle;
	}
	public List<CreateTrainingRecordDTO> getTrainingRecords() {
		return trainingRecords;
	}
	public void setTrainingRecords(List<CreateTrainingRecordDTO> trainingRecords) {
		this.trainingRecords = trainingRecords;
	}

	    
	    
	    
}
