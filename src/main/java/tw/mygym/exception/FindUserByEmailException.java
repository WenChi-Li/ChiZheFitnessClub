package tw.mygym.exception;

public class FindUserByEmailException extends RuntimeException {

	public FindUserByEmailException(String message) {
        super(message);
    }
}
