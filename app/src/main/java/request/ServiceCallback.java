package request;

/**
 * This class contains the definition of common methods for when a listener is implemented
 *
 * @author Renato Mainieri Sáenz.
 */
public interface ServiceCallback<T, E> {
    public void onPreExecute(T listener);
    public void onSuccessResponse(T response);
    public void onErrorResponse(E error);
}