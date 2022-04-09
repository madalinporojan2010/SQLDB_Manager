package application.bll;

import application.dao.DatabaseDAO;
import application.model.BillData;

import java.util.List;
import java.util.NoSuchElementException;

public class DatabaseBLL {
    private DatabaseDAO databaseDAO;

    public DatabaseBLL() {
        databaseDAO = new DatabaseDAO();
    }

    public List<BillData> findAllBillData() {
        List<BillData> billData = databaseDAO.findAll();
        if (billData == null) {
            throw new NoSuchElementException("Not enough entries in database");
        }
        return billData;
    }

}
