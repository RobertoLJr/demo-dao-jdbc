package app;


import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department.findById() ===");
        Department dept = departmentDao.findById(1);
        System.out.println(dept);

        System.out.println("\n=== TEST 2: department.findAll() ===");
        List<Department> deptList = departmentDao.findAll();
        System.out.println(deptList);
    }
}
