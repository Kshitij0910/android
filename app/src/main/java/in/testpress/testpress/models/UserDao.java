package in.testpress.testpress.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import in.testpress.testpress.models.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Username = new Property(2, String.class, "username", false, "USERNAME");
        public final static Property FirstName = new Property(3, String.class, "firstName", false, "FIRST_NAME");
        public final static Property LastName = new Property(4, String.class, "lastName", false, "LAST_NAME");
        public final static Property DisplayName = new Property(5, String.class, "displayName", false, "DISPLAY_NAME");
        public final static Property Photo = new Property(6, String.class, "photo", false, "PHOTO");
        public final static Property LargeImage = new Property(7, String.class, "largeImage", false, "LARGE_IMAGE");
        public final static Property MediumImage = new Property(8, String.class, "mediumImage", false, "MEDIUM_IMAGE");
        public final static Property MediumSmallImage = new Property(9, String.class, "mediumSmallImage", false, "MEDIUM_SMALL_IMAGE");
        public final static Property SmallImage = new Property(10, String.class, "smallImage", false, "SMALL_IMAGE");
        public final static Property XSmallImage = new Property(11, String.class, "xSmallImage", false, "X_SMALL_IMAGE");
        public final static Property MiniImage = new Property(12, String.class, "miniImage", false, "MINI_IMAGE");
        public final static Property Followers_count = new Property(13, Integer.class, "followers_count", false, "FOLLOWERS_COUNT");
        public final static Property Following_count = new Property(14, Integer.class, "following_count", false, "FOLLOWING_COUNT");
        public final static Property Following = new Property(15, Integer.class, "following", false, "FOLLOWING");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"URL\" TEXT," + // 1: url
                "\"USERNAME\" TEXT," + // 2: username
                "\"FIRST_NAME\" TEXT," + // 3: firstName
                "\"LAST_NAME\" TEXT," + // 4: lastName
                "\"DISPLAY_NAME\" TEXT," + // 5: displayName
                "\"PHOTO\" TEXT," + // 6: photo
                "\"LARGE_IMAGE\" TEXT," + // 7: largeImage
                "\"MEDIUM_IMAGE\" TEXT," + // 8: mediumImage
                "\"MEDIUM_SMALL_IMAGE\" TEXT," + // 9: mediumSmallImage
                "\"SMALL_IMAGE\" TEXT," + // 10: smallImage
                "\"X_SMALL_IMAGE\" TEXT," + // 11: xSmallImage
                "\"MINI_IMAGE\" TEXT," + // 12: miniImage
                "\"FOLLOWERS_COUNT\" INTEGER," + // 13: followers_count
                "\"FOLLOWING_COUNT\" INTEGER," + // 14: following_count
                "\"FOLLOWING\" INTEGER);"); // 15: following
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(3, username);
        }
 
        String firstName = entity.getFirstName();
        if (firstName != null) {
            stmt.bindString(4, firstName);
        }
 
        String lastName = entity.getLastName();
        if (lastName != null) {
            stmt.bindString(5, lastName);
        }
 
        String displayName = entity.getDisplayName();
        if (displayName != null) {
            stmt.bindString(6, displayName);
        }
 
        String photo = entity.getPhoto();
        if (photo != null) {
            stmt.bindString(7, photo);
        }
 
        String largeImage = entity.getLargeImage();
        if (largeImage != null) {
            stmt.bindString(8, largeImage);
        }
 
        String mediumImage = entity.getMediumImage();
        if (mediumImage != null) {
            stmt.bindString(9, mediumImage);
        }
 
        String mediumSmallImage = entity.getMediumSmallImage();
        if (mediumSmallImage != null) {
            stmt.bindString(10, mediumSmallImage);
        }
 
        String smallImage = entity.getSmallImage();
        if (smallImage != null) {
            stmt.bindString(11, smallImage);
        }
 
        String xSmallImage = entity.getXSmallImage();
        if (xSmallImage != null) {
            stmt.bindString(12, xSmallImage);
        }
 
        String miniImage = entity.getMiniImage();
        if (miniImage != null) {
            stmt.bindString(13, miniImage);
        }
 
        Integer followers_count = entity.getFollowers_count();
        if (followers_count != null) {
            stmt.bindLong(14, followers_count);
        }
 
        Integer following_count = entity.getFollowing_count();
        if (following_count != null) {
            stmt.bindLong(15, following_count);
        }
 
        Integer following = entity.getFollowing();
        if (following != null) {
            stmt.bindLong(16, following);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // username
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // firstName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // lastName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // displayName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // photo
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // largeImage
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // mediumImage
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // mediumSmallImage
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // smallImage
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // xSmallImage
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // miniImage
            cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // followers_count
            cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14), // following_count
            cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15) // following
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUsername(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFirstName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLastName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDisplayName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPhoto(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLargeImage(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMediumImage(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMediumSmallImage(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSmallImage(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setXSmallImage(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMiniImage(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFollowers_count(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setFollowing_count(cursor.isNull(offset + 14) ? null : cursor.getInt(offset + 14));
        entity.setFollowing(cursor.isNull(offset + 15) ? null : cursor.getInt(offset + 15));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
