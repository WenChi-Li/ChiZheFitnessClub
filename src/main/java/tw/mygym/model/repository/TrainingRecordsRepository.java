package tw.mygym.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.mygym.model.bean.GymUSerProfilesBean;
import tw.mygym.model.bean.TrainingRecordTitleBean;
import tw.mygym.model.bean.TrainingRecordsBean;
import tw.mygym.model.dto.DailyTrainingStatsDTO;
import tw.mygym.model.dto.MonthlyTrainingStatsDTO;

public interface TrainingRecordsRepository extends JpaRepository<TrainingRecordsBean, Integer> {

    // 根据訓練器材類型找相關紀錄，用於統計
    List<TrainingRecordsBean> findByExerciseType(String exerciseType);

    // 根据訓練器材類型與訓練標題找相關紀錄，用於統計
    List<TrainingRecordsBean> findByExerciseTypeAndTrainingRecordTitle(String exerciseType, TrainingRecordTitleBean trainingRecordTitle);

    // 根据訓練標題搜尋所有的訓練紀錄
    List<TrainingRecordsBean> findByTrainingRecordTitle(TrainingRecordTitleBean trainingRecordTitle);

 // 每日訓練統計(目前沒用到)
    @Query("SELECT new tw.mygym.model.dto.DailyTrainingStatsDTO(r.trainingRecordTitle.trainingDate, SUM(r.totalTrainingVolume)) " +
    	       "FROM TrainingRecordsBean r " +
    	       "WHERE r.trainingRecordTitle.user = :user AND r.trainingRecordTitle.trainingDate = :date " +
    	       "GROUP BY r.trainingRecordTitle.trainingDate")
    List<DailyTrainingStatsDTO> findDailyStats(@Param("date") Date date, @Param("user") GymUSerProfilesBean user);

//  每月訓練統計
    @Query(value = "SELECT " +
            "COUNT(DISTINCT CONVERT(DATE, T.trainingDate)) AS trainingDays, " +
            "SUM(R.totalTrainingVolume) AS totalVolume " +
            "FROM TrainingRecords R " +
            "INNER JOIN TrainingRecordTitle T ON R.trainingTitleID = T.trainingTitleID " +
            "WHERE T.userID = :userId " +
            "AND FORMAT(T.trainingDate, 'yyyy-MM') = :month",
           nativeQuery = true)
    List<Object[]> findMonthlyStatsRaw(@Param("month") String month, @Param("userId") int userId);

    
//  每周訓練統計
    @Query(value = "SELECT " +
            "DATEPART(WEEK, T.trainingDate) AS weekOfYear, " + // 獲取年份中的第幾周
            "MIN(T.trainingDate) AS startDate, " + // 周的開始日期
            "MAX(T.trainingDate) AS endDate, " + // 周的結束日期
            "COUNT(DISTINCT CONVERT(DATE, T.trainingDate)) AS trainingDays, " + // 每周訓練天數
            "SUM(R.totalTrainingVolume) AS totalVolume " + // 每周訓練總量
            "FROM TrainingRecords R " +
            "INNER JOIN TrainingRecordTitle T ON R.trainingTitleID = T.trainingTitleID " +
            "WHERE T.userID = :userId " + // 指定用戶
            "AND YEAR(T.trainingDate) = :year " + // 指定年份
            "GROUP BY DATEPART(WEEK, T.trainingDate) " + // 按周分組
            "ORDER BY weekOfYear ASC", // 按周排序
            nativeQuery = true)
    List<Object[]> findWeeklyStats(@Param("year") int year, @Param("userId") int userId);

    
    
    
}
