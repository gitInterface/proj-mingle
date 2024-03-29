package com.ispan.mingle.projmingle.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "House_Photo")
public class HousePhotoBean {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "photoID")
	private Integer photoid;

    @Column(name = "photo", columnDefinition = "varbinary")
	@Lob
	private byte[] photo;

    @Column(name = "photoSize", columnDefinition = "int")
	private Integer photoSize;

    @Column(name = "contentType", columnDefinition = "varchar", length = 20)
	private String contentType;

    @Column(name = "createdAt", columnDefinition = "datetime")
	private Date createdAt;

	@Column(name = "updatedAt", columnDefinition = "datetime")
	private Date updatedAt;

    @Column(name = "isDeleted", columnDefinition = "bit")
    private Character isDeleted;
    
    @Column(name = "fk_houseID", columnDefinition = "int")
	private Integer houseid;

}