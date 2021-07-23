package org.benjamin.util.excel;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Student {
    @ExcelCell(index = 0)
    private String username;
    @ExcelCell(index = 1)
    private Integer age;
    @ExcelCell(index = 2)
    private String sex;
    @ExcelCell(index = 3)
    private Date birthday;
}
