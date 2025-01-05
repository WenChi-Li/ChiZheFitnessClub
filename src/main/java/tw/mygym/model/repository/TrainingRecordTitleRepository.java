package tw.mygym.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tw.mygym.model.bean.GymUSerProfilesBean;
import tw.mygym.model.bean.TrainingRecordTitleBean;

@Repository
public interface TrainingRecordTitleRepository extends JpaRepository<TrainingRecordTitleBean, Integer> {

//	用日期尋找所有訓練標題，一天可能有多筆資料，所以用List
	 List<TrainingRecordTitleBean> findByTrainingDateAndUser(Date trainingDate, GymUSerProfilesBean user);
	 
	 
//	 只查詢trainingTitleID 和 trainingDate，用於顯示只要有數據，就標記藍點
//	 @Query(value = "SELECT trainingtitleid AS trainingTitleID, trainingdate AS trainingDate FROM trainingrecordtitle", nativeQuery = true)
//	 List<Map<String, Object>> findAllTrainingDates();
	 
	 @Query(value = """
			    SELECT t.trainingtitleid AS trainingTitleID, t.trainingdate AS trainingDate
			    FROM TrainingRecordTitle t
			    INNER JOIN GymUSerProfiles u ON t.userID = u.userID
			    WHERE u.emailAddress = :emailAddress
			""", nativeQuery = true)
			List<Map<String, Object>> findAllTrainingDatesByEmailAddress(@Param("emailAddress") String emailAddress);

	 
	 
	 
//	 查詢指定月份的訓練天數
	 @Query(value = "SELECT COUNT(DISTINCT CONVERT(DATE, trainingDate)) AS trainingDays " +
             "FROM trainingrecordtitle " +
             "WHERE userID = :userId " +
             "AND FORMAT(trainingDate, 'yyyy-MM') = :month", 
             nativeQuery = true)
	 int countTrainingDaysByUserAndMonth(@Param("userId") int userId, @Param("month") String month);
	 
	 
}
