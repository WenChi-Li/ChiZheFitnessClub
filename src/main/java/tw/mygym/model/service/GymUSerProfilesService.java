package tw.mygym.model.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.mygym.exception.DeleteUserException;
import tw.mygym.exception.DuplicateResourceException;
import tw.mygym.exception.ResourceNotFoundException;
import tw.mygym.exception.FindAllUserException;
import tw.mygym.exception.FindByPhoneException;
import tw.mygym.exception.FindUserByEmailException;
import tw.mygym.model.bean.GymUSerProfilesBean;
import tw.mygym.model.repository.GymUSerProfilesRepository;


@Service
@Transactional
public class GymUSerProfilesService implements UserDetailsService{

	@Autowired
	private GymUSerProfilesRepository gymUSerProfilesRepository;
	
	@Autowired
	private  PasswordEncoder passwordEncoder ;
	
	
	/* 							==============
	 * 							|   三種寫法	|
	 * 							==============
	 * ======================================================================== *
	 * (一) public GymUSerProfilesBean updateUser(GymUSerProfilesBean user) {	*
	 * 		 	return gymUSerProfilesRepository.save(user);					*
	 * 	  }																		*
	 * 	說明:																		*
	 * 	直接返回更新後的用戶實體，這樣調用者可以獲得更新後的完整資料，尤其是在其他功能需要這些資料的情況下。			*
	 * 	應用場景：1. 前端需要展示完整的更新後資料												*
	 * 		  2. 其他功能可以重用返回的數據。												*
	 * ========================================================================	*
	 
	 * ==================================================================================== *
	 * (二) public PasswordUpdateDto updatePassword(PasswordUpdateDto dto, String email) {	*
	 * 			PasswordUpdateDto response = new PasswordUpdateDto();						*
	 * 			response.setNewPassword("****"); // 不回傳明文密碼，隱藏為安全						*
	 * 			response.setOldPassword(null); // 隱藏原密碼									*
	 * 			return response;															*
	 * 		}																				*
	 * 	說明:																					*
	 * 	如果業務需求需要返回一些更新後的訊息（如用戶名或更新結果的確認訊息），可以用dto作為返回值							*
	 * 	應用場景：1. 前端可能需要顯示操作成功的確認訊息。														*
	 * 		  2. 例如返回的 PasswordUpdateDto 表示密碼已成功更新（只含非敏感數據）。							*
	 * ==================================================================================== *

	 * ========================================================================================	*
	 * (三) public void updatePassword(String email, String oldPassword, String newPassword) {	*
	 * 			user.setUserPassword(passwordEncoder.encode(newPassword));						*
	 * 			gymUSerProfilesRepository.save(user); // 不返回任何數據，操作完成即可						*
	 * 		}																					*
	 * 	說明:																						*
	 * 	void 方法的特點是純執行操作，不需要返回結果。															*
	 * 	適用於無需向調用者提供額外訊息的場景。例如：																*
	 * 		  1. 僅保存操作成功即可。 																	*
	 * 		  2. 錯誤情況通過拋出異常來處理。																*
	 * ========================================================================================	*/
	
//	驗證登入帳號密碼邏輯
	@Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        GymUSerProfilesBean user = gymUSerProfilesRepository.findByEmailAddress(username)
	            .orElseThrow(() -> new UsernameNotFoundException("使用者不存在：" + username));

	        return User.builder()
	            .username(user.getEmailAddress())
	            .password(user.getUserPassword()) // 需確保是加密的密碼
	            .roles("USER") // 可以根據業務需求設置角色
	            .build();
	 }
	
	
	/* **********************************
	 * 舊版(新手更看的懂) 					*
	 * 前台 + 後台 -> 新增使用者帳號				*
	 * **********************************/
