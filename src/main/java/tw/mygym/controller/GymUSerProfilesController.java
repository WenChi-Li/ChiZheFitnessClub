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

import tw.mygym.model.bean.GymUSerProfilesBean;
import tw.mygym.model.dto.PasswordUpdateDto;
import tw.mygym.model.service.GymUSerProfilesService;

@Controller
@Transactional
public class GymUSerProfilesController {

	@Autowired
	private GymUSerProfilesService gymUSerProfilesService;
	
//	新增使用者(使用全域處理)
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@RequestBody GymUSerProfilesBean user) {
		System.out.println(user);
		
			GymUSerProfilesBean newUSer = gymUSerProfilesService.insertUser(user);
			System.out.println(newUSer);
			return  ResponseEntity.status(HttpStatus.CREATED).body(newUSer);
	}
	
//	新增使用者時，點選"下一步"驗證帳號密碼是否重複，不需要儲存
	@PostMapping("/checkDuplicateUser")
	public ResponseEntity<?> checkDuplicateUser(@RequestBody GymUSerProfilesBean user){
		        gymUSerProfilesService.validateUser(user);
		        System.err.println(user);
		        return ResponseEntity.ok("資料無重複");		    
	}
	
	
//	修改使用者
	@PostMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestBody GymUSerProfilesBean user){
		
		try {
			GymUSerProfilesBean updateUser = gymUSerProfilesService.updateUser(user);
			return ResponseEntity.ok(updateUser);			
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());		
	    }
	}

//	修改使用者密碼(獨立出來)
//	先驗證密碼
	@PostMapping("/updateUser/verifyPassword")
	public ResponseEntity<?> verifyPassword(@RequestBody PasswordUpdateDto dto, Authentication authentication) {
	    String email = authentication.getName(); // 從 session 獲取用戶 email
	    gymUSerProfilesService.verifyPassword(email, dto.getOldPassword());
	    return ResponseEntity.ok("密碼驗證成功");
	}
//	再修改密碼
	@PostMapping("/updateUser/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDto dto, Authentication authentication) {
	    String email = authentication.getName();
	    gymUSerProfilesService.updatePassword(email, dto.getNewPassword());
	    return ResponseEntity.ok("密碼更新成功");
	}
	
	
	
////	刪除使用者(舊版)
//	@DeleteMapping("/deleteUser/{id}")
//	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
//		try {
//			GymUSerProfilesBean deleteUser = gymUSerProfilesService.deleteUser(id);
//			return ResponseEntity.ok("使用者已刪除:" + deleteUser.getUserName());			
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());			
//		}
//	}
	
//	刪除使用者(使用全域例外處理 "GlobalExceptionHandler"，不用多寫 try catch ，專心處理controller)
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id){
			GymUSerProfilesBean deleteUser = gymUSerProfilesService.deleteUser(id);
			return ResponseEntity.ok("使用者已刪除:" + deleteUser.getUserName());			
	}	
	
//	查詢全部使用者(使用全域處理)
	@GetMapping("/findAllUser")
	public ResponseEntity<?> findAllUser(){
			List<GymUSerProfilesBean> findAllUser = gymUSerProfilesService.findAllUser();
			return ResponseEntity.ok(findAllUser);			
	}
	
//	用電話號碼查詢使用者(使用全域處理)
	@GetMapping("/phone/{phone}")
	public ResponseEntity<?> findByPhone(@PathVariable String phone){
			GymUSerProfilesBean findByPhone = gymUSerProfilesService.findByPhone(phone);
			return ResponseEntity.ok("電話:" + findByPhone.getUserPhone() + "資訊:" + findByPhone);
	}
	
//	使用Authentication 查詢使用者資料，顯示在前端使用者可以看到他自己的資訊(使用全域處理)
	@GetMapping("/user/profile")
	public ResponseEntity<?> getUserProfile(Authentication authentication){
//		authentication.getName()獲取登入時的帳號，我們帳號是mail
		String email = authentication.getName();
//		把帳號塞進查詢方法內查詢
		GymUSerProfilesBean userProfile = gymUSerProfilesService.findByEmail(email);
			return ResponseEntity.ok(userProfile);
	}	
}
