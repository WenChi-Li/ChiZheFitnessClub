package tw.mygym.model.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "trainingrecordtitle")
@Component
public class TrainingRecordTitleBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="trainingtitleid")
	private int trainingTitleID; 
	
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
//	@JsonIgnore
//	@JsonBackReference
//	@JsonIgnoreProperties(value = {"trainingRecords"})
	private GymUSerProfilesBean user;
		
	
	@OneToMany(mappedBy = "trainingRecordTitle", fetch = FetchType.LAZY)
	@JsonIgnore
//	@JsonManagedReference
	private List<TrainingRecordsBean> trainingRecords;	
	
	
	@Column(name="trainingtitle", nullable = false)
	private String trainingTitle;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="trainingdate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date trainingDate;


	public int getTrainingTitleID() {
		return trainingTitleID;
	}


	public void setTrainingTitleID(int trainingTitleID) {
		this.trainingTitleID = trainingTitleID;
	}


	public GymUSerProfilesBean getUser() {
		return user;
	}


	public void setUser(GymUSerProfilesBean user) {
		this.user = user;
	}


	public String getTrainingTitle() {
		return trainingTitle;
	}


	public void setTrainingTitle(String trainingTitle) {
		this.trainingTitle = trainingTitle;
	}


	public Date getTrainingDate() {
		return trainingDate;
	}


	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}
	
	
	
	public List<TrainingRecordsBean> getTrainingRecords() {
		return trainingRecords;
	}


	public void setTrainingRecords(List<TrainingRecordsBean> trainingRecords) {
		this.trainingRecords = trainingRecords;
	}


	@Override
	public String toString() {
		return "TrainingRecordTitleBean [trainingTitleID=" + trainingTitleID + ", trainingTitle=" + trainingTitle
				+ ", trainingDate=" + trainingDate + "]";
	}
	

	public TrainingRecordTitleBean(int trainingTitleID, GymUSerProfilesBean user,
			List<TrainingRecordsBean> trainingRecords, String trainingTitle, Date trainingDate) {
		super();
		this.trainingTitleID = trainingTitleID;
		this.user = user;
		this.trainingRecords = trainingRecords;
		this.trainingTitle = trainingTitle;
		this.trainingDate = trainingDate;
	}


	public TrainingRecordTitleBean(GymUSerProfilesBean user, List<TrainingRecordsBean> trainingRecords,
			String trainingTitle, Date trainingDate) {
		super();
		this.user = user;
		this.trainingRecords = trainingRecords;
		this.trainingTitle = trainingTitle;
		this.trainingDate = trainingDate;
	}


	public TrainingRecordTitleBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
