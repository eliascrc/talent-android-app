package networking;

public final class NetworkConstants {
    public static final String BASE_URL = "http://ws.talent.cr/ws";
    public static final String TERMS_OF_SERVICE_URL = BASE_URL + "/content/termsOfService";
    public static final String PRIVACY_POLICY_URL = BASE_URL + "/content/privacyPolicy";
    public static final String SIGN_IN_URL = BASE_URL + "/login";
    public static final String GET_ORGANIZATION_URL = BASE_URL + "/login/organization?uniqueIdentifier=";
    public static final String FORGOT_PASSWORD_SEND_EMAIL_URL = BASE_URL + "/passwordReset/forgotPassword";
}