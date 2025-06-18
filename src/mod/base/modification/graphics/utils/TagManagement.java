package mod.base.modification.graphics.utils;

import javafx.scene.Node;

public class TagManagement {
    public static void addTagInId (Node n,String tag) {
        if (tag != null && !tag.isEmpty()) {
            String oldId = n.getId();
            //System.out.println("old id \"" + oldId + ", add tag \"" + tag);
            if (oldId == null) {
                n.setId(tag + ";");
            } else {
                if (!oldId.endsWith(";"))
                    oldId += ";";
                String completeTag = oldId + tag + ";";
                //System.out.println("new tag \"" + completeTag);
                n.setId(completeTag);
            }
        }
    }
}
