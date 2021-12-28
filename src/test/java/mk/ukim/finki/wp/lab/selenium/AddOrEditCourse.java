package mk.ukim.finki.wp.lab.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditCourse extends AbstractPage{
    private WebElement name;
    private WebElement description;
    private WebElement teacherId;
    private WebElement submit;

    public AddOrEditCourse(WebDriver driver) {
        super(driver);
    }
    public static CoursesPage addCourse(WebDriver driver,String name,String description){
        get(driver,"courses/addCourse");
        AddOrEditCourse addOrEditCourse = PageFactory.initElements(driver,AddOrEditCourse.class);
        addOrEditCourse.name.sendKeys(name);
        addOrEditCourse.description.sendKeys(description);
//        Select select = new Select(driver.findElement(By.name("teacherId")));
//        select.selectByIndex(1);
//        Select selectType = new Select(addOrEditEvent.type);
//        selectType.selectByValue(type);
        addOrEditCourse.submit.click();
        return PageFactory.initElements(driver,CoursesPage.class);
    }
//    @Test
//    public void test_create_10pt() {
//        SubmissionHelper.startTest("test-create-10");
//        List<NewsCategory> categories = this.newsCategoryService.listAll();
//        List<News> entities = this.service.listAllNews();
//
//        int itemNum = entities.size();
//        ItemsPage listPage = null;
//
//        try {
//            LoginPage loginPage = LoginPage.openLogin(this.driver);
//            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
//        } catch (Exception e) {
//        }
//        listPage = AddOrEditEvent.add(this.driver, ADD_URL, "testName", "testDescription", "100", NewsType.PROMOTION.name(), categories.get(0).getId().toString());
//        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
//        listPage.assertNoError();
//        listPage.assertItems(itemNum + 1);
//
//        SubmissionHelper.endTest();
//    }
}
