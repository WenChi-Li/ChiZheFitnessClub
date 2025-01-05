package tw.mygym.model.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "gymuserprofiles")
@Component
public class GymUSerProfilesBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid")
	private int id;

	
	/* ******************************************************************************
	 * 									使用工具觀念										*
	 * ******************************************************************************
	 * mappedBy = "user"															*
	 * 		表示關聯是由 TrainingRecordsBean 中的 user 屬性維護的（即 @ManyToOne 的 user 屬性）。	*
	 * 		mappedBy 屬性避免了雙向關聯中不必要的額外表或外鍵。										*
	 * 																				*
	 * cascade = CascadeType.ALL													*
	 * 		指定了當對 GymUSerProfilesBean 執行操作時，自動對相關的 TrainingRecordsBean 執行對應操作。*
	 *		新增：新增一個使用者時，可以一併新增其訓練記錄。											*
	 *		刪除：刪除一個使用者時，會自動刪除相關的訓練記錄。											*
	 * fetch = FetchType.LAZY														*
	 * 		預設只在需要時才載入訓練記錄														*
	 * @JsonManagedReference														*
	 *		在序列化時，這一方會顯示關聯的另一方數據。												*
	 * @@JsonBackReference															*
	 * 		在序列化時，這一方的關聯數據將被忽略。													*
	 * @JsonIgnore																	*
	 * 		忽略屬性，完全不序列化。															*
	 * 		需要隱藏或屏蔽的屬性使用。	
	 * @JsonIgnoreProperties(value = {"user"})
	 * 		忽略指定的bean，防止無限序列化。											*
	 * 																				*
	 * ******************************************************************************/
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
//	@JsonManagedReference
	private List<TrainingRecordTitleBean> trainingRecordTitle;
	

	@Column(name="username")
	private String userName;
	
	@Column(name="emailaddress")
	private String emailAddress;

	@Column(name="userpassword")
	private String userPassword;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mma")
	@Column(name="registrationdate")
	private Date registrationDate;
		
	@Column(name="userphone")
	private String userPhone;
	
	@Column(name="useraddress")
	private String userAddress;
	
	@Column(name="gender")
	private String gender;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birthdate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;
	
	@Column(name="emergencycontactname")
	private String emergencyContactName;
	
	@Column(name="emergencycontactphone")
	private String emergencyContactPhone;
	
		

	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}

	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	
	public List<TrainingRecordTitleBean> getTrainingRecordTitle() {
		return trainingRecordTitle;
	}

	public void setTrainingRecords(List<TrainingRecordTitleBean> trainingRecordTitle) {
		this.trainingRecordTitle = trainingRecordTitle;
	}

/************************************************************
 *	toString()方法											*
 *	除錯與日誌：在除錯或記錄日誌時，您可以快速查看物件的狀態。					*
 *	輸出顯示：如果您需要將物件資訊顯示在介面或控制台中。						*
 *	範例： 假設您正在除錯一個註冊功能，您可以直接將Bean物件列印出來查看其內容：		*
 *	GymUSerProfilesBean profile = new GymUSerProfilesBean();*
 *	設置一些屬性													*
 *	System.out.println(profile);  // 這將自動調用toString()方法 
 *
 * 注意:使用關聯式時要把toString部分拔掉，不然會造成StackOverflow error	*
 ************************************************************/
	@Override
	public String toString() {
		return "GymUSerProfilesBean [id=" + id + ", userName=" + userName + ", emailAddress=" + emailAddress
			+ ", userPassword=" + userPassword + ", registrationDate=" + registrationDate + ", userPhone=" + userPhone
			+ ", userAddress=" + userAddress + ", gender=" + gender + ", birthdate=" + birthdate
			+ ", emergencyContactName=" + emergencyContactName + ", emergencyContactPhone=" + emergencyContactPhone
			+ "]";
	}
	
/********************************************************************************
 * 構造函數																		*
 * 物件初始化：在創建物件時，可以快速初始化物件的屬性。												*
 * 依賴注入：Spring等框架通常會利用構造函數來進行依賴注入。										*
 * 範例： 假設有一個服務層需要創建新的使用者資料，可以使用構造函數來簡化這個過程：							*
 * GymUSerProfilesBean newUser = new GymUSerProfilesBean(						*
 * "JohnDoe", "john@example.com", "password123", new Date(), 					*
 * "123456789", "123 Main St", "Male", new Date(), "Jane Doe", 					*
 * "987654321"); 																*
 * 																				*
 * 什麼情況下可以不寫？																	*
 * 單純存儲資料：如果您的Bean只是單純存儲數據且不涉及到復雜的操作或框架依賴，可以省略一些方法，但這會降低代碼的靈活性。	*
 * 小型測試應用：對於小型或臨時測試應用，您可以簡化Bean的結構，但在生產環境中不推薦這樣做。						*
 * ******************************************************************************/
	

	public GymUSerProfilesBean(int id, String userName, String emailAddress, String userPassword, Date registrationDate,
		String userPhone, String userAddress, String gender, Date birthdate, String emergencyContactName,
		String emergencyContactPhone, List<TrainingRecordTitleBean> trainingRecordTitle) {
	super();
	this.id = id;
	this.userName = userName;
	this.emailAddress = emailAddress;
	this.userPassword = userPassword;
	this.registrationDate = registrationDate;
	this.userPhone = userPhone;
	this.userAddress = userAddress;
	this.gender = gender;
	this.birthdate = birthdate;
	this.emergencyContactName = emergencyContactName;
	this.emergencyContactPhone = emergencyContactPhone;
	this.trainingRecordTitle = trainingRecordTitle;
}
	


	public GymUSerProfilesBean(String userName, String emailAddress, String userPassword, Date registrationDate,
		String userPhone, String userAddress, String gender, Date birthdate, String emergencyContactName,
		String emergencyContactPhone, List<TrainingRecordTitleBean> trainingRecordTitle) {
	super();
	this.userName = userName;
	this.emailAddress = emailAddress;
	this.userPassword = userPassword;
	this.registrationDate = registrationDate;
	this.userPhone = userPhone;
	this.userAddress = userAddress;
	this.gender = gender;
	this.birthdate = birthdate;
	this.emergencyContactName = emergencyContactName;
	this.emergencyContactPhone = emergencyContactPhone;
	this.trainingRecordTitle = trainingRecordTitle;
}

	public GymUSerProfilesBean() {
		super();
		// TODO Auto-generated constructor stub
	}


}
