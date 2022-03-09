package com.ljn.server.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: ljn
 * @Date: 2022/03/01/18:25
 * @Description:自定义Authority解析器
 */
public class CustomAuthorityDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper mapper= (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        List<GrantedAuthority> grantedAuthorityList = new LinkedList<>();
        Iterator<JsonNode> elements= jsonNode.elements();
        while (elements.hasNext()){
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            grantedAuthorityList.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return grantedAuthorityList;
    }
}
