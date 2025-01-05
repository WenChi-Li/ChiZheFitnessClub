package tw.mygym.model.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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
import tw.mygym.model.dto.CreateTrainingRecordTitleDTO;
import tw.mygym.model.repository.GymUSerProfilesRepository;
import tw.mygym.model.repository.TrainingRecordTitleRepository;
import tw.mygym.model.repository.TrainingRecordsRepository;

@Service
@Transactional
public class TrainingRecordTitleService {
	
	@Autowired
	private TrainingRecordTitleRepository trainingRecordTitleRepository;
	
	@Autowired
	private TrainingRecordsRepository trainingRecordsRepository;
	
    @Autowired
    private GymUSerProfilesRepository gymUserProfilesRepository;
	
    
    
//    新增訓練開頭
    public CreateTrainingRecordTitleDTO inserRecordTitle(TrainingRecordTitleBean bean,Authentication authentication) {
    	GymUSerProfilesBean user = getAuthenticatedUser(authentication);
    	System.out.println("找到的使用者: " + user);
    	
    	TrainingRecordTitleBean newTrainingRecords = new TrainingRecordTitleBean();	
    	newTrainingRecords.setTrainingTitle(bean.getTrainingTitle());
    	newTrainingRecords.setTrainingDate(bean.getTrainingDate());
    	// 根據@ManyToOne的結構，找到外鍵資料後，會直接提取資料ID(@JoinColumn(name = "userID"))       
    	newTrainingRecords.setUser(user);
    	System.out.println("service的資料:"+newTrainingRecords);
    	
    	TrainingRecordTitleBean savedRecord = trainingRecordTitleRepository.save(newTrainingRecords);
    	
    	return createTrainingRecordTitleDTO(savedRecord);
    }
      

////	用日期查詢訓練標題(有多筆)
//	public List<CreateTrainingRecordTitleDTO> findRecordTitleByDate(String date, Authentication authentication) {
//        GymUSerProfilesBean user = getAuthenticatedUser(authentication);
//        Date sqlDate = Date.valueOf(date); // 將 String 轉為 java.sql.Date
////        return trainingRecordTitleRepository.findByTrainingDateAndUser(sqlDate, user);       
//        List<TrainingRecordTitleBean> records = trainingRecordTitleRepository.findByTrainingDateAndUser(sqlDate, user);
//
//        return records.stream()
//                .map(this::convertToTitleDTO)
//                .toList();
//	}
        
//	用日期查詢訓練標題與訓練內容(有多筆)
        public List<CreateTrainingRecordTitleDTO> findRecordTitleByDate(String date, Authentication authentication) {
        	GymUSerProfilesBean user = getAuthenticatedUser(authentication);
        	Date sqlDate = Date.valueOf(date); // 將 String 轉為 java.sql.Date
        	List<TrainingRecordTitleBean> records = trainingRecordTitleRepository.findByTrainingDateAndUser(sqlDate, user);
        	
        	return records.stream()
        			.map(record -> {
                        CreateTrainingRecordTitleDTO dto = convertToTitleDTO(record);
                        // 取得當前標題下的所有訓練內容
                        List<TrainingRecordsBean> trainingRecords = trainingRecordsRepository.findByTrainingRecordTitle(record);
                        dto.setTrainingRecords(trainingRecords.stream()
                                .map(this::createTrainingRecordDTO)
                                .toList());

                        return dto;
                    })
                    .toList();
    }
	
//   刪除訓練標題時 同時 刪除訓練內容(在資料庫已經設定ON DELETE CASCADE)
        public void deleteById(Integer id) {
        	try {
    			trainingRecordTitleRepository.deleteById(id);
    			
    		} catch (EmptyResultDataAccessException e) {
    			throw new ResourceNotFoundException("查無此訓練標題 ID：" + id);
    		}
        }    
        
//	查詢所有訓練標題的日期，用於標記月曆上有訓練的時間
//    	public List<Map<String, Object>> getAllTrainingDates() {
//            return trainingRecordTitleRepository.findAllTrainingDates();
//        }
    	
        public List<Map<String, Object>> getAllTrainingDates(String emailAddress) {
            return trainingRecordTitleRepository.findAllTrainingDatesByEmailAddress(emailAddress);
        }

    	
    	
    	
//	查詢指定月份的訓練天數有幾天
    	public int getTrainingDaysByMonth(String month, Authentication authentication) {
    	    String emailAddress = authentication.getName();
    	    						   
    	    GymUSerProfilesBean user = gymUserProfilesRepository.findByEmailAddress(emailAddress)
    	            .orElseThrow(() -> new ResourceNotFoundException("找不到使用者信箱:" + emailAddress));

    	    int userId = user.getId(); // 使用 getId() 方法獲取 userId

    	    // 查詢當月訓練天數
    	    return trainingRecordTitleRepository.countTrainingDaysByUserAndMonth(userId, month);
    	}

    	
    	
    	
    	
        
	
//	封裝一個方法，取得登入的使用者資訊，可以重複用
	private GymUSerProfilesBean getAuthenticatedUser(Authentication authentication) {
		//	從帳號取得此帳號的個人資料
        String email = authentication.getName();
        return gymUserProfilesRepository.findByEmailAddress(email)
                .orElseThrow(() -> new ResourceNotFoundException("找不到當前使用者"));
    }	
	
//	封裝一個DTO方法 "新增訓練標題" ，讓前端與DTO傳輸與回傳
	private CreateTrainingRecordTitleDTO createTrainingRecordTitleDTO(TrainingRecordTitleBean record) {
		CreateTrainingRecordTitleDTO dto = new CreateTrainingRecordTitleDTO();
        dto.setTrainingTitleID(record.getTrainingTitleID());
        dto.setTrainingTitle(record.getTrainingTitle());
        dto.setTrainingDate(record.getTrainingDate());
        return dto;
	}
	
//	封裝DTO方法 "用日期顯示所有訓練標題"，讓前端與DTO傳輸與回傳
	private CreateTrainingRecordTitleDTO convertToTitleDTO(TrainingRecordTitleBean record) {
	    CreateTrainingRecordTitleDTO dto = new CreateTrainingRecordTitleDTO();
	    dto.setTrainingTitleID(record.getTrainingTitleID());
	    dto.setTrainingTitle(record.getTrainingTitle());
	    dto.setTrainingDate(record.getTrainingDate());
	    return dto;
	}
	
	// 封裝DTO方法 "顯示訓練內容"
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
