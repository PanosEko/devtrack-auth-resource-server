package com.panoseko.devtrack.image;

import com.panoseko.devtrack.task.Task;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String type;
    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    @Column(name= "image_data", length = 10000)
    private byte[] imageData;

    @Lob
    @JdbcType(VarbinaryJdbcType.class)
    @Column(name= "image_preview", length = 10000)
    private byte[] imagePreview;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

}
