package group6.ecommerce.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;

public class HandleSort {

    public static Sort buildSortProperties(String fields, String orders) {
        String[] listFields = fields.split(",");
        String[] listOrders = orders.split(",");
        List<Sort.Order> orderBuilder = new ArrayList<>();
        Map<String, Sort.Direction> propertyAndDirection = getStringDirectionMap(listFields, listOrders);

        for (Map.Entry<String, Sort.Direction> entry : propertyAndDirection.entrySet()) {
            String property = entry.getKey();
            Sort.Direction direction = entry.getValue();
            orderBuilder.add(new Sort.Order(direction, property));
        }
        return Sort.by(orderBuilder);
    }

    private static Map<String, Sort.Direction> getStringDirectionMap(String[] listFields, String[] listOrders) {
        Map<String, Sort.Direction> propertyAndDirection = new LinkedHashMap<>();

        for (String field : listFields) {
            Sort.Direction direction = Sort.Direction.ASC;
            propertyAndDirection.put(field.trim(), direction);
        }

        for (int i = 0; i < listFields.length; i++) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (listOrders[i].equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
            propertyAndDirection.put(listFields[i].trim(), direction);
        }
        return propertyAndDirection;
    }
}