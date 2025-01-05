package tw.mygym.exception;


	// 自訂拋出例外，資料庫數值"重複"時拋出"字串顯示"
    public class DuplicateResourceException extends RuntimeException {
        
    	public DuplicateResourceException(String message) {
    		super(message);
    	}
    }
    
    
