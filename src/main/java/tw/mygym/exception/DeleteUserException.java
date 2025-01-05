package tw.mygym.exception;

public class DeleteUserException extends RuntimeException {

	// 刪除使用者Id例外處理	
	public DeleteUserException(String message) {
        super(message);
    }

}
