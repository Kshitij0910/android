package in.testpress.testpress.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USER".
 */
public class User {

    private Long id;
    private String url;
    private String username;
    private String firstName;
    private String lastName;
    private String displayName;
    private String photo;
    private String largeImage;
    private String mediumImage;
    private String mediumSmallImage;
    private String smallImage;
    private String xSmallImage;
    private String miniImage;
    private Integer followers_count;
    private Integer following_count;
    private Integer following;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String url, String username, String firstName, String lastName, String displayName, String photo, String largeImage, String mediumImage, String mediumSmallImage, String smallImage, String xSmallImage, String miniImage, Integer followers_count, Integer following_count, Integer following) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.photo = photo;
        this.largeImage = largeImage;
        this.mediumImage = mediumImage;
        this.mediumSmallImage = mediumSmallImage;
        this.smallImage = smallImage;
        this.xSmallImage = xSmallImage;
        this.miniImage = miniImage;
        this.followers_count = followers_count;
        this.following_count = following_count;
        this.following = following;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getMediumSmallImage() {
        return mediumSmallImage;
    }

    public void setMediumSmallImage(String mediumSmallImage) {
        this.mediumSmallImage = mediumSmallImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getXSmallImage() {
        return xSmallImage;
    }

    public void setXSmallImage(String xSmallImage) {
        this.xSmallImage = xSmallImage;
    }

    public String getMiniImage() {
        return miniImage;
    }

    public void setMiniImage(String miniImage) {
        this.miniImage = miniImage;
    }

    public Integer getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(Integer followers_count) {
        this.followers_count = followers_count;
    }

    public Integer getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(Integer following_count) {
        this.following_count = following_count;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

}
