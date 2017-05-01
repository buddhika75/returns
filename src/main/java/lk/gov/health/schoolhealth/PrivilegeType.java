/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.gov.health.schoolhealth;

/**
 *
 * @author User
 */
public enum PrivilegeType {
    //System Level
    System_Administrator,
    System_Super_User,
    Institution_Administrator,
    Institution_Super_User,
    //Provincial Level
    CCP_PDHS,
    MO_PDHS,
    PSPHI,
    PDHS_Staff,
    //District Level
    CCP_RDHS,
    MO_School_Health,
    MO_RDHS,
    DSPHI,
    RDHS_Staff,
    //MOH Level
    MOH,
    AMOH,
    MO,
    RMO_AMO,
    SPHI,
    MOH_Staff,
    //PHI Level
    PHI,
    PHI_Staff,
    //Guest Level
    Guest,
}