//	public GymUSerProfilesBean InsertUser(GymUSerProfilesBean user) {
//		
////		找出帳號
//		Optional<GymUSerProfilesBean> UserEmailAddress = 
//				gymUSerProfilesRepository.findByEmailAddress(user.getEmailAddress());
//		
////		找出電話
//		Optional<GymUSerProfilesBean> UserPhone = 
//				gymUSerProfilesRepository.findByUserPhone(user.getUserPhone());
//			
//		if (UserEmailAddress.isPresent()) {
//			throw new IllegalArgumentException("此信箱已使用，無法註冊");
//		}else if (UserPhone.isPresent()) {
//			throw new IllegalArgumentException("此電話已使用，無法註冊");
//		}else if(UserEmailAddress.isPresent()&& UserPhone.isPresent()){
//			throw new IllegalArgumentException("此信箱與電話已使用，無法註冊");
//		}
//		
//		return gymUSerProfilesRepository.save(user);
//		
//	}
	
	/* **************************************************************
	 * 新版(optional的常用設計模式)										*
	 * 前台 + 後台 -> 新增使用者帳號											*
	 * 優缺點比較: 														*
	 * 	可讀性 -> 明確列出所有欄位，適合新增時需要控制每個欄位初始值的情境。				*
	 *	安全性與穩定性 -> 適合更複雜的場景，因為可以更細緻地控制新增的數據，避免傳入資料有潛在問題。	*
	 *	開發效率 -> 開發過程稍微繁瑣，特別是在欄位較多的情況下，手動賦值可能會耗時。			*
	 * 	數據一致性與可控性 -> 高，一目了然地控制每個欄位的值和邏輯，方便進行數據檢查和擴展。		*
	 * **************************************************************/
	public GymUSerProfilesBean insertUser(GymUSerProfilesBean user) {
	    // 檢查信箱是否重複
	    boolean emailExists = gymUSerProfilesRepository.findByEmailAddress(user.getEmailAddress()).isPresent();
	    // 檢查電話是否重複
	    boolean phoneExists = gymUSerProfilesRepository.findByUserPhone(user.getUserPhone()).isPresent();

	    if (emailExists && phoneExists) {
	        throw new DuplicateResourceException("此信箱與電話已使用，無法註冊");
	    } else if (emailExists) {
	        throw new DuplicateResourceException("此信箱已使用，無法註冊");
	    } else if (phoneExists) {
	        throw new DuplicateResourceException("此電話已使用，無法註冊");
	    }
	    
	 // 加密密碼
	    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
	    
	    GymUSerProfilesBean newUser = new GymUSerProfilesBean();
	    newUser.setUserName(user.getUserName());
	    newUser.setEmailAddress(user.getEmailAddress());
	    newUser.setUserPassword(user.getUserPassword());
//	    newUser.setRegistrationDate(user.getRegistrationDate());
	    newUser.setRegistrationDate(new Date());
	    newUser.setUserPhone(user.getUserPhone());
	    newUser.setUserAddress(user.getUserAddress());
	    newUser.setGender(user.getGender());
	    newUser.setBirthdate(user.getBirthdate());
	    newUser.setEmergencyContactName(user.getEmergencyContactName());
	    newUser.setEmergencyContactPhone(user.getEmergencyContactPhone());

//	    System.out.println(newUser);
	    return gymUSerProfilesRepository.save(newUser);
	}	
	
//	註冊時，點選"下一步"時驗證資料庫是否有重複
	public void  validateUser(GymUSerProfilesBean user) {		
		 boolean emailExists = gymUSerProfilesRepository.findByEmailAddress(user.getEmailAddress()).isPresent();
		 boolean phoneExists = gymUSerProfilesRepository.findByUserPhone(user.getUserPhone()).isPresent();

		    if (emailExists && phoneExists) {
		        throw new DuplicateResourceException("此信箱與電話已使用，無法註冊");
		    } else if (emailExists) {
		        throw new DuplicateResourceException("此信箱已使用，無法註冊");
		    } else if (phoneExists) {
		        throw new DuplicateResourceException("此電話已使用，無法註冊");
		    }	
	}
	
	
	/* **************************
	 * 	舊版						*
	 * 	前台 + 後台 -> 修改使用者資料	*
	 * **************************/
