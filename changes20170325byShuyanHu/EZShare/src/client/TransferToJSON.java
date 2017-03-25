/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Administrator
 */
public class TransferToJSON {

    String nameString = "";
    String tagsString = "[";
    String descriptionString = "";
    String uriString = "";
    String channelString = "";
    String ownerString = "";
    String ezserverString = "";
    String resourceSizeString = "";
    String JSONString = "";

    public String TransferToJSON(String message) {
        String[] messageArray = message.split("-");
        //return messageArray[1];
        for (int i = 1; i < messageArray.length; i++) {
            String[] messageArray2 = messageArray[i].split(" ");
            //return messageArray2[0];
            if (messageArray2[0].equals("name")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    nameString = nameString + messageArray2[i2] + " ";
                }
                nameString = nameString.substring(0, nameString.length() - 1);
            }
            if (messageArray2[0].equals("tags")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    tagsString = tagsString + "\"" + messageArray2[1] + "\",";
                }
                tagsString = tagsString.substring(0, tagsString.length() - 1);
            }
            if (messageArray2[0].equals("description")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    descriptionString = descriptionString + messageArray2[i2] + " ";
                }
                descriptionString = descriptionString.substring(0, descriptionString.length() - 1);
            }
            if (messageArray2[0].equals("uri")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    uriString = uriString + messageArray2[i2] + " ";
                }
                uriString = uriString.substring(0, uriString.length() - 1);
            }
            if (messageArray2[0].equals("channel")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    channelString = channelString + messageArray2[i2] + " ";
                }
                channelString = channelString.substring(0, channelString.length() - 1);
            }
            if (messageArray2[0].equals("owner")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    ownerString = ownerString + messageArray2[i2] + " ";
                }
                ownerString = ownerString.substring(0, ownerString.length() - 1);
            }

            if (messageArray2[0].equals("ezserver")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    ezserverString = ezserverString + messageArray2[i2] + " ";
                }
                ezserverString = ezserverString.substring(0, ezserverString.length() - 1);
            }

            if (messageArray2[0].equals("resourceSize")) {
                for (int i2 = 1; i2 < messageArray2.length; i2++) {
                    resourceSizeString = resourceSizeString + messageArray2[i2] + " ";
                }
                resourceSizeString = resourceSizeString.substring(0, resourceSizeString.length() - 1);
            }

        }
        tagsString = tagsString + "]";

        JSONString = "{" + "\"name\":" + "\"" + nameString + "\"" + ","
                + "\"tags\":" + tagsString + ","
                + "\"description\":" + "\"" + descriptionString + "\"" + ","
                + "\"uri\":" + "\"" + uriString + "\"" + ","
                + "\"channel\":" + "\"" + channelString + "\"" + ","
                + "\"owner\":" + "\"" + ownerString + "\"" + ","
                + "\"ezserver\":" + "\"" + ezserverString + "\"" + ","
                + "\"resourceSize\":" + "\"" + resourceSizeString + "\""
                + "}";

        return JSONString;
    }
}
