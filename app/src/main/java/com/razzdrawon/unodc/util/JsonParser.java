package com.razzdrawon.unodc.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razzdrawon.unodc.model.Item;
import com.razzdrawon.unodc.model.ItemResponse;
import com.razzdrawon.unodc.model.ObjectSync;
import com.razzdrawon.unodc.model.Option;

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

    public static String parseFromItemsToResponse (List<Item> items) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();


        List<ItemResponse> itemResp = new ArrayList<>();
        for (Item item: items) {
            ItemResponse ir = new ItemResponse();
            ir.setQstnNbr(item.getQstnNbr());

            if(item.getOpenAnswer() != null)
            ir.setOpenAns(item.getOpenAnswer());


            List<String> opts = new ArrayList<>();

            if(item.getOptions() != null && item.getOptions().size() > 0) {
                for(Option opt : item.getOptions()){
                    if(opt.getChosen()) {
                        opts.add(opt.getOpt());
                        if (opt.getDependentChosen() != null){
                            ir.setDepOpt(opt.getDependentChosen());
                        }
                        if (opt.getOpenAnswer() != null){
                            ir.setDepOpenAns(opt.getOpenAnswer());
                        }
                    }
                }
            }
            ir.setOptsAns(opts);

            itemResp.add(ir);
        }

        String jsonResp = objectMapper.writeValueAsString(itemResp);

        return jsonResp;
    }

    public static ObjectSync parseObjSync(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectSync objSync = (objectMapper.readValue(json, ObjectSync.class));
        return objSync;
    }

    public static List<ObjectSync> parseListObjSync(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<ObjectSync> listObjsSync = new ArrayList<>();

        JsonNode rootNode = objectMapper.readTree(json);

        for(JsonNode dataAuxNode : rootNode){
            listObjsSync.add(objectMapper.treeToValue(dataAuxNode, ObjectSync.class));
        }

        return listObjsSync;
    }

    public static String parseObjSynctoString(ObjectSync item) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResp = objectMapper.writeValueAsString(item);
        return jsonResp;
    }
//
//    public static String parseListObjSynctoString(List<ObjectSync> items) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonResp = objectMapper.writeValueAsString(items);
//        return jsonResp;
//    }

}
