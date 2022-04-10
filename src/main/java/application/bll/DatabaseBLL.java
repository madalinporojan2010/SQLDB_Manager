package application.bll;

import application.dao.DatabaseDAO;
import application.model.BillData;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class used for the Database Business Layer.
 */
public class DatabaseBLL {
    private DatabaseDAO databaseDAO;

    /**
     * DatabaseBLL constructor.
     * Instantiates the DatabaseDAO Class.
     */
    public DatabaseBLL() {
        databaseDAO = new DatabaseDAO();
    }

    /**
     * Finds all the data required for the Bill.
     *
     * @return A List of BillData.
     * @throws NoSuchElementException Stating that the Order table is empty.
     */
    public List<BillData> findAllBillData() {
        List<BillData> billData = databaseDAO.findAll();
        if (billData == null) {
            throw new NoSuchElementException("Not enough entries in database");
        }
        return billData;
    }

}
