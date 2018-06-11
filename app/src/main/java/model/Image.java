package model;

/**
 * Class that represents an image within the Talent system.
 * It contains the link of the image and the information inherited from
 * {@link BasicEntity} class.
 *
 * @author Elías Calderón
 */
public class Image extends BasicEntity {

    /**
     * Link to get to the image.
     */
    private String link;

	public Image(){}
		
    @Override
    protected boolean onEquals(Object o) {
        boolean result = false;
        if ( o instanceof Image){
            Image image = (Image) o;
            result = (this.link == null ? image.getLink() == null : this.link.equals(image.getLink()));
        }
        return result;
    }

    @Override
    protected int onHashCode(int result) {
        final int prime = 23;
        result = prime * result + (this.link == null ? 0 : this.link.hashCode());
        return result;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}