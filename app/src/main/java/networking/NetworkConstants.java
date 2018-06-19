package networking;

public final class NetworkConstants {
    public static final String BASE_URL = "http://ws.talent.cr";
    public static final String TERMS_OF_SERVICE_URL = BASE_URL + "/ws/content/termsOfService?platform=android";
    public static final String PRIVACY_POLICY_URL = BASE_URL + "/ws/content/privacyPolicy?platform=android";
    public static final String SIGN_IN_URL = BASE_URL + "/ws/login";
    public static final String LOGOUT_URL = BASE_URL + "/ws/logout";
    public static final String SIGN_IN_TOKEN_URL = BASE_URL + "/ws/login/token";
    public static final String GET_ORGANIZATION_URL = BASE_URL + "/ws/login/organization?uniqueIdentifier=";
    public static final String FORGOT_PASSWORD_SEND_EMAIL_URL = BASE_URL + "/ws/passwordReset/forgotPassword";
    public static final String USER_AUTHENTICATED = BASE_URL + "/ws/user/authenticated";
    public static final String CONTACT_US_AUTHENTICATED = BASE_URL + "/ws/contactUs/authenticated";
    public static final String CONTACT_US_UNAUTHENTICATED = BASE_URL + "/ws/contactUs/unauthenticated";
    public static final String COOKIE_HEADER_KEY = "Set-Cookie";
    public static final String SEMICOLON = ";";
    public static final String TOKEN = "token";
    public static final String COOKIE = "cookie";
    public static final String ORIGIN = "origin";
    public static final String ANDROID = "android";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ORGANIZATION_IDENTIFIER = "organizationIdentifier";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String EMAIL = "email";
    public static final String ISSUE_TYPE = "issueType";
    public static final String ISSUE = "issue";
}