//	public GymUSerProfilesBean UpdateUser(GymUSerProfilesBean user) {
//		
////		檢查使用者Id是否存在，存在才能修改
//		Optional<GymUSerProfilesBean> FindByUserId = 
//				gymUSerProfilesRepository.findById(user.getId());
//		
//		if (FindByUserId.isPresent()) {
//			return gymUSerProfilesRepository.save(user);			
//		}else {
//			throw new IllegalArgumentException("使用者不存在，請先新增資料再修改");
//		}
//		
//	}
	
	
	/* **************************************************************************
	 * 新版																		*
	 * 前台 + 後台 -> 修改使用者資料														*
	 * 																			*
	 * 問題解釋:為什麼 return save要儲存(existingUser) 而不是 user ?						*
	 * 	existingUser是已經調用資料庫的資料，要更新後把它重新存進資料庫								*
	 *  user是外部資料，如果直接儲存的外部資料為null，會複蓋到原本資料庫資料變成null，一次修改要全部重填一遍		*
	 * 																			*
	 * 範例:																		*
	 * 如果用 existingUser：														*
	 *	existingUser.setUserName(user.getUserName()); // 更新名字					*
	 *	existingUser.setEmailAddress(user.getEmailAddress()); // 更新信箱			*
	 *	gymUSerProfilesRepository.save(existingUser);							*
	 * 資料庫結果：																	*
	 *	{																		*
	 *  "id": 1,																*
	 *  "userName": "Alice Smith",												*
	 *  "emailAddress": "alice.smith@example.com",								*
	 *  "userPhone": "123456789",												*
	 *  "userAddress": "123 Main St"											*
	 *	}																		*
	 * 																			*
	 * 如果用 user：																*
	 *	existingUser.setUserName(user.getUserName()); // 更新名字					*
	 *	existingUser.setEmailAddress(user.getEmailAddress()); // 更新信箱			*
	 *	gymUSerProfilesRepository.save(user);;									*
	 * 資料庫結果：																	*
	 *	{																		*
	 *  "id": 1,																*
	 *  "userName": "Alice Smith",												*
	 *  "emailAddress": "alice.smith@example.com",								*
	 *  "userPhone": "123456789",												*
	 *  "userAddress": null														*
	 *	}																		*
	 * 																			*
	 * 會直接複寫																	*
	 * **************************************************************************/
	
	public GymUSerProfilesBean updateUser(GymUSerProfilesBean user) {
		 try {
		GymUSerProfilesBean existingUser = gymUSerProfilesRepository.findById(user.getId())
				.orElseThrow(()->new DuplicateResourceException("使用者Id不存在，請先新增資料"));		
		/* **************************************************************************
		 * 檢查 Email 是否被其他使用者使用													*
		 * 找尋 EmailAddress，如果 EmailAddress 有值(全部資料庫)，執行下面邏輯->					*
		 * {如果 emailUser 的 id 與當前更新的 user.getId 不同，代表信箱已被其他使用者id使用，無法更新}		*
		 * 如果 EmailAddress屬於 user.getId(自己)，允許更新 									*
		 * **************************************************************************/
	    gymUSerProfilesRepository.findByEmailAddress(user.getEmailAddress()).ifPresent(emailUser -> {
	        if (emailUser.getId() != (user.getId())) {
	            throw new  DuplicateResourceException("此信箱已使用，更新失敗");
	        }
	    });
	    // 檢查 Phone 是否被其他使用者使用
	    gymUSerProfilesRepository.findByUserPhone(user.getUserPhone()).ifPresent(phoneUser -> {
//	        if (phoneUser.getId() != (user.getId())) {
	        	if (!Objects.equals(phoneUser.getId(), user.getId())) {
	            throw new  DuplicateResourceException("此電話已使用，更新失敗");
	        }
	    });	
		
		existingUser.setUserName(user.getUserName());
		existingUser.setEmailAddress(user.getEmailAddress());
		existingUser.setUserPhone(user.getUserPhone());
		existingUser.setUserAddress(user.getUserAddress());
		existingUser.setGender(user.getGender());
		existingUser.setBirthdate(user.getBirthdate());
		existingUser.setEmergencyContactName(user.getEmergencyContactName());
		existingUser.setEmergencyContactPhone(user.getEmergencyContactPhone());
		
		return gymUSerProfilesRepository.save(existingUser);
		 }catch (Exception e) {
			 e.printStackTrace();
		     throw new RuntimeException("更新使用者資料時發生錯誤", e);
		 }
	}
	
	
