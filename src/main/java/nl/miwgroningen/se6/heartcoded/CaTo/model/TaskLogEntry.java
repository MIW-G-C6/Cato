package nl.miwgroningen.se6.heartcoded.CaTo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Remco Lantinga <remco_lantinga@hotmail.com>
 *
 * Contains one single action made on model Task.
 */

@Entity
public class TaskLogEntry {

    @Id
    @GeneratedValue
    private Integer TaskLogEntryId;

    private Integer TaskLogId;

    private String changedProperty;

    private Integer oldId;

    private String oldValue;

    private Integer newId;

    private String newValue;

    public Integer getTaskLogEntryId() {
        return TaskLogEntryId;
    }

    public void setTaskLogEntryId(Integer taskLogEntryId) {
        TaskLogEntryId = taskLogEntryId;
    }

    public Integer getTaskLogId() {
        return TaskLogId;
    }

    public void setTaskLogId(Integer taskLogId) {
        TaskLogId = taskLogId;
    }

    public String getChangedProperty() {
        return changedProperty;
    }

    public void setChangedProperty(String changedProperty) {
        this.changedProperty = changedProperty;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
