package tw.mygym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//	全域例外處理，讓controller少寫例外，集中在這裡處理
@ControllerAdvice
public class GlobalExceptionHandler {
	
	// 處理資料重複的例外
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    // 資料不存在的例外   
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
    	return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    

    // 處理刪除使用者時 ID 不存在的例外
    @ExceptionHandler(DeleteUserException.class)
    public ResponseEntity<?> handleDeleteUserException(DeleteUserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    // 處理查詢全部 無資料時例外處理
    @ExceptionHandler(FindAllUserException.class)
    public ResponseEntity<?> handleFindAllUserException(FindAllUserException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    // 處理查詢的電話 無資料時例外處理
    @ExceptionHandler(FindByPhoneException.class)
    public ResponseEntity<?> handleFindByPhoneException(FindByPhoneException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    // 處理查詢的Id 無資料時例外處理
    @ExceptionHandler(FindUserByEmailException.class)
    public ResponseEntity<?> handleFindUserByEmailException(FindUserByEmailException ex) {
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    

    // 處理所有其他未捕捉的例外
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系統錯誤，請稍後再試");
    }
}
