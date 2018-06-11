package model;

import java.util.Date;

/**
 * Class that represents a Project position holder within the Talent system.
 * It contains the dates, the related position, if it has been already reviewed, the resource, the assigned hours,
 * if it's active and the information inherited from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class ProjectPositionHolder extends BasicEntity {

    /**
     * The date in which the project position holder started
     */
    private Date startDate;

    /**
     * The date in which the project position holder ended
     */
    private Date endDate;

    /**
     * The related position of the position holder
     */
    private ProjectPosition projectPosition;

    /**
     * Indicates if the holder resource has already being reviewed for this position
     */
    private boolean reviewed;

    /**
     * The related technical resource for the position holder.
     */
    private TechnicalResource resource;

    /**
     * The assigned hours to the holder for the position.
     */
    private int assignedHours;

    /**
     * Indicates if the position is currently active
     */
    private boolean active;

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof ProjectPositionHolder ){
            ProjectPositionHolder projectPositionHolder = (ProjectPositionHolder) o;
            result = ((this.projectPosition == null ? projectPositionHolder.getProjectPosition() == null : this.projectPosition.equals(projectPositionHolder.getProjectPosition()))
                    && (this.resource == null ? projectPositionHolder.getResource() == null : this.resource.equals(projectPositionHolder.getResource()))
                    && (this.startDate == null ? projectPositionHolder.getStartDate() == null : this.startDate.equals(projectPositionHolder.getStartDate())));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.projectPosition == null ? 0 : this.projectPosition.hashCode());
        result = prime * result + (this.resource == null ? 0 : this.resource.hashCode());
        result = prime * result + (this.startDate == null ? 0 : this.startDate.hashCode());
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

    public ProjectPosition getProjectPosition() {
        return projectPosition;
    }

    public void setProjectPosition(ProjectPosition projectPosition) {
        this.projectPosition = projectPosition;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public TechnicalResource getResource() {
        return resource;
    }

    public void setResource(TechnicalResource resource) {
        this.resource = resource;
    }

    public int getAssignedHours() {
        return assignedHours;
    }

    public void setAssignedHours(int assignedHours) {
        this.assignedHours = assignedHours;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
