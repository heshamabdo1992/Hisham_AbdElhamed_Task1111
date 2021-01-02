package APITest.API;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "userId",
    "balance",
    "consumption",
    "remainder"
})
public class JsonParser {

    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("userId")
    public Integer getuserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setType(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("id")
    public Integer getid() {
        return id;
    }

    @JsonProperty("id")
    public void setid(Integer id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String gettitle() {
        return title;
    }

    @JsonProperty("title")
    public void settitle(String title) {
        this.title = title;
    }

    @JsonProperty("body")
    public String getbody() {
        return body;
    }

    @JsonProperty("body")
    public void setbody(String body) {
        this.body = body;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
