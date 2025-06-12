package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller.findById() ===");
        Seller seller = sellerDao.findById(3);

        System.out.println("\n=== TEST 2: seller.findByDepartment() ===");
        Department dept = new Department(2, null);
        List<Seller> sellersList = sellerDao.findByDepartment(dept);
        for (Seller s : sellersList) {
            System.out.println(s);
        }
    }
}