package com.assignment.controller.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class APIAddressGHN {

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<String> getStringResponseEntity(String apiUrl, HttpEntity<String> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();
            // Xử lý kết quả ở đây
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }
    }

    @GetMapping("/getProvinceByValue")
    public ResponseEntity<String> getProvinceName(@RequestParam int provinceValue) throws JsonProcessingException {
        // URL của API trả về tên tỉnh thành
        String apiUrl = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province?value=" + provinceValue;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", "af00fa30-4f0e-11ee-96dc-de6f804954c9");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData);

            JsonNode dataNode = jsonNode.get("data");
            if (dataNode != null && dataNode.isArray()) {
                JsonNode provinceNode = dataNode.get(0); // Lấy ra phần tử đầu tiên
                String provinceName = provinceNode.get("ProvinceName").asText();
                System.out.println(provinceName);

                return ResponseEntity.ok(responseData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy tỉnh thành với giá trị " + provinceValue);
            }
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }
    }

    @GetMapping("/getDistrictByProvince")
    public ResponseEntity<String> getDistrictByName(@RequestParam String provinceId) throws JsonProcessingException {
        // URL của API trả về tên quận/huyện
        String apiUrl = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", "af00fa30-4f0e-11ee-96dc-de6f804954c9");

        String requestBody = "{\"province_id\":\"" + provinceId + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);


        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseData);

            JsonNode dataNode = jsonNode.get("data");
            if (dataNode != null && dataNode.isArray()) {
                JsonNode provinceNode = dataNode.get(0); // Lấy ra phần tử đầu tiên
                String DistrictName = provinceNode.get("DistrictName").asText();
                System.out.println(DistrictName);

                return ResponseEntity.ok(responseData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy tỉnh thành với giá trị " + provinceId);
            }
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        }
    }

    @GetMapping("/getWardByDistrict")
    public ResponseEntity<String> getWardByDistrict(@RequestParam String district_id) {
        String apiUrl = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + district_id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", "af00fa30-4f0e-11ee-96dc-de6f804954c9");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        return getStringResponseEntity(apiUrl, requestEntity);
    }
}
