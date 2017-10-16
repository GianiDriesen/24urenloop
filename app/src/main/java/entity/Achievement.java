package entity;

import java.util.List;

/**
 * Interface for all Achievement classes
 * @author Koen
 */
public interface Achievement {
    int getId();
    void setId(int id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    List<String> getExportableFields();
    String printType();
    String toString();
}
