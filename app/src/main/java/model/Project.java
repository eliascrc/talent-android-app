package model;

import java.util.Set;
import java.util.Date;

/**
 * Class that represents a project within the Talent system.
 * It contains the project name, description, dates, links, capabilities, lead history, status, timeline,
 * positions and capabilities and the information inherited from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Project extends BasicEntity {

    /**
     * The name of the project.
     */
    private String name;

    /**
     * The description of the project.
     */
    private String description;

    /**
     * The date in which the project started.
     */
    private Date startDate;

    /**
     * The date in which the project finished.
     */
    private Date endDate;

    /**
     * The link to the JIRA page of the project.
     */
    private String jiraLink;

    /**
     * The link to the Confluence page of the project.
     */
    private String confluenceLink;

    /**
     * The link to the Version Control page of the project.
     */
    private String versionControlLink;

    /**
     * The event timeline of the project
     */
    private Set<ProjectEvent> timeline;

    /**
     * The state that the project currently has. It's represented by the latest event.
     */
    private ProjectEvent state;

    /**
     * A set with the history of project manager's throughout the life time of the project.
     */
    private Set<LeadPosition> leadHistory;

    /**
     * A set with the capabilities of the project.
     */
    private Set<CapabilityLevel> projectCapabilities;

    /**
     * A set with the positions of the project
     */
    private Set<ProjectPosition> projectPositions;

    /**
     * The organization that the project belongs to.
     */
    private Organization organization;

    /**
     * List of feedback made in the project.
     */
    private Set<Feedback> resourcesFeedback;

    public Project () {}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Project){
            Project project = (Project) o;
            result = ((this.name == null ? project.getName() == null : this.name.equals(project.getName()))
                    && (this.organization == null ? project.getOrganization() == null : this.organization.equals(project.getOrganization())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result + (this.organization == null ? 0 : this.organization.hashCode());
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<CapabilityLevel> getProjectCapabilities() {
        return projectCapabilities;
    }

    public void setProjectCapabilities(Set<CapabilityLevel> projectCapabilities) {
        this.projectCapabilities = projectCapabilities;
    }

    public ProjectEvent getState() {
        return state;
    }

    public void setState(ProjectEvent state) {
        this.state = state;
    }

    public Set<LeadPosition> getLeadHistory() {
        return leadHistory;
    }

    public void setLeadHistory(Set<LeadPosition> leadHistory) {
        this.leadHistory = leadHistory;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Feedback> getResourcesFeedback() {
        return resourcesFeedback;
    }

    public void setResourcesFeedback(Set<Feedback> resourcesFeedback) {
        this.resourcesFeedback = resourcesFeedback;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public String getConfluenceLink() {
        return confluenceLink;
    }

    public void setConfluenceLink(String confluenceLink) {
        this.confluenceLink = confluenceLink;
    }

    public String getVersionControlLink() {
        return versionControlLink;
    }

    public void setVersionControlLink(String versionControlLink) {
        this.versionControlLink = versionControlLink;
    }

    public Set<ProjectEvent> getTimeline() {
        return timeline;
    }

    public void setTimeline(Set<ProjectEvent> timeline) {
        this.timeline = timeline;
    }

    public Set<ProjectPosition> getProjectPositions() {
        return projectPositions;
    }

    public void setProjectPositions(Set<ProjectPosition> projectPositions) {
        this.projectPositions = projectPositions;
    }
}