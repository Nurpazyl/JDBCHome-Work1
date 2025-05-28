package java17;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import dao.JobDaoImpl;
import db.DBConnection;
import models.Employee;
import models.Job;
import service.Impl.JobServiceImpl;
import service.JobService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello world!
 */
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

        Employee employee1 = new Employee("Junusbek", "Amanov", 21, "Junusbek@gmail.com", 1);

        employeeDao.addEmployee(employee1);


        Employee employee2 = new Employee("Adilet", "Adiletov", 21, "Adilet@gmail.com", 2);
        employeeDao.addEmployee(employee2);

        Employee updateEmployee = new Employee("Baitenir", "Busurmankulov", 18, "Baitenir@gmail.com", 2);
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


//        jobService.deleteDescriptionColumn();
//
//        employeeDao.cleanTable();
//        employeeDao.cleanTable();
//
//        employeeDao.dropTable();
//        employeeDao.dropTable();

    }

}




