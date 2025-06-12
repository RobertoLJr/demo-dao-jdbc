package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department dept) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                            INSERT INTO department
                            (Name)
                            VALUES
                            (?);""",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            st.setString(1, dept.getName());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt("Id");
                    dept.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected Error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department dept) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    """
                            UPDATE department
                            SET Name = ?
                            WHERE Id = ?;"""
            );
            st.setString(1, dept.getName());
            st.setInt(2, dept.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                            SELECT *
                            FROM department
                            WHERE Id = ?;"""
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department dept = new Department();
                dept.setId(rs.getInt("Id"));
                dept.setName(rs.getString("Name"));
                return dept;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                            SELECT *
                            FROM department;"""
            );
            rs = st.executeQuery();

            List<Department> deptList = new ArrayList<>();
            while (rs.next()) {
                Department dept = new Department(rs.getInt("Id"), rs.getString("Name"));
                deptList.add(dept);
            }
            return deptList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
