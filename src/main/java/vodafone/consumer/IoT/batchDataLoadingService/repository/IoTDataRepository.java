package vodafone.consumer.IoT.batchDataLoadingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vodafone.consumer.IoT.batchDataLoadingService.entity.IoTDataEntity;

@Repository
public interface IoTDataRepository extends JpaRepository<IoTDataEntity, Long> {
}
