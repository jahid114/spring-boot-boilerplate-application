package dev.jahid.user_auth_db_config_boilerplate.utility.dateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateRange {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
