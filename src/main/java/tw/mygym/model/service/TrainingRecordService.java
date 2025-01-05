package tw.mygym.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.mygym.exception.ResourceNotFoundException;
import tw.mygym.model.bean.GymUSerProfilesBean;
import tw.mygym.model.bean.TrainingRecordTitleBean;
import tw.mygym.model.bean.TrainingRecordsBean;
import tw.mygym.model.dto.CreateTrainingRecordDTO;
import tw.mygym.model.dto.MonthlyTrainingStatsDTO;
import tw.mygym.model.dto.WeeklyTrainingStatsDTO;
import tw.mygym.model.repository.GymUSerProfilesRepository;
import tw.mygym.model.repository.TrainingRecordTitleRepository;
import tw.mygym.model.repository.TrainingRecordsRepository;

@Service
@Transactional
public class TrainingRecordService {

	@Autowired
	private TrainingRecordsRepository trainingRecordRepository;
    
    @Autowired
	private TrainingRecordTitleRepository trainingRecordTitleRepository;
    
    @Autowired
    private GymUSerProfilesRepository gymUSerProfilesRepository;
	
	
	
//  新增訓練內容
  public CreateTrainingRecordDTO insertRecord(TrainingRecordsBean bean,int trainingTitleID) {
  	
      TrainingRecordTitleBean title = trainingRecordTitleRepository.findById(trainingTitleID)
              .orElseThrow(() -> new ResourceNotFoundException("找不到對應的訓練標題 ID: " + trainingTitleID));

      // 設定外鍵 TrainingRecordTitle
      bean.setTrainingRecordTitle(title);
	  
	  TrainingRecordsBean savedRecord = trainingRecordRepository.save(bean);
      return createTrainingRecordDTO(savedRecord);
  
  }
  

//	查詢訓練標題下的所有訓練內容
  public List<TrainingRecordsBean> findRecordsByTitle(int trainingTitleID) {
      TrainingRecordTitleBean title = trainingRecordTitleRepository.findById(trainingTitleID)
              .orElseThrow(() -> new ResourceNotFoundException("找不到對應的訓練標題 ID: " + trainingTitleID));
      return trainingRecordRepository.findByTrainingRecordTitle(title);
  }
  
//  刪除訓練內容
	public void deleteById(Integer id) {
		try {
			trainingRecordRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("查無此訓練內容 ID：" + id);
		}
	}
	
//	查詢每月訓練容積統計(用id找)
//	public MonthlyTrainingStatsDTO getMonthlyStats(String month, int userId) {
//        List<Object[]> rawResults = trainingRecordRepository.findMonthlyStatsRaw(month, userId);
//
//        if (!rawResults.isEmpty()) {
//            Object[] row = rawResults.get(0);
//            Long trainingDays = row[0] != null ? ((Number) row[0]).longValue() : 0L;
//            Double totalVolume = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
//
//            return new MonthlyTrainingStatsDTO(trainingDays, totalVolume);
//        }
//
//        return new MonthlyTrainingStatsDTO(0L, 0.0);
//    }
	
//	查詢這個月訓練容積統計(用Authentication找)
//	public MonthlyTrainingStatsDTO getMonthlyStats(String month, Authentication authentication) {
//	    String emailAddress = authentication.getName(); 
//	    
//	    // 通過 emailAddress 取得使用者資料
//	    Optional<GymUSerProfilesBean> userOptional = gymUSerProfilesRepository.findByEmailAddress(emailAddress);
//	    if (userOptional.isEmpty()) {
//	        throw new ResourceNotFoundException("找不到使用者信箱: " + emailAddress);
//	    }
//	    GymUSerProfilesBean user = userOptional.get();
//	    int userId = user.getId(); // 使用 getId() 方法取得 userId
//
//	    // 獲取統計數據
//	    List<Object[]> rawResults = trainingRecordRepository.findMonthlyStatsRaw(month, userId);
//
//	    if (!rawResults.isEmpty()) {
//	        Object[] row = rawResults.get(0);
//	        Long trainingDays = row[0] != null ? ((Number) row[0]).longValue() : 0L;
//	        Double totalVolume = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
//
//	        return new MonthlyTrainingStatsDTO(trainingDays, totalVolume);
//	    }
//	    // 如果沒有資料，返回空的統計資料
//	    return new MonthlyTrainingStatsDTO(0L, 0.0);
//	}
	
//	查詢每月訓練容積統計(用Authentication找，改進版)
	public MonthlyTrainingStatsDTO getMonthlyStats(String month, Authentication authentication) {
	    // 從 Authentication 中獲取 emailAddress
	    String emailAddress = authentication.getName(); 
	    
	    // 通過 emailAddress 獲取用戶資料
	    GymUSerProfilesBean user = gymUSerProfilesRepository.findByEmailAddress(emailAddress)
	    	    .orElseThrow(() -> new ResourceNotFoundException("找不到使用者信箱:" + emailAddress));

	    int userId = user.getId(); // 使用 getId() 方法獲取 userId

	    // 獲取統計數據
	    List<Object[]> rawResults = trainingRecordRepository.findMonthlyStatsRaw(month, userId);

	    if (!rawResults.isEmpty()) {
	        Object[] row = rawResults.get(0);
	        Long trainingDays = row[0] != null ? ((Number) row[0]).longValue() : 0L;
	        Double totalVolume = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;

	        return new MonthlyTrainingStatsDTO(trainingDays, totalVolume);
	    }
	    // 如果沒有資料，返回空的統計資料
	    return new MonthlyTrainingStatsDTO(0L, 0.0);
	}	
	
	
//	查詢每周訓練容積，作曲線圖
	 // 每周訓練統計
    public List<WeeklyTrainingStatsDTO> getWeeklyStats(int year, Authentication authentication) {
        // 從 Authentication 中獲取 emailAddress
        String emailAddress = authentication.getName();

        // 通過 emailAddress 獲取用戶資料
        GymUSerProfilesBean user = gymUSerProfilesRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new ResourceNotFoundException("找不到使用者信箱: " + emailAddress));

        int userId = user.getId(); // 使用 getId() 方法獲取 userId

        // 從 Repository 獲取數據
        List<Object[]> rawResults = trainingRecordRepository.findWeeklyStats(year, userId);

        // 將原始數據轉換為 DTO
        return rawResults.stream()
                .map(row -> new WeeklyTrainingStatsDTO(
                        (int) row[0], // weekOfYear
                        (Date) row[1], // startDate
                        (Date) row[2], // endDate
                        row[3] != null ? ((Number) row[3]).longValue() : 0L, // trainingDays
                        row[4] != null ? ((Number) row[4]).doubleValue() : 0.0 // totalVolume
                ))
                .toList();
    }
	
	
	
//	封裝一個DTO方法 "新增訓練內容" ，讓前端與DTO傳輸與回傳
	private CreateTrainingRecordDTO createTrainingRecordDTO(TrainingRecordsBean record) {
		CreateTrainingRecordDTO dto = new CreateTrainingRecordDTO();
		
        dto.setTrainingID(record.getTrainingID());
        dto.setExerciseType(record.getExerciseType());
        dto.setTrainingWeight(record.getTrainingWeight());
        dto.setRepetitions(record.getRepetitions());
        dto.setTrainingSets(record.getTrainingSets());
        dto.setTotalTrainingVolume(record.getTotalTrainingVolume());
        return dto;
	}
	
	
}
