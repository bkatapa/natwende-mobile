/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.view.TripMobileView;
import com.mweka.natwende.mobile.util.MessageHelper;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bell
 */
@Named
@ApplicationScoped
public class NavigationHandler extends MessageHelper {
    
    public String bookingCalendarScreen() {
        return "pm:booking-page-calendarScreen?transition=slideup";
    }
    
    public String bookingSearchScreen() {
        return "pm:booking-page?transition=slide";
    }
    
    public String loginScreen() {
        return "pm:login-page?transition=slide&reverse=true";
    }
    
    public String registerScreen() {
        return "pm:register-page?transition=slideUp";
    }
    
    public void onDateSelect(SelectEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);        
        if (session != null) {
            TripMobileView tripMobileView = (TripMobileView) session.getAttribute("tripMobileView");
            Date selectedDate = (Date) event.getObject();
            if (tripMobileView == null) {
                tripMobileView = new TripMobileView();
                tripMobileView.init();
                session.setAttribute("tripMobileView", tripMobileView);
            }
            if (tripMobileView.isReturnDate()) {
                tripMobileView.setTravelDateReturn(selectedDate);
                tripMobileView.setReturnDate(false);
            }
            else {
                tripMobileView.setTravelDate(selectedDate);
            }
            RequestContext.getCurrentInstance().update("booking-page");
            RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#booking-page', {reverse: false, transition: 'slidedown'});");
        }
        else {
            try {
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getRequest()).sendRedirect("/natwende-mobile");
            }
            catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
                onMessage("error", ex.getMessage());
            }
        }
    }
}
