package com.hada.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hada.api.model.Video;

@Mapper
public interface VideoMapper {
	public List<Video> selectVideoList(int cno);
	public int insertVideoUrl(Video Video);
	public int deleteVideoUrl(int vno);
}
