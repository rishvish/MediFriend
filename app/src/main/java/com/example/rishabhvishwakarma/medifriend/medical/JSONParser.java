package com.example.rishabhvishwakarma.medifriend.medical;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


class JSONParser extends ArrayList<PageBeans> {

    /***Parsing First aid page*/
    ArrayList<PageBeans> parseFirstAidJSON(JSONObject jsonObject, String topic){
        JSONArray jFieldArray;
        JSONArray jFinalArray = null;

        if (jsonObject != null) {
            try {
                    jFieldArray = jsonObject.getJSONArray("First_aid");
                    JSONObject jTopicObject = jFieldArray.getJSONObject(0);
                    if (topic != null) {
                        String t = topic.replace(" ", "_");
                        jFinalArray = jTopicObject.getJSONArray(t);
                    } else {
                        return null;
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getAllParagraphs(jFinalArray, topic);
        }  else {
            return null;
        }
    }

    private ArrayList<PageBeans> getAllParagraphs(JSONArray jsonArray, String topic){
        int paraCount;
        try {
            paraCount = jsonArray.length();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        ArrayList<PageBeans> topicList = new ArrayList<>();
        PageBeans pageBeans;

        for (int i = 0; i < paraCount; i++){
            try {
                pageBeans = getHeadingAndContent((JSONObject) jsonArray.get(i), topic);
                topicList.add(pageBeans);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return topicList;
    }

    private PageBeans getHeadingAndContent(JSONObject jsonObject, String topic){
        PageBeans pageBeans = new PageBeans();
        String heading;
        String content;
        try {
            heading = jsonObject.getString("heading");
            content = jsonObject.getString("content");

            pageBeans.setTopic(topic);
            pageBeans.setHeading(heading);
            pageBeans.setContent(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pageBeans;
    }

    /***Parsing List Of Conditions*/
    ArrayList<ConditionReferenceBeans> parseSingleConditionList(JSONObject jsonObject, String bodyPart, String selectedSymptom) {

        JSONObject bodyPartObject;
        JSONObject finalObject = null;

        if (jsonObject != null) {
            try {
                if (bodyPart != null) {
                    bodyPart = bodyPart.replace(" ", "_");
                    bodyPartObject = jsonObject.getJSONObject(bodyPart);
                    if (selectedSymptom != null) {
                        selectedSymptom = selectedSymptom.replace(" ", "_");
                        finalObject = bodyPartObject.getJSONObject(selectedSymptom);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (finalObject != null) {
                ArrayList<ConditionReferenceBeans> bodyPartList = new ArrayList<>();

                bodyPartList.add(getWarningAndCondition(finalObject, selectedSymptom));

                return bodyPartList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    ArrayList<ConditionReferenceBeans> parseAllConditionsList(JSONObject jsonObject, String bodyPart, String[] selectedSymptoms) {

        JSONObject bodyPartObject;
        JSONObject[] finalObjects = new JSONObject[selectedSymptoms.length];
        ArrayList<ConditionReferenceBeans> bodyPartList = new ArrayList<>();

        if (jsonObject != null) {
            try {
                if (bodyPart != null) {
                    bodyPart = bodyPart.replace(" ", "_");
                    bodyPartObject = jsonObject.getJSONObject(bodyPart);
                    for (int i = 0; i < selectedSymptoms.length; i++) {
                        selectedSymptoms[i] = selectedSymptoms[i].replace(" ", "_");
                        finalObjects[i] = bodyPartObject.getJSONObject(selectedSymptoms[i]);

                        bodyPartList.add(getWarningAndCondition(finalObjects[i], selectedSymptoms[i]));
                    }
                } else {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return bodyPartList;
        } else {
            return null;
        }
    }

    private ConditionReferenceBeans getWarningAndCondition(JSONObject jsonObject, String selectedSymptom) {
        ConditionReferenceBeans conditionReferenceBeans = new ConditionReferenceBeans();
        String warning;
        String conditions;
        try {
            warning = jsonObject.getString("warning");
            conditions = jsonObject.getString("conditions");

            conditionReferenceBeans.setSymptom(selectedSymptom);
            conditionReferenceBeans.setWarning(warning);
            conditionReferenceBeans.setConditions(conditions);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return conditionReferenceBeans;
    }

    ArrayList<PageBeans> parseConditionPage(JSONObject jsonObject, String selectedCondition) {
        JSONArray selectedConditionArray;
        ArrayList<PageBeans> conditionsPage = new ArrayList<>();
        try {
            selectedConditionArray = jsonObject.getJSONArray(selectedCondition.replace(" ", "_"));
            for (int i = 0; i < selectedConditionArray.length(); i++) {
                conditionsPage.add(getHeadingAndContent((JSONObject) selectedConditionArray.get(i), selectedCondition));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return conditionsPage;
    }
}