package model;


/**
 * Class that represents a feedback(kudo or warning) within the Talent system.
 * It contains the description, observee, observer, the related project, feedback type
 * and the information inherited from {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Feedback extends BasicEntity {

    /**
     * Feedback content.
     */
    private String description;

    /**
     * Technical resource which is receiving the feedback.
     */
    private TechnicalResource observee;

    /**
     * Technical resource which is receiving the feedback.
     */
    private TechnicalResource observer;

    /**
     * The project that is related to the feedback.
     */
    private Project relatedProject;

    /**
     * The type of feedback. Kudo or Warning.
     */
    private FeedbackType feedbackType;

    public Feedback(){}

    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Feedback){
            Feedback observation = (Feedback) o;
            result = (this.relatedProject == null ? observation.getRelatedProject() == null : this.relatedProject.equals(observation.getRelatedProject()))
                    && (this.feedbackType == null ? observation.getFeedbackType() == null : this.feedbackType.equals(observation.getFeedbackType()))
                    && (this.observee == null ? observation.getObservee() == null : this.observee.equals(observation.getObservee()))
                    && (this.observer == null ? observation.getObserver() == null : this.observer.equals(observation.getObserver()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.relatedProject == null ? 0 : this.relatedProject.hashCode());
        result = prime * result + (this.feedbackType == null ? 0 : this.feedbackType.hashCode());
        result = prime * result + (this.observee == null ? 0 : this.observee.hashCode());
        result = prime * result + (this.observer == null ? 0 : this.observer.hashCode());
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TechnicalResource getObservee() {
        return observee;
    }

    public void setObservee(TechnicalResource observee) {
        this.observee = observee;
    }

    public Project getRelatedProject() {
        return relatedProject;
    }

    public void setRelatedProject(Project relatedProject) {
        this.relatedProject = relatedProject;
    }

    public TechnicalResource getObserver() {
        return observer;
    }

    public void setObserver(TechnicalResource observer) {
        this.observer = observer;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }
}