/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.util;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Bell
 */
public class MessageHelper implements Serializable {
    
    @Inject
    protected transient Logger logger;
    
    public void onMessage(String msgType, String msg) {
        FacesMessage.Severity severity = msgType.equals("info") ? FacesMessage.SEVERITY_INFO
                : msgType.equals("warn") ? FacesMessage.SEVERITY_WARN
                : msgType.equals("error") ? FacesMessage.SEVERITY_ERROR
                : FacesMessage.SEVERITY_FATAL;
        
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(severity, msg, severity == FacesMessage.SEVERITY_INFO ? "Success"
                        : severity == FacesMessage.SEVERITY_WARN ? "Warning"
                        : severity == FacesMessage.SEVERITY_ERROR ? "Error"
                        : "Fatal"));
    }
    
    public void onMessageDlg(String msgType, String msg) {
        FacesMessage.Severity severity;
        String msgTitle;
        switch (msgType) {
            case "info" : severity = FacesMessage.SEVERITY_INFO;
            msgTitle = "Information";
            break;
            case "warn" : severity = FacesMessage.SEVERITY_WARN;
            msgTitle = "Warning";
            break;
            case "error" : severity = FacesMessage.SEVERITY_ERROR;
            msgTitle = "Error";
            break;
            default : severity = FacesMessage.SEVERITY_FATAL;
            msgTitle = "Fatal";
        }
        FacesMessage message = new FacesMessage(severity, msgTitle, msg);        
        RequestContext.getCurrentInstance().showMessageInDialog(message);                
    }
}
