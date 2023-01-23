package Dbf;

/**
 * Process each dbf row.
 *
 * @author Sergey Polovko
 */
public interface DbfRowProcessor {

    void processRow(Object[] row);

}
