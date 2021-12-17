package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class File
{
    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(final Integer userId,
                final String fileName,
                final String contentType,
                final Long fileSize,
                final byte[] fileData)
    {
        this.userId = userId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }

}
