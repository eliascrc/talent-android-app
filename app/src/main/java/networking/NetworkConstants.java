package networking;

public final class NetworkConstants {
    public static final String BASE_URL = "http://ws.talent.cr";
    public static final String TERMS_OF_SERVICE_URL = BASE_URL + "/ws/content/termsOfService?platform=android";
    public static final String PRIVACY_POLICY_URL = BASE_URL + "/ws/content/privacyPolicy?platform=android";
    public static final String SIGN_IN_URL = BASE_URL + "/ws/login";
    public static final String SIGN_IN_TOKEN_URL = BASE_URL + "/ws/login/token";
    public static final String GET_ORGANIZATION_URL = BASE_URL + "/ws/login/organization?uniqueIdentifier=";
    public static final String FORGOT_PASSWORD_SEND_EMAIL_URL = BASE_URL + "/ws/passwordReset/forgotPassword";
    public static final String USER_AUTHENTICATED = BASE_URL + "/ws/user/authenticated";
}