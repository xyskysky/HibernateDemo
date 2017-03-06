package com.jwn.hibernate.helloworld;

import java.sql.Blob;
import java.util.Date;

public class News
{
	private Integer id; //field
	private String title;
	private String author;
	
	private String desc;
	
	//ʹ�� title + "," + content ������������ǰ�� News ��¼. 
	//�� title + "," + content ������Ϊ News �� desc ����ֵ
	
	private String content;
	
	private Blob picture;
	
	private Date date;
	
	

	public Integer getId()
	{
		return id;
	}
    public News()
	{
	}
	public News(String title, String author, Date date)
	{
		super();
		this.title = title;
		this.author = author;
		this.date = date;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Blob getPicture()
	{
		return picture;
	}

	public void setPicture(Blob picture)
	{
		this.picture = picture;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	@Override
	public String toString()
	{
		return "News [id=" + id + ", title=" + title + ", author=" + author
				+ ", desc=" + desc + ", content=" + content + ", picture="
				+ picture + ", date=" + date + "]";
	}
	
}
