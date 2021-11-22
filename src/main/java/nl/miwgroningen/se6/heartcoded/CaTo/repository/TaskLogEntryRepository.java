package nl.miwgroningen.se6.heartcoded.CaTo.repository;

import nl.miwgroningen.se6.heartcoded.CaTo.model.TaskLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLogEntryRepository extends JpaRepository<TaskLogEntry, Integer> {
}
