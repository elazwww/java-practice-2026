package org.example.ru.itis.shop.user.infrastrcture.persistence.jdbc;

import org.example.ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import org.example.ru.itis.shop.user.domain.User;
import org.example.ru.itis.shop.user.repository.UserRepository;

import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    private final RowMapper<User> userRowMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profileDescription")
    );

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user){
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account(name, email, password, profileDescription) VALUES (?, ?, ?, ?)")){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getProfileDescription());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE email = ?")){
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()) {
                        return Optional.of(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE id = ?")){
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()) {
                        return Optional.of(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()){
                try (ResultSet resultSet = statement.executeQuery("select * from account")){
                    while (resultSet.next()) {
                        users.add((userRowMapper.mapRow(resultSet)));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE profileDescription = ?")){
                preparedStatement.setString(1, profileDescription);
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    if (resultSet.next()) {
                        users.add(userRowMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return users;
    }

    @Override
    public void updateProfileDescriptionByEmail(String email, String newProfileDescription) {
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET profileDescription = ? WHERE email = ?")){
                preparedStatement.setString(1, newProfileDescription);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
