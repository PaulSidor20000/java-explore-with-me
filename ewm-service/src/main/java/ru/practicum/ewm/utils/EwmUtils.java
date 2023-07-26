package ru.practicum.ewm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import static java.lang.Integer.parseInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EwmUtils {
//    public static Pageable getPage(MultiValueMap<String, String> params) {
//        int from = params.get("from") != null ? parseInt(params.get("from").get(0)) : 0;
//        int size = params.get("size") != null ? parseInt(params.get("size").get(0)) : 10;
//
//        return PageRequest.of(from > 0 ? from / size : 0, size);
//    }

}
