package in.testpress.testpress.models;

import in.testpress.testpress.models.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "POST".
 */
public class Post {

    private Long id;
    private String title;
    private String summary;
    private String contentHtml;
    private String url;
    private String publishedDate;
    private Long published;
    private String modified;
    private Integer institute;
    private Boolean is_active;
    private Long modifiedDate;
    private String short_web_url;
    private String short_url;
    private String shortLink;
    private String web_url;
    private Integer commentsCount;
    private String commentsUrl;
    private String coverImage;
    private Long categoryId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PostDao myDao;

    public Category category;
    private Long category__resolvedKey;


    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Post(Long id, String title, String summary, String contentHtml, String url, String publishedDate, Long published, String modified, Integer institute, Boolean is_active, Long modifiedDate, String short_web_url, String short_url, String shortLink, String web_url, Integer commentsCount, String commentsUrl, String coverImage, Long categoryId) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.contentHtml = contentHtml;
        this.url = url;
        this.publishedDate = publishedDate;
        this.published = published;
        this.modified = modified;
        this.institute = institute;
        this.is_active = is_active;
        this.modifiedDate = modifiedDate;
        this.short_web_url = short_web_url;
        this.short_url = short_url;
        this.shortLink = shortLink;
        this.web_url = web_url;
        this.commentsCount = commentsCount;
        this.commentsUrl = commentsUrl;
        this.coverImage = coverImage;
        this.categoryId = categoryId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPostDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getPublished() {
        return published;
    }

    public void setPublished(Long published) {
        this.published = published;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getInstitute() {
        return institute;
    }

    public void setInstitute(Integer institute) {
        this.institute = institute;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getShort_web_url() {
        return short_web_url;
    }

    public void setShort_web_url(String short_web_url) {
        this.short_web_url = short_web_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /** To-one relationship, resolved on first access. */
    public Category getCategory() {
        Long __key = this.categoryId;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
            	category__resolvedKey = __key;
            }
        }
        return category;
    }

    public void setCategory(Category category) {
        synchronized (this) {
            this.category = category;
            categoryId = category == null ? null : category.getId();
            category__resolvedKey = categoryId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
