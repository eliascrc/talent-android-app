package model;

import java.util.Date;

/**
 * Class that represents a project event within the Talent system.
 * It contains the event's dates, eventType, project and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class ProjectEvent extends BasicEntity {

    /**
     * The date in which the event started.
     */
    private Date startDate;

    /**
     * The date in which the event finished.
     */
    private Date endDate;

    /**
     * The type of event.
     */
    private ProjectEventType eventType;

    /**
     * The project of the event.
     */
    private Project project;

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof ProjectEvent){
            ProjectEvent projectEvent = (ProjectEvent) o;
            result = ((this.startDate == null ? projectEvent.getStartDate() == null : this.startDate.equals(projectEvent.getStartDate()))
                      && (this.eventType == null ? projectEvent.getEventType() == null : this.eventType.equals(projectEvent.getEventType()))
                      && (this.project == null ? projectEvent.getProject() == null : this.project.equals(projectEvent.getProject())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.startDate == null ? 0 : this.startDate.hashCode());
        result = prime * result + (this.eventType == null ? 0 : this.eventType.hashCode());
        result = prime * result + (this.project == null ? 0 : this.project.hashCode());
        return result;
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

    public ProjectEventType getEventType() {
        return eventType;
    }

    public void setEventType(ProjectEventType eventType) {
        this.eventType = eventType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
