/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile;

import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class TripMobileView extends MessageHelper {
    
    private List<TripVO> entityList;
    private TripVO selectedEntity;
    private int activeIndex;
    private Date travelDate, travelDateReturn;
    private boolean roundTrip, returnDate, destination;
    
    @PostConstruct
    public void init() {
        selectedEntity = new TripVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
        travelDate = new Date();
        travelDateReturn = new Date();
        //System.out.println(travelDate);
        roundTrip = false;
        returnDate = false;
        destination = false;
    }

    public List<TripVO> getEntityList() {
        loadEntityList();
        return entityList;
    }

    public void setEntityList(List<TripVO> entityList) {
        this.entityList = entityList;
    }

    public TripVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(TripVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public Date getTravelDate() {
        return travelDate;
    }
    
    public String getTravelDateAsString() {
        String travelDateAsString = DateFormat.getDateInstance().format(travelDate);        
        return travelDateAsString;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Date getTravelDateReturn() {
        return travelDateReturn;
    }
    
    public String getTravelDateReturnAsString() {
        String travelDateReturnAsString = DateFormat.getDateInstance().format(travelDateReturn);        
        return travelDateReturnAsString;
    }

    public void setTravelDateReturn(Date travelDateReturn) {
        this.travelDateReturn = travelDateReturn;
    }

    public boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    public boolean isReturnDate() {
        return returnDate;
    }

    public void setReturnDate(boolean returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }
    
    public void toggleRoundTrip() {
        roundTrip = !roundTrip;
        logger.log(Level.CONFIG, "roundTrip is: {0}", roundTrip);
    }
    
    public String searchResult() {
        return "pm:tripList-page";
    }
    
    public String viewEntity() {
        return "pm:seatSelection-page?transition=slide";
    }
    
    public void onSeatSelectionDialogClose() {
        String script = "$('#selected-seats').empty(); sc.find('selected').status('available'); PF('var_ConfirmSeatSelection').hide();";
        RequestContext.getCurrentInstance().execute(script);
    }
    
    public void onRoundTrip() {
        //System.out.println("Roud trip is: " + roundTrip);
    }
    
    public void onDateSelect(SelectEvent event) {
        //Date selectedDate = (Date) event.getObject();
        if (returnDate) {
            returnDate = false;
        }
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#booking-page', {reverse: false, transition: 'slidedown'});");        
        RequestContext.getCurrentInstance().update("booking-page");
    }
    
    public List<String> completeTown(String query) {
        //System.out.println("query: " + query);
        List<String> townList = new ArrayList<>();
        for (Town t : Town.values()) {
            if (query == null || query.trim().isEmpty()) {
                continue;
            }
            if (StringUtils.startsWithIgnoreCase(t.getDisplay(), query)) {
                townList.add(t.getDisplay());
            }
        }
        //System.out.println(townList);
        return townList;
    }
    
    public void onTownSelect(SelectEvent event) {
        if (logger != null) {
            logger.log(Level.CONFIG, "event.getObject() [{0}]", event.getObject());
        }
        if (event.getObject() == null) {
            return;
        }
        if (destination) {
            destination = false;
        }        
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#booking-page', {reverse: false, transition: 'slidedown'});");        
        RequestContext.getCurrentInstance().update("booking-page");
    }
    
    private void loadEntityList() {
        entityList.clear();
        // read trips from web service call
    }
}
