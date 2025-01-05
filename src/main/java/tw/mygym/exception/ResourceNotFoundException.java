package tw.mygym.exception;

//	資源不存在異常
public class ResourceNotFoundException extends RuntimeException  {

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
