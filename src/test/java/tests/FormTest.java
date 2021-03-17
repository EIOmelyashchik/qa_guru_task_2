package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    void checkForm() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String email = "Ivanov@mail.com";
        String gender = "Male";
        String mobileNumber = "1234567890";
        Calendar dateOfBirth = new GregorianCalendar(1999, Calendar.AUGUST, 25);
        List<String> subjects = Arrays.asList("Maths", "Biology", "English");
        List<String> hobbies = Arrays.asList("Sports", "Music");
        String picture = "picture.png";
        String address = "455 Main st";
        String state = "Uttar Pradesh";
        String city = "Lucknow";

        open("https://demoqa.com/automation-practice-form");
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").find(byText(gender)).click();
        $("#userNumber").setValue(mobileNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOptionByValue(String.valueOf(dateOfBirth.get(Calendar.MONTH)));
        $(".react-datepicker__year-select").selectOptionByValue(String.valueOf(dateOfBirth.get(Calendar.YEAR)));
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd", Locale.ENGLISH);
        String dayCalendarLocator = String.format("[aria-label*='%sth']", formatter.format(dateOfBirth.getTime()));
        $(dayCalendarLocator).click();
        subjects.forEach(subject ->
                $("#subjectsInput").setValue(subject).pressEnter());
        hobbies.forEach(hobby ->
                $("#hobbiesWrapper").find(byText(hobby)).click());
        $("#uploadPicture").uploadFromClasspath(picture);
        $("#currentAddress").setValue(address);
        $("#state").click();
        $("#state > div[class*='menu']").find(byText(state)).click();
        $("#city").click();
        $("#city > div[class*='menu']").find(byText(city)).click();
        $("#submit").click();

        //todo how does it write down with css?
        $x("//td[text()='Student Name']/following-sibling::td").shouldHave(text(firstName), text(lastName));
        $x("//td[text()='Student Email']/following-sibling::td").shouldHave(text(email));
        $x("//td[text()='Gender']/following-sibling::td").shouldHave(text(gender));
        $x("//td[text()='Mobile']/following-sibling::td").shouldHave(text(mobileNumber));
        String dateOfBirthExpected = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH).format(dateOfBirth.getTime());
        $x("//td[text()='Date of Birth']/following-sibling::td").shouldHave(text(dateOfBirthExpected));
        subjects.forEach(subject ->
                $x("//td[text()='Subjects']/following-sibling::td").shouldHave(text(subject)));
        hobbies.forEach(hobby ->
                $x("//td[text()='Hobbies']/following-sibling::td").shouldHave(text(hobby)));
        $x("//td[text()='Picture']/following-sibling::td").shouldHave(text(picture));
        $x("//td[text()='Address']/following-sibling::td").shouldHave(text(address));
        $x("//td[text()='State and City']/following-sibling::td").shouldHave(text(state), text(city));
    }
}
