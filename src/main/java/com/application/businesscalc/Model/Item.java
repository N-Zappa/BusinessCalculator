package com.application.businesscalc.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String Name;
    private String Description;
    private String Status;
    private double BuyPrice;
    private double SellPrice;
    private double Income;
    private String PercentIncome;
    private String Date;
    private String ImageName;

    public Item(String Name, String Description, double BuyPrice, double SellPrice)
    {
        this.Name = Name;
        this.Description = Description;
        this.BuyPrice = BuyPrice;
        this.SellPrice = SellPrice;
        this.Income = SellPrice - BuyPrice;
    }
    public Item()
    {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public double getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        BuyPrice = buyPrice;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }

    public double getIncome() {
        return Income;
    }

    public void setIncome(double income) {
        Income = income;
    }

    public String getPercentIncome() {
        return PercentIncome;
    }

    public void setPercentIncome(String percentIncome) {
        PercentIncome = percentIncome;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }
}
