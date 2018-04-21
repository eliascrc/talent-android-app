package request;

/**
 * This class contains the definition of common methods for when a listener is implemented
 *
 * @author Renato Mainieri Sáenz.
 */
public interface ServiceCallback<T, E> {
    public void onPreExecute();
    public void onSuccessResponse(T response);
    public boolean onErrorResponse(E error);
}