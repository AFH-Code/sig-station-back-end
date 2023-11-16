package com.sprintpay.projetsig.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrantedAuthorityDeserializer extends JsonDeserializer<List<GrantedAuthority>> {

    @Override
    public List<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<String> authorityList = jsonParser.readValueAs(List.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String authority : authorityList) {
            // Créez une instance de la classe SimpleGrantedAuthority en fonction de la valeur de l'autorité
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
    }
}
