package model;

import java.util.Date;
import java.util.Set;

/**
 * Class that represents a Technical Resource within the Talent system. It contains the technical resource
 * organization, profile picture, skills, education records and other important information for Talent.
 * It also contains the information inherited from {@link User} class.
 *
 * @author Elías Calderón
 */
public class TechnicalResource extends User{

    /**
     * The profile picture of the user.
     */
    private Image profilePicture;

    /**
     * The organization that the resource belongs to.
     */
    protected Organization organization;

    /**
     * The list of the resource's skills.
     */
    private Set<Skill> skills;
    /**
     * The project positions for the resource
     */
    private Set<ProjectPositionHolder> projectPositions;

    /**
     * The resource's job position.
     */
    private JobPosition jobPosition;

    /**
     * The resource's technical position.
     */
    private TechnicalPosition technicalPosition;

    /**
     * The resource's kudos and warnings.
     */
    private Set<Feedback> feedbackMade;

    /**
     * The resource's timezone setting.
     */
    private String timeZone;

    /**
     * The resouce's emergency contacts list.
     */
    private Set<EmergencyContact> emergencyContacts;

    /**
     * The resource's level assessment time gap.
     */
    private int levelAssessmentTimeGap;

    /**
     * Specifies the date of the last level assessment result.
     */
    private Date lastLevelAssessment;

    /**
     * Specifies the date of the last performance review result.
     */
    private Date lastPerformanceReview;

    /**
     * Defines if the user is an administrator of the organization.
     */
    private boolean isAdministrator;

    /**
     * List of feedback that that resource has given to others.
     */
    private Set<Feedback> feedbackGiven;

    /**
     * A list with the project management positions that the Lead has occupied in the organization.
     */
    private Set<LeadPosition> leadPositions;

    /**
     * A small description of the resource
     */
    private String description;

    public TechnicalResource(){}

    /**
     * Method that returns the User's authorities, in this case it assigns the TECHNICAL_RESOURCE
     * role along with the ones inherited from its super class .
     * @return the collection of authorities
     */

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if (o instanceof TechnicalResource){
            TechnicalResource technicalResource = (TechnicalResource) o;
            result = (this.username == null ? technicalResource.getUsername() == null : this.username.equals(technicalResource.getUsername())
                    && this.password == null ? technicalResource.getPassword() == null : this.password.equals(technicalResource.getPassword())
                    && this.organization == null ? technicalResource.getOrganization() == null : this.organization.equals(technicalResource.getOrganization()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + ((this.username == null)? 0 : this.username.hashCode());
        result = prime * result + ((this.password == null)? 0 : this.password.hashCode());
        result = prime * result + ((this.organization == null)? 0 : this.organization.hashCode());
        return result;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public Date getLastLevelAssessment() {
        return lastLevelAssessment;
    }

    public void setLastLevelAssessment(Date lastLevelAssessment) {
        this.lastLevelAssessment = lastLevelAssessment;
    }

    public Date getLastPerformanceReview() {
        return lastPerformanceReview;
    }

    public void setLastPerformanceReview(Date lastPerformanceReview) {
        this.lastPerformanceReview = lastPerformanceReview;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public TechnicalPosition getTechnicalPosition() {
        return technicalPosition;
    }

    public void setTechnicalPosition(TechnicalPosition technicalPosition) {
        this.technicalPosition = technicalPosition;
    }

    public Set<Feedback> getFeedbackMade() {
        return feedbackMade;
    }

    public void setFeedbackMade(Set<Feedback> feedbackMade) {
        this.feedbackMade = feedbackMade;
    }

    public Set<EmergencyContact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(Set<EmergencyContact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public int getLevelAssessmentTimeGap() {
        return levelAssessmentTimeGap;
    }

    public void setLevelAssessmentTimeGap(int levelAssessmentTimeGap) {
        this.levelAssessmentTimeGap = levelAssessmentTimeGap;
    }

    public Set<Feedback> getFeedbackGiven() {
        return feedbackGiven;
    }

    public void setFeedbackGiven(Set<Feedback> madeKudo) {
        this.feedbackGiven = madeKudo;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<ProjectPositionHolder> getProjectPositions() {
        return projectPositions;
    }

    public void setProjectPositions(Set<ProjectPositionHolder> projectPositions) {
        this.projectPositions = projectPositions;
    }

    public Set<LeadPosition> getLeadPositions() {
        return leadPositions;
    }

    public void setLeadPositions(Set<LeadPosition> leadPositions) {
        this.leadPositions = leadPositions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
