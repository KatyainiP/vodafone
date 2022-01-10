package vodafone.consumer.IoT.batchDataLoadingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vodafone.consumer.IoT.batchDataLoadingService.service.IoTDataService;

import java.io.*;
import java.util.TimeZone;

@RestController
public class IoTDataController {

    @Autowired
    IoTDataService ioTDataService;

    @PostMapping(value = "/iot/event/v1")
    public ResponseEntity<String> uploadData(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty() == false) {
            try {
                ioTDataService.saveCSVData(multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("data refreshed", HttpStatus.OK);
        }
        if (multipartFile.isEmpty() == true)
            return new ResponseEntity<>("ERROR: no data file found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("ERROR: A technical exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("iot/event/v1")
    public void getDeviceLocation(@RequestParam(required = true) String productId,
                                  @RequestParam(required = false) String timeStamp) {

        ioTDataService.getLocation(productId,timeStamp);
    }
}
