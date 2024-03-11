import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonParser {

    public List<Map<String, String>> parse(String json) {

        Type conteudoListType = new TypeToken<ArrayList<Map<String, String>>>(){}.getType();
        List<Map<String, String>> conteudos = new Gson().fromJson(json, conteudoListType);

        return conteudos;
    }

}
