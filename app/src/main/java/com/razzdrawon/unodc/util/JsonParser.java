package com.razzdrawon.unodc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razzdrawon.unodc.model.Item;
import com.razzdrawon.unodc.model.ItemResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mapadi3 on 09/07/17.
 */

public class JsonParser {

    public static List<Item> parseItems(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(json);
        for(JsonNode dataAuxNode : rootNode) {
            items.add(objectMapper.treeToValue(dataAuxNode, Item.class));
        }
        return items;
    }

//    public static String parseFromItemToString (List<Item> items) throws IOException {
//        List<ItemResponse> itemResp = new ArrayList<>();
//        for (Item item: items) {
//
//        }
//    }

}