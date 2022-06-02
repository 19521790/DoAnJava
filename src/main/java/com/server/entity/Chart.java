package com.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "charts")
public class Chart {
    @Id
    private int id;

    @NotNull(message = "Chart name cannot be null")
    private String name;
    private String image;
    private Timestamp createTime;
}
