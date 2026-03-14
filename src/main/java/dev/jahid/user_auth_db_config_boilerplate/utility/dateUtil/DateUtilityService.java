package dev.jahid.user_auth_db_config_boilerplate.utility.dateUtil;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DateUtilityService {

    public LocalDateTime getStartOfDayAsLocalDateTime ( LocalDate date ) {
        return date.atStartOfDay();
    }

    public LocalDateTime getEndOfDayAsLocalDateTime( LocalDate date ) {
        return date.atTime(LocalTime.MAX);
    }

    public DateRange calculateDateRangeForPage( int pageNumber, int pageSize ) {
        int startOffset = (pageNumber + 1) * pageSize - 1;
        int endOffset = pageNumber * pageSize;

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(startOffset);
        LocalDate endDate = today.minusDays(endOffset);

        return new DateRange(
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );
    }

    public List<LocalDate> mergeDateCollections( Map<LocalDate, ?> map1, Map<LocalDate, ?> map2 ) {
        return Stream.concat( map1.keySet().stream(), map2.keySet().stream() )
                .distinct()
                .sorted( Comparator.reverseOrder() )
                .collect(Collectors.toList() );
    }
}