//	將更新密碼獨立出來，驗證密碼後更新密碼
//	先驗證密碼
	public void verifyPassword(String email, String oldPassword) {
        GymUSerProfilesBean user = gymUSerProfilesRepository.findByEmailAddress(email)
                .orElseThrow(() -> new ResourceNotFoundException("使用者不存在"));

        if (!passwordEncoder.matches(oldPassword, user.getUserPassword())) {
            throw new ResourceNotFoundException("密碼驗證失敗，請重新輸入");
        }
    }
//	再更新密碼
	public void updatePassword(String email, String newPassword) {
	    GymUSerProfilesBean user = gymUSerProfilesRepository.findByEmailAddress(email)
	            .orElseThrow(() -> new ResourceNotFoundException("使用者不存在"));

	    user.setUserPassword(passwordEncoder.encode(newPassword));
	    gymUSerProfilesRepository.save(user);
	}
	
	
	
	/* **********************************************************
	 * 舊版未簡化													*
	 * 前台 + 後台 - 刪除使用者資料(有回傳值，可以做後續動作，例如發送通知給使用者)		*
	 * **********************************************************/
	
//	public GymUSerProfilesBean DeleteUser(Integer UserId) {
//		Optional<GymUSerProfilesBean> FindByUserId = 
//				gymUSerProfilesRepository.findById(UserId);
//		if(FindByUserId.isPresent()) {
////			刪除前先取得刪除的資訊
//			GymUSerProfilesBean user = FindByUserId.get();
////			 刪除資料
//			 gymUSerProfilesRepository.deleteById(UserId);
////			 返回之前刪除的資訊(可以做其他動作，如果是void就不能做後續動作，看情況使用)
//			 return user;
//		}else {
//			throw new IllegalArgumentException("使用者不存在，無法刪除");
//		}
//	}
	
	/* *******************************************************
	 * 簡化版													 *
	 * 前台 + 後台 -> 刪除使用者資料(有回傳值，可以做後續動作，例如發送通知給使用者)	 *
	 * *******************************************************/
	public GymUSerProfilesBean deleteUser(Integer userId) {
	    GymUSerProfilesBean user = 
	    		gymUSerProfilesRepository.findById(userId)
	            .orElseThrow(() -> new DeleteUserException("使用者 ID " + userId + " 不存在"));

	    gymUSerProfilesRepository.delete(user);
	    return user; // 如果需要通知，可以回傳被刪除的資料
	}
	
//	後台 -> 查詢使用者全部資料
	public List<GymUSerProfilesBean> findAllUser(){
		List<GymUSerProfilesBean> user = gymUSerProfilesRepository.findAll();
		if (user.isEmpty()) {
			throw new FindAllUserException("目前無任何使用者資料");
		}
		return user; 
	}
	
//	後台 -> 查詢使用者電話號碼
	public GymUSerProfilesBean findByPhone(String userPhone) {
		return gymUSerProfilesRepository.findByUserPhone(userPhone)
				.orElseThrow(() -> new FindByPhoneException("使用者電話 : " + userPhone + "不存在，搜尋無效"));
	}
	
//	前台 - 顯示單筆使用者資訊
	public GymUSerProfilesBean findByEmail(String email) {
	    return gymUSerProfilesRepository.findByEmailAddress(email)
	            .orElseThrow(() -> new FindUserByEmailException("使用者mail : " + email + "不存在，搜尋無效"));
	}
}
