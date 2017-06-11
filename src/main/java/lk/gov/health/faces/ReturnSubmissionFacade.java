/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.faces;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lk.gov.health.schoolhealth.ReturnSubmission;

/**
 *
 * @author User
 */
@Stateless
public class ReturnSubmissionFacade extends AbstractFacade<ReturnSubmission> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReturnSubmissionFacade() {
        super(ReturnSubmission.class);
    }
    
}
