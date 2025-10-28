package java19;

import dao.EmployeeDaoImpl;
import models.Employee;
import models.Job;
import service.Impl.JobServiceImpl;
import service.JobService;

import java.util.List;
import java.util.Map;

public class App {
    private static Employee EmployeeDaoImpl;

    public static void main(String[] args) {


        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        JobService jobService = new JobServiceImpl();

        jobService.createJobTable();
        employeeDao.createEmployee();

        Job job1 = new Job("Mentor", "Java Developer", "Backend", 1);
        jobService.addJob(job1);

        Job job2 = new Job("Mentor", "JS Developer", "Fronted", 2);
        jobService.addJob(job2);

        Employee employee1 = new Employee("Nurpazyl", "Nabiev", 20, "Nurpazyl@gmail.com", 1);

        employeeDao.addEmployee(employee1);


        Employee employee2 = new Employee("Adilet", "Adiletov", 20, "Adilet@gmail.com", 2);
        employeeDao.addEmployee(employee2);

        Employee updateEmployee = new Employee("Bek", "Bekov", 18, "Bek@gmail.com", 2);
        employeeDao.updateEmployee(1L, updateEmployee);

        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Employee emailEmployee = employeeDao.findByEmail("Adilet@gmail.com");
        System.out.println("email " + emailEmployee);

        Map<Employee, Job> empJobMap = employeeDao.getEmployeeById(1L);
        empJobMap.forEach((e, j) -> System.out.println(e + " | " + j));
        List<Employee> mentor = employeeDao.getEmployeeByPosition("Mentor");
       mentor.forEach(System.out::println);

        List<Job> sortedJobs = jobService.sortByExperience("desc");
        System.out.println("Jobs sorted by experience:");
        sortedJobs.forEach(System.out::println);

        sortedJobs = jobService.sortByExperience("ASC");
        System.out.println("Jobs sorted by experience:");
        sortedJobs.forEach(System.out::println);

    }

}




