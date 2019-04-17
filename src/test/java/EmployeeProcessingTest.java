import com.internship.coera.entity.Employee;
import com.internship.coera.entity.HolidayRightsPerYear;
import com.internship.coera.entity.Output;
import com.internship.coera.entity.SuspensionPeriod;
import com.internship.coera.service.EmployeeServiceImplementation;
import com.internship.coera.service.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeProcessingTest {
    private Employee employee;

    @Before
    public void initData(){
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse("2019-01-01");
            Date endDate = simpleDateFormat.parse("2019-12-31");
            Date startSuspensionDate = simpleDateFormat.parse("2019-03-01");
            Date endSuspensionDate = simpleDateFormat.parse("2019-08-31");
            SuspensionPeriod suspensionPeriod = new SuspensionPeriod(new java.sql.Date(startSuspensionDate.getTime()), new java.sql.Date(endSuspensionDate.getTime()));
            List<SuspensionPeriod> suspensionPeriodList = new ArrayList<>();
            suspensionPeriodList.add(suspensionPeriod);
            employee = new Employee(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()), suspensionPeriodList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void processEmployeeTest(){
        EmployeeService service = new EmployeeServiceImplementation();
        Output output = service.calculateHolidayDays(employee);
        List<HolidayRightsPerYear> holidayRightsPerYearList = output.getHolidayRightPerYearList();
        Assert.assertNotNull(output);
        Assert.assertNotNull(holidayRightsPerYearList);
        Assert.assertNull(output.getErrorMessage());
        Assert.assertEquals(2019, holidayRightsPerYearList.get(0).getYear());
        Assert.assertEquals(10, holidayRightsPerYearList.get(0).getHolidayDays());
    }
}
