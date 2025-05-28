package dao;

import db.DBConnection;
import models.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {

    private final Connection connection = DBConnection.getConnection();

    @Override
    public void createJobTable() {
        String sql = """
                    CREATE TABLE JOBS(
                    id serial primary key,
                    position varchar(20),
                    profession varchar(20),
                    description varchar(20),
                    experience integer)   
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            statement.close();
            System.out.println("Jobs table created successfully");
        } catch (SQLException e) {
            System.out.println("Error while creating job table" + e.getMessage());

        }
    }

    @Override
    public void addJob(Job job) {
        String sql = """
                INSERT INTO JOBS( position, profession, description, experience)
                values(?,?,?,?);
                
                """;
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sql)) {
            preparedStatement1.setString(1, job.getPosition());
            preparedStatement1.setString(2, job.getProfession());
            preparedStatement1.setString(3, job.getDescription());
            preparedStatement1.setInt(4, job.getExperience());
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            System.out.println("oshibka  adding job" + e.getMessage());
        }

    }
    @Override
    public Job getJobById(Long jobId) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery(); // Бул жерде бир эле жолу чакыруу жетет
            if (resultSet.next()) {
                return new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );
            }
        } catch (SQLException e) {
            System.out.println("Kata: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String order = ascOrDesc.equalsIgnoreCase("asc") ? "ASC" : "DESC";
        String sql = "SELECT * FROM jobs ORDER BY experience " + order;

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Job job = new Job(
                        rs.getLong("id"),
                        rs.getString("position"),
                        rs.getString("profession"),
                        rs.getString("description"),
                        rs.getInt("experience")
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            System.out.println("Kata: " + e.getMessage());
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        String sql = """
            SELECT j.* 
            FROM jobs j
            JOIN employee e ON j.id = e.job_id 
            WHERE e.id = ?;
        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error while getting job by employee id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql = "ALTER TABLE jobs DROP COLUMN description";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Succusfully deleted");
        } catch (SQLException e) {
            System.out.println(" Error while dropping description column: " + e.getMessage());
        }
    }
}