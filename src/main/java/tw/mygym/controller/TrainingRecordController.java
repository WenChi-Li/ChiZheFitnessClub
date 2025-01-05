package tw.mygym.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import tw.mygym.model.bean.TrainingRecordsBean;
import tw.mygym.model.dto.CreateTrainingRecordDTO;
import tw.mygym.model.dto.MonthlyTrainingStatsDTO;
import tw.mygym.model.dto.WeeklyTrainingStatsDTO;
import tw.mygym.model.service.TrainingRecordService;

@Controller
@Transactional
public class TrainingRecordController {

	
	@Autowired
    private TrainingRecordService trainingRecordService;
	
	// 查詢訓練標題下的所有訓練內容
    @GetMapping("/findTrainingRecord/{titleId}")
    public ResponseEntity<?> getTrainingRecords(@PathVariable("titleId") int titleId) {
    	if (titleId <= 0) {
            return ResponseEntity.badRequest().body("提供的 titleId 無效");
        }
        List<TrainingRecordsBean> records = trainingRecordService.findRecordsByTitle(titleId);
        return ResponseEntity.ok(records);
    }
	
 // 新增訓練內容
    @PostMapping("/addTrainingRecord")
    public ResponseEntity<CreateTrainingRecordDTO> addRecord(@RequestBody TrainingRecordsBean trainingRecord,
    		@RequestParam("titleId") int titleId) {
        CreateTrainingRecordDTO dto = trainingRecordService.insertRecord(trainingRecord, titleId);
        return ResponseEntity.ok(dto);
    }
	
	
//	刪除訓練內容
    @DeleteMapping("/deleteTrainingRecord{trainingRecordId}")
    public ResponseEntity<?> deleteTrainingRecordById(@PathVariable("trainingRecordId") int trainingRecordId){
    	
    	trainingRecordService.deleteById(trainingRecordId);
		return ResponseEntity.ok("訓練內容已成功刪除，ID：" + trainingRecordId);
    }
	
//	查詢每月訓練容積加總(用id找)
//    @GetMapping("/monthly-stats")
//    public ResponseEntity<MonthlyTrainingStatsDTO> getMonthlyStats(
//            @RequestParam String month,
//            @RequestParam int userId) {
//
//        MonthlyTrainingStatsDTO stats = trainingRecordService.getMonthlyStats(month, userId);
//        return ResponseEntity.ok(stats);
//    }
    
//	查詢這個月訓練容積加總(用Authentication找)
    @GetMapping("/monthly-stats")
    public ResponseEntity<MonthlyTrainingStatsDTO> getMonthlyStats(@RequestParam String month,
    		Authentication authentication) {
    	
    	MonthlyTrainingStatsDTO stats = trainingRecordService.getMonthlyStats(month, authentication);
    	return ResponseEntity.ok(stats);
    }
    
    
    
 // 查詢每周訓練統計，用於曲線圖
    @GetMapping("/weekly-stats")
    public ResponseEntity<List<WeeklyTrainingStatsDTO>> getWeeklyStats(
            @RequestParam int year,
            Authentication authentication) {

        List<WeeklyTrainingStatsDTO> stats = trainingRecordService.getWeeklyStats(year, authentication);
        return ResponseEntity.ok(stats);
    }
    
    
    
}
