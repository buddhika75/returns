package lk.gov.health.beans;

import lk.gov.health.schoolhealth.ReturnSubmission;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.ReturnSubmissionFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import lk.gov.health.schoolhealth.AreaType;
import lk.gov.health.schoolhealth.Month;
import lk.gov.health.schoolhealth.Quarter;
import lk.gov.health.schoolhealth.ReturnFormat;
import lk.gov.health.schoolhealth.ReturnTimeFrequency;

@Named("returnSubmissionController")
@SessionScoped
public class ReturnSubmissionController implements Serializable {

    @Inject
    WebUserController webUserController;
    @EJB
    private lk.gov.health.faces.ReturnSubmissionFacade ejbFacade;
    private List<ReturnSubmission> items = null;
    private ReturnSubmission selected;

    ReturnFormat returnFormat;
    int year;
    Month month;
    Quarter quarter;
    Date date;
    
    
    public String toReceiveReturns(){
        return "/returnSubmission/receive_returns";
    }
    
    public String toSubmitNewReport(){
        selected = new ReturnSubmission();
        return "/returnSubmission/submit_new_report";
    }
    
    public String toSubmitNewReportAfterSelection(){
        if(returnFormat==null){
            JsfUtil.addErrorMessage("Select a Return");
            return "";
        }
        selected.setReturnFormat(returnFormat);
        selected.setSentDate(new Date());
        
        if(selected.getReturnFormat().getSendingAreaType()==null || selected.getReturnFormat().getSendingAreaType()==AreaType.MOH){
            selected.setSentArea(webUserController.getLoggedMohArea());
        }
        if(selected.getReturnFormat().getReceivingAreaType()==null || selected.getReturnFormat().getReceivingAreaType()==AreaType.District){
            selected.setReceiveArea(webUserController.getLoggedRdhsArea());
        }
        
        selected.setReceiveArea(webUserController.getLoggedRdhsArea());
        
        if(null==selected.getReturnFormat().getFrequency()){
            return "Still Under Development";
        }else switch (returnFormat.getFrequency()) {
            case Annual:
                selected.setReturnYear(webUserController.getLastYear());
                break;
            case Quarterly:
                selected.setQuarter(webUserController.getLastQuarterFromDate(new Date()));
                if(selected.getQuarter()==Quarter.Forth){
                    selected.setReturnYear(webUserController.getLastYear());
                }else{
                    selected.setReturnYear(webUserController.getThisYear());
                }   break;
            case Monthly:
                selected.setReturnMonth(webUserController.getLastMonth(new Date()));
                if(selected.getReturnMonth()==Month.December){
                    selected.setReturnYear(webUserController.getLastYear());
                }else{
                    selected.setReturnYear(webUserController.getThisYear());
                }   break;
            case Weekely:
                return "Still Under Development";
            default:
                return "Still Under Development";
        }
        return "/returnSubmission/submit_new_report_selected";
    }
    
    public String submitReturn(){
        
        if(selected.getId()==null){
            selected.setSentBy(webUserController.getLoggedUser());
            getFacade().create(selected);
            JsfUtil.addSuccessMessage("Submitted");
        }else{
            getFacade().edit(selected);
            JsfUtil.addSuccessMessage("Submission Updated");
        }
        return "/index";
    }
    
    
    public ReturnSubmissionController() {
    }

    public ReturnSubmission getSelected() {
        return selected;
    }

    public void setSelected(ReturnSubmission selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ReturnSubmissionFacade getFacade() {
        return ejbFacade;
    }

    public ReturnSubmission prepareCreate() {
        selected = new ReturnSubmission();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleReturns").getString("ReturnSubmissionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleReturns").getString("ReturnSubmissionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleReturns").getString("ReturnSubmissionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ReturnSubmission> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleReturns").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleReturns").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ReturnSubmission getReturnSubmission(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ReturnSubmission> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ReturnSubmission> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public ReturnFormat getReturnFormat() {
        return returnFormat;
    }

    public void setReturnFormat(ReturnFormat returnFormat) {
        this.returnFormat = returnFormat;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public Date getDate() {
        if(date==null){
            date = new Date();
        }
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    
    @FacesConverter(forClass = ReturnSubmission.class)
    public static class ReturnSubmissionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReturnSubmissionController controller = (ReturnSubmissionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "returnSubmissionController");
            return controller.getReturnSubmission(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ReturnSubmission) {
                ReturnSubmission o = (ReturnSubmission) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ReturnSubmission.class.getName()});
                return null;
            }
        }

    }

}
