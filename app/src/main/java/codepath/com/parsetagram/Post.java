package codepath.com.parsetagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public Post() {
        super();
    }

    // Add a constructor that contains core properties
    public Post(String body) {
        super();
        setBody(body);
    }

    // Use getString and others to access fields
    public String getBody() {
        return getString("body");
    }

    // Use put to modify field values
    public void setBody(String value) {
        put("body", value);
    }

    public ParseFile getMedia() {
        return getParseFile("media");
    }

    public void setMedia(ParseFile parseFile) {
        put("media", parseFile);
    }

    // Associate each item with a user
    public void setOwner(ParseUser user) {
        put("owner", user);
    }

//    @Override
//    public Date getCreatedAt() {
//        return getDate("createdAt");
//    }
}
