package io.github.marcocab.movies;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtils {

    private static final String DOUBLE_QUOTES_REGEX = "\\\"\\\"";
    private static final String JSON_ARRAY_INIT_REGEX = "\"\\[";
    private static final String JSON_ARRAY_END_REGEX = "\\]\"";

    private static final String DOUBLE_QUOTES_REGEX_RESULT = "\"";
    private static final String JSON_ARRAY_INIT_REGEX_RESULT = "[";
    private static final String JSON_ARRAY_END_REGEX_RESULT = "]";

    public static String parseToJsonArrayString(String text) {
	return text.replaceAll(DOUBLE_QUOTES_REGEX, DOUBLE_QUOTES_REGEX_RESULT)
		.replaceAll(JSON_ARRAY_INIT_REGEX, JSON_ARRAY_INIT_REGEX_RESULT)
		.replaceAll(JSON_ARRAY_END_REGEX, JSON_ARRAY_END_REGEX_RESULT);
    }

    public static String printAsJsonArray(JSONArray jsonArray) {

	if (jsonArray.size() != 0) {
	    StringBuilder sb = new StringBuilder("\n " + jsonArray.toJSONString().charAt(0) + "\n");
	    @SuppressWarnings("unchecked")
	    Iterator<JSONObject> it = jsonArray.iterator();
	    while (it.hasNext()) {
		JSONObject jsonObject = (JSONObject) it.next();
		sb.append("  {\n    \"id\": " + jsonObject.get("id").toString() + ",\n ");
		sb.append("   \"name\": " + jsonObject.get("name").toString() + "\n  }, \n");
	    }
	    sb.append(" " + jsonArray.toString().charAt(jsonArray.toString().lastIndexOf("]")));
	    return sb.toString();
	} else {
	    return jsonArray.toJSONString();
	}
    }
}
