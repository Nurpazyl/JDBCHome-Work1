package dao;

import db.DBConnection;
import models.Employee;
import models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = DBConnection.getConnection();


    @Override
    public void createEmployee() {
        String sql = """
                 CREATE TABLE IF NOT EXISTS employees (
                 id serial primary key,
                 firstName varchar,
                 lastName varchar,
                 age int,
                 email varchar,
                 job_id int references jobs(id)
                )
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            statement.close();
            System.out.println("Employee created successfully");
        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }


    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = """
                INSERT INTO employees(firstName, lastName, age, email, job_id) 
                VALUES (?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Employee added successfully");
        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
    }

    @Override
    public void dropTable() {
        String sql = """
                DROP TABLE IF NOT EXISTS employees;
                
                """;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error while dropping employee table" + e.getMessage());
        }
    }

    @Override
    public void cleanTable() {
        String sql = """
                TRUNCATE TABLE employees;
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error while cleaning employee table" + e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = """
                UPDATE employees 
                SET firstname = ?, LastName = ?, age = ?, email = ?, job_id = ?
                WHERE id = ?;
                
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Employee updated successfully");
        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "select * from employees;";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                );
                employees.add(employee);


            }

        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = "select * from employees where email = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")

                );
                return employee;
            }
        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employees = new HashMap<>();
        String sql = "select * from employees where id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                );
                Job job = new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("desscription"),
                        resultSet.getString("profeccion"),
                        resultSet.getInt("experience")
                );
                employees.put(employee, job);

            }
        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String sql = """
                SELECT * FROM employees e join jobs j on e.id = j.id where j.position = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, position);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                );
                employees.add(employee);
                return employees;
            }

        } catch (SQLException e) {
            System.out.println("Error while creating employee table" + e.getMessage());
        }
        return employees;

    }
}

