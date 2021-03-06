package com.selenium.course.testng;

import com.selenium.course.pages.*;
import org.testng.annotations.*;
import static org.junit.Assert.assertTrue;


public class CreateOpportunity {
    private LoginPage loginPage;
    private MainApp mainApp;
    private PageMenuBar pageMenuBar;
    private OpportunitiesPage opportunitiesPage;
    private NewOpportunityForm newOpportunityForm;
    private OpportunityDetail opportunityDetail;
    String name,stage,type,leadSource, amount, nextstep;

    @BeforeClass
    public void setUp() {
        loginPage = new LoginPage();
        mainApp = loginPage.loginAsPrimaryUser();
        pageMenuBar = mainApp.goToPageMenuBar();
        opportunitiesPage = pageMenuBar.clickOpportunities();
    }

    @Test
    public void testUntitled() {
        name = "NewOpportunity";
        stage = "Prospecting";
        type ="New Customer";
        leadSource = "Web";
        amount = "15";
        nextstep = "Step";
        newOpportunityForm = opportunitiesPage.clickNewOpportunity();
        opportunityDetail = newOpportunityForm.setName(name)
                .setPrivate()
                .setType(type)
                .setLeadSource(leadSource)
                .setAmount(amount)
                .setCloseDate()
                .setNextStep(nextstep)
                .setStage(stage)
                .clickSaveOpportunity();
        assertTrue("Verification Failed:The Opportunity was not created",opportunityDetail.verifyNewOpportunityName(name));
        }

    @AfterClass
    public void tearDown() {
        opportunityDetail.deleteOpportunity();
    }
}
