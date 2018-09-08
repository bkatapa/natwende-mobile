/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile;

import com.mweka.natwende.mobile.util.MessageHelper;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bell
 */
@Named
@ApplicationScoped
public class NavigationView extends MessageHelper {
    private Date travelDate;
    
    public String gotoSecond() {
        return "pm:second";
    }
    
    public Date getTravelDate() {
        return travelDate;
    }
    
    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }
    
    public void onDateSelect(SelectEvent event) {
        travelDate = (Date) event.getObject();
        RequestContext.getCurrentInstance().update("second");
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#second', {reverse: true, transition: 'slide'});");
    }
}
