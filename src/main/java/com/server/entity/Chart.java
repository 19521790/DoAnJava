package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "charts")
public class Chart {
    @Id
    private int id;
    private String name;
    private String image;
    private Timestamp createTime;
}
