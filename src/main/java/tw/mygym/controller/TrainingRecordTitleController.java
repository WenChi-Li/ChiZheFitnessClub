package tw.mygym.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import tw.mygym.model.bean.TrainingRecordTitleBean;
import tw.mygym.model.dto.CreateTrainingRecordTitleDTO;
import tw.mygym.model.service.TrainingRecordTitleService;

	@Controller
	@Transactional
public class TrainingRecordTitleController {

	@Autowired
	private TrainingRecordTitleService trainingRecordTitleService;
	
	
//	新增訓練開頭
	@PostMapping("/createTrainingRecordTitle")
	public ResponseEntity<?> addTrainingTitle(@RequestBody TrainingRecordTitleBean bean,Authentication authentication) {
		System.out.println(bean);
		if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登入使用者無法新增訓練項目");
        }
		 try {
			 CreateTrainingRecordTitleDTO dto = trainingRecordTitleService.inserRecordTitle(bean, authentication);
		        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		    } catch (Exception e) {
		        // 打印日志以便排查错误
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系統其他錯誤，請稍後再試");
		    }
	
	}	
	
//	點擊日期，顯示所有當日訓練標題(舊版)
//	@GetMapping("/trainingRecordTitleByDate")
//    public ResponseEntity<?> getTrainingRecordTitleByDate(@RequestParam("date") String date, Authentication authentication) {
//        try {
//            List<CreateTrainingRecordTitleDTO> records = trainingRecordTitleService.findRecordTitleByDate(date, authentication);
//            return ResponseEntity.ok(records);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查詢失敗，請稍後再試");
//        }
//    }
	
//	點擊日期，顯示所有當日標題與內容
	@GetMapping("/trainingRecordTitleWithContentsByDate")
	public ResponseEntity<?> getTrainingRecordTitleWithContentsByDate(@RequestParam("date") String date, Authentication authentication) {
	    try {
	        List<CreateTrainingRecordTitleDTO> records = trainingRecordTitleService.findRecordTitleByDate(date, authentication);
	        return ResponseEntity.ok(records);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败，请稍后再试");
	    }
	}

	
//	刪除訓練標題 同時 刪除訓練內容
	@DeleteMapping("/deleteRecordTitleAndRecord/{titleId}")
	public ResponseEntity<?> deleteRecordTitleAndRecord(@PathVariable("titleId") Integer titleId){
		trainingRecordTitleService.deleteById(titleId);
		 return ResponseEntity.ok("刪除成功!");
	}

//	標記有訓練標題的日期
	@GetMapping("/getAllDates")
	@ResponseBody
	public List<Map<String, Object>> getAllTrainingDates(Authentication authentication) {
	    String emailAddress = authentication.getName(); 
	    return trainingRecordTitleService.getAllTrainingDates(emailAddress); 
	}
	
	
	
//	搜尋指定月份的總訓練天數
	@GetMapping("/getTraining-days")
	public ResponseEntity<Integer> getTrainingDays(@RequestParam String month,Authentication authentication) {
	    int trainingDays = trainingRecordTitleService.getTrainingDaysByMonth(month, authentication);
	    return ResponseEntity.ok(trainingDays);
	}

	
}
