package com.happytech.electronicstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

        @Column(name="create_date",updatable = false)
        @CreationTimestamp
        private LocalDate createdate;

        @Column(name="update_date",insertable = false)
        @UpdateTimestamp
        private LocalDate updatedate;

        @Column(name="status")
        private String isactive;
    }


