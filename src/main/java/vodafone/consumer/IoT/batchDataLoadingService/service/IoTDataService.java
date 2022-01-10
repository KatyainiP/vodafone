package vodafone.consumer.IoT.batchDataLoadingService.service;

import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vodafone.consumer.IoT.batchDataLoadingService.entity.IoTDataEntity;
import vodafone.consumer.IoT.batchDataLoadingService.repository.IoTDataRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class IoTDataService {

    @Autowired
    IoTDataRepository ioTDataRepository;

    public void saveCSVData(MultipartFile multipartFile) throws IOException {

        List<IoTDataEntity> iotDataList = new ArrayList<>();
        InputStream inputStream = multipartFile.getInputStream();
        CsvParserSettings setting = new CsvParserSettings();
        setting.setHeaderExtractionEnabled(true);
        CsvParser csvParser = new CsvParser(setting);
        List<Record> parseRecords = csvParser.parseAllRecords(inputStream);
        parseRecords.forEach(record -> {
            IoTDataEntity ioTDataEntity = new IoTDataEntity();
            ioTDataEntity.setDatetime(record.getString("DateTime"));
            ioTDataEntity.setEventId(Long.parseLong(record.getString("EventId")));
            ioTDataEntity.setProductId(record.getString("ProductId"));
            ioTDataEntity.setLatitude(record.getString("Latitude"));
            ioTDataEntity.setLongitude(record.getString("Longitude"));
            ioTDataEntity.setBattery(record.getString("Battery"));
            ioTDataEntity.setLight(record.getString("Light"));
            ioTDataEntity.setAirplaneMode(record.getString("AirplaneMode"));
            iotDataList.add(ioTDataEntity);
        });

        ioTDataRepository.saveAll(iotDataList);
    }

    public ResponseEntity<String> getLocation(String productId, String timestamp) {
        List<IoTDataEntity> ioTDataEntityList = ioTDataRepository.findAll();
        for (IoTDataEntity record : ioTDataEntityList){
            if (record.getProductId().equals(productId)) {
                if (record.getDatetime().equals(timestamp)) {
                    System.out.println("id:" + record.getProductId());
                    System.out.println("name:" + productName(record.getProductId()));
                    System.out.println("timeStamp:" + record.getDatetime());
                    airplaneMode(record);
                } else {
                    System.out.println("id:" + record.getProductId());
                    System.out.println("name:" + productName(record.getProductId()));
                    Instant time = Instant.now();
                    System.out.println("timeStamp:"+ time);
                    airplaneMode(record);
                }
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("description: ERROR: Id <%id> not found", (record.getProductId())));
        }
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Device details received"));
    }


    private String productName(String productId) {
        if (productId.startsWith("WG"))
            return "Name: CyclePlusTracker";
        else
            return "Name: GeneralTracker";
    }


    private String batteryCheck(String battery) {
        String batteryStatus = "";
        double batt = Double.parseDouble(battery);
        switch ((int) batt) {
            case 1:
                if (batt >= 0.98)
                    batteryStatus = "FULL";
                break;
            case 2:
                if (batt >= 0.60)
                    batteryStatus = "HIGH";
                break;
            case 3:
                if (batt >= 0.40)
                    batteryStatus = "MEDIUM";
                break;
            case 4:
                if (batt >= 0.10)
                    batteryStatus = "LOW";
                break;
            case 5:
                if (batt < 0.10)
                    batteryStatus = "CRITICAL";
                break;
        }
        return batteryStatus;
    }


    private ResponseEntity<String> airplaneMode(IoTDataEntity entity) {
        String status = "";
        if (entity.getAirplaneMode().equals("ON")) {
            System.out.println("Lat: Null");
            System.out.println("Long: Null");
            System.out.println(status = "INACTIVE");
            batteryCheck(entity.getBattery());
            System.out.println("SUCCESS: Location not available: Plase turn off aiplane mode.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("description: ERROR: Device could not be located"));
        } else {
            System.out.println("Lat:" + entity.getLatitude());
            System.out.println("Long:" + entity.getLongitude());
            System.out.println(status = "ACTIVE");
            batteryCheck(entity.getBattery());
            System.out.println("SUCCESS: Location identified.");
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Device Located"));
        }
    }
}
