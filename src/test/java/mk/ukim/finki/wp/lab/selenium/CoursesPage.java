package mk.ukim.finki.wp.lab.selenium;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CoursesPage extends AbstractPage{

    @FindBy(css = "tr[class=course]")
    private List<WebElement> courseRows;

    @FindBy(css = ".delete-btn")
    private List<WebElement> deleteButtons;

    @FindBy(css = ".edit-btn")
    private List<WebElement> editButtons;

    @FindBy(css = ".add-course")
    private List<WebElement> addCourseButton;


    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage to(WebDriver driver) {
        get(driver,"/courses");
        return PageFactory.initElements(driver,CoursesPage.class);
    }
    public void assertElement(int rows,int editBtn,int deleteBtn,int addCourse)
    {
        Assert.assertEquals("rows do not match",courseRows.size(),rows);
        Assert.assertEquals("editBtn do not match",editButtons.size(),editBtn);
        Assert.assertEquals("deleteBtn do not match",deleteButtons.size(),deleteBtn);
        Assert.assertEquals("addCourse do not match",addCourseButton.size(),addCourse);
    }

}
