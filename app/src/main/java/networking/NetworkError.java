package networking;

/**
 * This class is for network error responses so that common data like error code and error message.
 *
 * @author Renato Mainieri Sáenz.
 */
public class NetworkError {

    private String errorMessage;
    private String errorCode;

    public NetworkError(){
        this.errorMessage = "";
        this.errorCode = "";
    }

    public NetworkError(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
