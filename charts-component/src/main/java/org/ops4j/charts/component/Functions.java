package org.ops4j.charts.component;

import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

public class Functions {

    public static String min() {
        if (FacesContext.getCurrentInstance().getApplication().getProjectStage() == ProjectStage.Production) {
            return ".min";
        }
        else {
            return "";
        }
    }
}
