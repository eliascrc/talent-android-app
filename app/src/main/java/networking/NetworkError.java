package networking;

/**
 * This class is for network error responses so that common data like error code and error message.
 *
 * @author Renato Mainieri Sáenz.
 */
public class NetworkError {

    private String errorMessage;
    private int errorCode;

    public NetworkError(){
        this.errorMessage = "";
        this.errorCode = 0;
    }

    public NetworkError(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
